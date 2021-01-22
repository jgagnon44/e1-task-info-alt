package com.fossfloors.e1tasks.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.entity.TaskRelationship;
import com.fossfloors.e1tasks.backend.repository.TaskMasterRepository;

@Service
@Transactional
public class TaskMasterService {

  private static final Logger           logger = LoggerFactory.getLogger(TaskMasterService.class);

  private final TaskMasterRepository    taskMasterRepo;

  private final TaskRelationshipService taskRelationService;

  @Autowired
  public TaskMasterService(TaskMasterRepository taskMasterRepo,
      TaskRelationshipService taskRelationService) {
    this.taskMasterRepo = taskMasterRepo;
    this.taskRelationService = taskRelationService;
  }

  public List<TaskMaster> findAll() {
    return taskMasterRepo.findAll();
  }

  public void saveAll(Set<TaskMaster> taskMasterSet) {
    taskMasterRepo.saveAll(taskMasterSet);
  }

  public List<TaskMaster> getTopLevelTasks() {
    List<TaskMaster> result = new ArrayList<>();
    List<String> topLevelIDs = taskRelationService.getTopLevelParentTaskIDs();

    topLevelIDs.forEach(id -> {
      TaskMaster task = taskMasterRepo.findByInternalTaskID(id);

      if (task != null) {
        result.add(task);
      }
    });

    return result;
  }

  public int getChildCount(TaskMaster parent) {
    if (parent != null) {
      return taskRelationService.getChildRelationsForParent(parent.getInternalTaskID()).size();
    } else {
      return 0;
    }
  }

  public boolean hasChildren(TaskMaster item) {
    if (item != null) {
      return !taskRelationService.getChildRelationsForParent(item.getInternalTaskID()).isEmpty();
    } else {
      return false;
    }
  }

  public List<TaskMaster> getChildren(TaskMaster parent) {
    List<TaskMaster> result = new ArrayList<>();

    if (parent != null) {
      List<TaskRelationship> childRelations = taskRelationService
          .getChildRelationsForParent(parent.getInternalTaskID());

      childRelations.forEach(relation -> {
        TaskMaster task = taskMasterRepo.findByInternalTaskID(relation.getChildTaskID());
        result.add(task);
      });
    }

    return result;
  }

}
