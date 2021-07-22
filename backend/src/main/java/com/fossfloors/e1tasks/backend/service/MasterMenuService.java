package com.fossfloors.e1tasks.backend.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fossfloors.e1tasks.backend.entity.Task;

@Service
@Transactional
public class MasterMenuService {

  private static final Logger           logger = LoggerFactory.getLogger(MasterMenuService.class);

  private final TaskRelationshipService trService;
  private final TaskService             taskService;

  private Map<String, Set<Task>>        masterMenuMap;

  @Autowired
  public MasterMenuService(TaskRelationshipService trService, TaskService taskService) {
    this.trService = trService;
    this.taskService = taskService;
  }

  @PostConstruct
  private void init() {
    List<String> taskViews = trService.getDistinctTaskViewIDs();
    masterMenuMap = taskService.getMasterMenuMap(taskViews);
  }

  public List<String> getMasterMenuTaskViewIDs() {
    return trService.getDistinctTaskViewIDs();
  }

  public Set<Task> getTopLevelTasks(String taskView) {
    return masterMenuMap.get(taskView);
  }

  public int getChildCount(String taskView, Task parent) {
    int count = 0;

    if (taskView == null) {
      return count;
    }

    if (parent != null) {
      count = parent.getToReferences().size();
    } else {
      Set<Task> taskSet = masterMenuMap.get(taskView);
      count = taskSet.size();
    }

    return count;
  }

  public boolean hasChildren(String taskView, Task item) {
    boolean result = false;

    if (taskView == null) {
      return result;
    }

    if (item != null) {
      result = !item.getToReferences().isEmpty();
    } else {
      Set<Task> taskSet = masterMenuMap.get(taskView);
      result = !taskSet.isEmpty();
    }

    return result;
  }

  public Set<Task> getChildren(String taskView, Task parent) {
    Set<Task> result = new HashSet<>();

    if (taskView == null) {
      return result;
    }

    if (parent != null) {
      result = parent.getToReferences();
    } else {
      result = masterMenuMap.get(taskView);
    }

    return result;
  }

}
