package com.fossfloors.e1tasks.backend.service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fossfloors.e1tasks.backend.beans.VariantID;
import com.fossfloors.e1tasks.backend.entity.Task;
import com.fossfloors.e1tasks.backend.util.TaskMenuPrintUtil;
import com.fossfloors.e1tasks.backend.util.TaskRelationshipsProcessor;

@Deprecated
// @Service
// @Transactional
public class TasksService {

  private static final Logger                 logger = LoggerFactory.getLogger(TasksService.class);

  private final TaskMasterService             taskMasterService;
  private final TaskRelationshipService       relationService;
  private final VariantDetailService          variantService;

  private TaskRelationshipsProcessor          relationsProcessor;

  private Map<String, LinkedHashSet<Task>>    masterMenuMap;
  private Map<VariantID, LinkedHashSet<Task>> variantMenuMap;

  @Autowired
  public TasksService(TaskMasterService taskMasterService, TaskRelationshipService relationService,
      VariantDetailService variantService) {
    this.taskMasterService = taskMasterService;
    this.relationService = relationService;
    this.variantService = variantService;
  }

  @PostConstruct
  private void init() {
    relationsProcessor = new TaskRelationshipsProcessor(taskMasterService.findAll(),
        relationService, variantService);

    // Map<String, Task> masterMenus = relationsProcessor.buildMasterMenus();
    // TaskMenuPrintUtil.printMasterMenuMap(masterMenus);

    // Task task91 = relationsProcessor.buildMasterMenu("91");
    // TaskMenuPrintUtil.printMasterMenu("91", task91);
    //
    Task fossTask = relationsProcessor.buildMasterMenu("1014");
    TaskMenuPrintUtil.printMasterMenu("1014", fossTask);
    //
    // fossVariantsMap = relationsProcessor.buildVariantMenusForView("1014");
    //
    // Task ffmfgloTask = relationsProcessor.buildVariantMenu("FFMFGLO", "1014");
    // TaskMenuPrintUtil.printVariantMenu(new VariantID("FFMFGLO", "1014"), ffmfgloTask);
    //
    // Task ffmathanTask = relationsProcessor.buildVariantMenu("FFMATHAN", "1014");
    // TaskMenuPrintUtil.printVariantMenu(new VariantID("FFMATHAN", "1014"), ffmathanTask);
    //
    // Task ffconvmgrTask = relationsProcessor.buildVariantMenu("FFCONVMGR", "1014");
    // TaskMenuPrintUtil.printVariantMenu(new VariantID("FFCONVMGR", "1014"), ffconvmgrTask);
  }

  // @formatter:on

  public List<String> getMasterMenuTaskViews() {
    return relationService.getDistinctTaskViewIDs();
  }

  // @formatter:off
  public Set<Task> getTopLevelTasks(String taskView) {
    LinkedHashSet<Task> variantSet = masterMenuMap.get(taskView);
    
    if (variantSet != null) {
      return variantSet.stream()
          .filter(t -> t.getFromReferences().isEmpty())
          .collect(Collectors.toSet());
    } else {
      return null;
    }
  }
  // @formatter:on

  // @formatter:off
  public int getChildCount(String taskView, Task parent) {
    int count = 0;

    if (taskView == null) {
      return count;
    }

    if (parent != null) {
      LinkedHashSet<Task> variantSet = masterMenuMap.get(taskView);
      Optional<Task> taskOpt = variantSet.stream()
          .filter(t -> t.getInternalTaskID().equals(parent.getInternalTaskID()))
          .findFirst();
      count = taskOpt
          .map(t -> t.getToReferences().size())
          .orElse(0);
    }

    return count;
  }
  // @formatter:on

  // @formatter:off
  public boolean hasChildren(String taskView, Task parent) {
    boolean result = false;

    if (taskView == null) {
      return result;
    }

    if (parent != null) {
      LinkedHashSet<Task> variantSet = masterMenuMap.get(taskView);
      Optional<Task> taskOpt = variantSet.stream()
          .filter(t -> t.getInternalTaskID().equals(parent.getInternalTaskID()))
          .findFirst();
      result = taskOpt
          .map(t -> !t.getToReferences().isEmpty())
          .orElse(false);
    }

    return result;
  }
  // @formatter:on

  // @formatter:off
  public Set<Task> getChildren(String taskView, Task parent) {
    Set<Task> result = new HashSet<>();

    if (taskView == null) {
      return result;
    }

    if (parent != null) {
      LinkedHashSet<Task> variantSet = masterMenuMap.get(taskView);
      Optional<Task> taskOpt = variantSet.stream()
          .filter(task -> task.getInternalTaskID().equals(parent.getInternalTaskID()))
          .findFirst();
      taskOpt.ifPresent(p -> {
        p.getToReferences().forEach(c -> result.add(c));
      });
    }

    return result;
  }
  // @formatter:on

  // @formatter:off
  public Set<Task> getTopLevelTasks(VariantID variant) {
    if (variant == null) {
      return new HashSet<>();
    }
    
    Set<Task> variantSet = variantMenuMap.get(variant);
    return variantSet.stream()
        .filter(t -> t.getFromReferences().isEmpty())
        .collect(Collectors.toSet());
  }
  // @formatter:on

  // @formatter:off
  public int getChildCount(VariantID variant, Task parent) {
    int count = 0;

    if (variant == null) {
      return count;
    }

    if (parent != null) {
      Set<Task> variantSet = variantMenuMap.get(variant);
      Optional<Task> taskOpt = variantSet.stream()
          .filter(t -> t.getInternalTaskID().equals(parent.getInternalTaskID()))
          .findFirst();
      count = taskOpt
          .map(t -> t.getToReferences().size())
          .orElse(0);
    }

    return count;
  }
  // @formatter:on

  // @formatter:off
  public boolean hasChildren(VariantID variant, Task parent) {
    boolean result = false;

    if (variant == null) {
      return result;
    }

    if (parent != null) {
      Set<Task> variantSet = variantMenuMap.get(variant);
      Optional<Task> taskOpt = variantSet.stream()
          .filter(t -> t.getInternalTaskID().equals(parent.getInternalTaskID()))
          .findFirst();
      result = taskOpt
          .map(t -> !t.getToReferences().isEmpty())
          .orElse(false);
    }

    return result;
  }
  // @formatter:on

  // @formatter:off
  public Set<Task> getChildren(VariantID variant, Task parent) {
    Set<Task> result = new HashSet<>();

    if (variant == null) {
      return result;
    }

    if (parent != null) {
      Set<Task> variantSet = variantMenuMap.get(variant);
      Optional<Task> taskOpt = variantSet.stream()
          .filter(task -> task.getInternalTaskID().equals(parent.getInternalTaskID()))
          .findFirst();
      taskOpt.ifPresent(p -> {
        p.getToReferences().forEach(c -> result.add(c));
      });
    }

    return result;
  }
  // @formatter:on

}
