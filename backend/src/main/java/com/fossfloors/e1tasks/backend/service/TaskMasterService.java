package com.fossfloors.e1tasks.backend.service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fossfloors.e1tasks.backend.beans.TaskFilter;
import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.repository.TaskMasterRepository;

@Service
@Transactional
public class TaskMasterService {

  private static final Logger        logger = LoggerFactory.getLogger(TaskMasterService.class);

  private final TaskMasterRepository taskMasterRepo;

  @Autowired
  public TaskMasterService(TaskMasterRepository taskMasterRepo) {
    this.taskMasterRepo = taskMasterRepo;
  }

  public Set<TaskMaster> findAll() {
    return Set.copyOf(taskMasterRepo.findAll());
  }

  @Deprecated
  public TaskMaster findByVariantAndInternalTaskID(String variantName, String id) {
    return taskMasterRepo.findByInternalTaskID(variantName, id);
  }

  public List<TaskMaster> filter(TaskFilter filter) {
    return taskMasterRepo.filter(filter);
  }

  public void saveAll(Set<TaskMaster> taskMasterSet) {
    taskMasterRepo.saveAll(taskMasterSet);
  }

}
