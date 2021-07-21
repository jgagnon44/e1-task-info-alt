package com.fossfloors.e1tasks.backend.util;

import java.util.HashMap;
import java.util.HashSet;
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
import com.fossfloors.e1tasks.backend.entity.TaskRelationship;
import com.fossfloors.e1tasks.backend.service.TaskMasterService;
import com.fossfloors.e1tasks.backend.service.TaskRelationshipService;
import com.fossfloors.e1tasks.backend.service.VariantDetailService;

public class RelationshipsProcessor {

  private static final Logger           logger      = LoggerFactory
      .getLogger(RelationshipsProcessor.class);

  private final TaskMasterService       taskMasterService;
  private final TaskRelationshipService relationService;
  private final VariantDetailService    variantService;

  private boolean                       initialized = false;
  private Map<String, TaskMaster>       taskMasterMap;

  public RelationshipsProcessor(TaskMasterService taskMasterService,
      TaskRelationshipService relationService, VariantDetailService variantService) {
    this.taskMasterService = taskMasterService;
    this.relationService = relationService;
    this.variantService = variantService;
  }

  private void initMasterMap() {
    // Convert TaskMaster set to map, keyed by internalTaskID.
    taskMasterMap = taskMasterService.findAll().stream()
        .collect(Collectors.toMap(TaskMaster::getInternalTaskID, e -> e));
    initialized = true;
  }

  public Map<String, Set<Task>> buildMasterMenus() {
    Map<String, Set<Task>> result = new HashMap<>();
    List<String> viewIDs = relationService.getDistinctTaskViewIDs();

    viewIDs.forEach(id -> {
      result.put(id, buildMasterMenu(id));
    });

    return result;
  }

  public Set<Task> buildMasterMenu(String viewID) {
    if (!initialized) {
      initMasterMap();
    }

    Set<Task> taskSet = new HashSet<>();

    logger.info("Processing task view: {}", viewID);

    // Get the top level task(s) for the task view.
    List<String> topViewIDs = relationService.getTopLevelTaskIDsByTaskView(viewID);

    topViewIDs.forEach(id -> {
      TaskMaster taskMaster = taskMasterMap.get(id);

      if (taskMaster != null) {
        Task task = new Task(taskMaster);

        // Set task view ID.
        task.setTaskView(viewID);

        processTask(task);
        taskSet.add(task);
      }
    });

    return taskSet;
  }

  private void processTask(Task task) {
    // Get child relationships for the task. (Ordered by presentation sequence.)
    List<TaskRelationship> children = relationService
        .getChildRelationsForParent(task.getInternalTaskID());

    children.forEach(rel -> {
      String childID = rel.getChildTaskID();
      TaskMaster childMaster = taskMasterMap.get(childID);

      if (childMaster != null) {
        if (!hasCircularReference(task, childID)) {
          Task child = new Task(childMaster);

          // Inherit task view ID from parent.
          child.setTaskView(task.getTaskView());

          // Add child as a "to" reference.
          task.addToReference(child);

          processTask(child);
        } else {
          logger.debug("Circular reference: child {}", childID);
        }
      }
    });
  }

  public Map<VariantID, Set<Task>> buildVariantMenus() {
    Map<VariantID, Set<Task>> result = new HashMap<>();
    List<String> variants = variantService.getDistinctVariantNames();

    variants.forEach(varName -> {
      List<String> varViews = variantService.getDistinctTaskViewIDsForVariant(varName);

      varViews.forEach(viewID -> {
        VariantID varID = new VariantID(varName, viewID);
        result.put(varID, buildVariantMenu(varName, viewID));
      });
    });

    return result;
  }

  public Set<Task> buildVariantMenu(String variantName, String taskView) {
    if (!initialized) {
      initMasterMap();
    }

    Set<Task> taskSet = new HashSet<>();

    logger.info("Processing variant/task view: {}/{}", variantName, taskView);

    // Get variant/view task exclusions. Convert to list of ParentChildRelationship objects.
    List<ParentChildRelationship> exclusions = variantService
        .getTaskExclusionsForVariantAndTaskView(variantName, taskView).stream()
        .map(detail -> new ParentChildRelationship(detail.getParentTaskID(),
            detail.getChildTaskID()))
        .collect(Collectors.toList());

    // Get the top level task(s) for the task view.
    List<String> topViewIDs = relationService.getTopLevelTaskIDsByTaskView(taskView);

    topViewIDs.forEach(id -> {
      TaskMaster taskMaster = taskMasterMap.get(id);

      if (taskMaster != null) {
        Task task = new Task(taskMaster);

        // Set task variant name and task view.
        task.setVariantName(variantName);
        task.setTaskView(taskView);

        processTask(task, exclusions);
        taskSet.add(task);
      }
    });

    return taskSet;
  }

  private void processTask(Task task, final List<ParentChildRelationship> exclusions) {
    // Get child relationships for the task. (Ordered by presentation sequence.)
    List<TaskRelationship> children = relationService
        .getChildRelationsForParent(task.getInternalTaskID());

    children.forEach(rel -> {
      ParentChildRelationship relation = new ParentChildRelationship(rel.getParentTaskID(),
          rel.getChildTaskID());

      if (!exclusions.contains(relation)) {
        String childID = rel.getChildTaskID();
        TaskMaster childMaster = taskMasterMap.get(childID);

        if (childMaster != null) {
          if (!hasCircularReference(task, childID)) {
            Task child = new Task(childMaster);

            // Inherit task variant name and task view from parent.
            child.setVariantName(task.getVariantName());
            child.setTaskView(task.getTaskView());

            // Add child as a "to" reference.
            task.addToReference(child);

            processTask(child, exclusions);
          } else {
            logger.debug("Circular reference: child {}", childID);
          }
        }
      } else {
        logger.debug("Excluded: child {} for parent {}", rel.getChildTaskID(),
            rel.getParentTaskID());
      }
    });
  }

  private boolean hasCircularReference(Task task, String id) {
    if (task.getInternalTaskID().equals(id)) {
      return true;
    }

    for (Task t : task.getFromReferences()) {
      return hasCircularReference(t, id);
    }

    return false;
  }

}