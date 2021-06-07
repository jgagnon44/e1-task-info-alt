package com.fossfloors.e1tasks.backend.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fossfloors.e1tasks.backend.beans.ParentChildRelationship;
import com.fossfloors.e1tasks.backend.beans.VariantID;
import com.fossfloors.e1tasks.backend.entity.Task;
import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.service.TaskRelationshipService;
import com.fossfloors.e1tasks.backend.service.VariantDetailService;

/**
 * 
 */
public class TaskRelationshipsProcessor {

  private static final Logger           logger = LoggerFactory
      .getLogger(TaskRelationshipsProcessor.class);

  private final TaskRelationshipService relationService;
  private final VariantDetailService    variantService;

  private final Map<String, TaskMaster> taskMasterMap;

  /**
   * 
   * @param taskMasterSet
   * @param relationService
   * @param variantService
   */
  public TaskRelationshipsProcessor(final Set<TaskMaster> taskMasterSet,
      TaskRelationshipService relationService, VariantDetailService variantService) {
    this.relationService = relationService;
    this.variantService = variantService;

    // Convert TaskMaster set to map, keyed by internalTaskID.
    taskMasterMap = taskMasterSet.stream()
        .collect(Collectors.toMap(TaskMaster::getInternalTaskID, e -> e));
  }

  /**
   * 
   * @return
   */
  public Map<String, Task> buildMasterMenus() {
    Map<String, Task> result = new HashMap<>();
    List<String> viewIDs = relationService.getDistinctTaskViewIDs();

    viewIDs.forEach(id -> {
      result.put(id, buildMasterMenu(id));
    });

    return result;
  }

  /**
   * 
   * @param viewID
   * @return
   */
  public Task buildMasterMenu(String viewID) {
    Task result = null;

    logger.info("Processing view ID: {}", viewID);
    TaskMaster viewMaster = taskMasterMap.get(viewID);

    if (viewMaster != null /* && viewMaster.isActive() */) {
      Task viewRoot = new Task(viewMaster);
      result = viewRoot;

      // Get the top level task(s) for the task view.
      List<String> topLevelIDs = relationService.getTopLevelTaskIDsByTaskView(viewID);

      topLevelIDs.forEach(id -> {
        // Process top level relations of the view root task.
        processChild(viewRoot, id);
      });
    } else {
      logger.warn("TaskMaster for view ID {} is null or not active", viewID);
    }

    return result;
  }

  private void processChild(Task parent, String childID) {
    TaskMaster childMaster = taskMasterMap.get(childID);

    if (childMaster != null /* && childMaster.isActive() */) {
      Task child = new Task(childMaster);
      // Add "to" reference from parent to child.
      parent.addToReference(child);

      // Process child relations of the child task.
      // (Child relations are ordered by presentation sequence.)
      relationService.getChildRelationsForParent(childID).forEach(rel -> {
        // Avoid circular dependency.
        if (childID.equals(rel.getChildTaskID())) {
          logger.warn("Circular reference: parent = child: {}", childID);
        } else {
          processChild(child, rel.getChildTaskID());
        }
      });
    } else {
      logger.warn("TaskMaster for ID {} is null or not active", childID);
    }
  }

  /**
   * 
   * @return
   */
  public Map<VariantID, Task> buildVariantMenus() {
    Map<VariantID, Task> result = new HashMap<>();

    List<String> variants = variantService.getDistinctVariantNames();

    variants.forEach(variantName -> {
      List<String> variantViews = variantService.getDistinctTaskViewIDsForVariant(variantName);

      variantViews.forEach(viewID -> {
        result.put(new VariantID(variantName, viewID), buildVariantMenu(variantName, viewID));
      });
    });

    return result;
  }

  /**
   * 
   * @param variantName
   * @param viewID
   * @return
   */
  public Task buildVariantMenu(String variantName, String viewID) {
    Task result = null;

    VariantID variantID = new VariantID(variantName, viewID);
    logger.info("Processing {}", variantID);

    TaskMaster viewMaster = taskMasterMap.get(viewID);

    if (viewMaster != null /* && viewMaster.isActive() */) {
      Task viewRoot = new Task(viewMaster);
      result = viewRoot;

      // Get variant/view task exclusions. Convert to list of ParentChildRelationship objects.
      List<ParentChildRelationship> exclusions = variantService
          .getTaskExclusionsForVariantAndTaskView(variantName, viewID).stream()
          .map(detail -> new ParentChildRelationship(detail.getParentTaskID(),
              detail.getChildTaskID()))
          .collect(Collectors.toList());

      // Get the top level task(s) for the task view.
      List<String> topLevelIDs = relationService.getTopLevelTaskIDsByTaskView(viewID);

      topLevelIDs.forEach(id -> {
        // Process top level relations of the view root task, checking for exclusions.
        checkForExclusions(viewRoot, id, exclusions);
      });
    } else {
      logger.warn("TaskMaster for view ID {} is null or not active", viewID);
    }

    return result;
  }

  private void checkForExclusions(Task parent, String childID,
      final List<ParentChildRelationship> exclusions) {
    ParentChildRelationship relation = new ParentChildRelationship(parent.getInternalTaskID(),
        childID);

    if (!exclusions.contains(relation)) {
      TaskMaster childMaster = taskMasterMap.get(childID);

      if (childMaster != null /* && childMaster.isActive() */) {
        Task child = new Task(childMaster);
        // Add "to" reference from parent to child.
        parent.addToReference(child);

        // Process child relations of the child task.
        // (Child relations are ordered by presentation sequence.)
        relationService.getChildRelationsForParent(childID).forEach(rel -> {
          // Avoid circular dependency.
          if (childID.equals(rel.getChildTaskID())) {
            logger.warn("Circular reference: parent = child: {}", childID);
          } else {
            checkForExclusions(child, rel.getChildTaskID(), exclusions);
          }
        });
      } else {
        logger.warn("TaskMaster for ID {} is null or not active", childID);
      }
    }
  }

}
