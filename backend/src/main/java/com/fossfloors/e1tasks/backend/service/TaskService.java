package com.fossfloors.e1tasks.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fossfloors.e1tasks.backend.entity.Task;
import com.fossfloors.e1tasks.backend.repository.TaskRepository;

@Service
@Transactional
public class TaskService {

  private static final Logger  logger = LoggerFactory.getLogger(TaskService.class);

  private final TaskRepository taskRepo;

  @Autowired
  public TaskService(TaskRepository taskRepo) {
    this.taskRepo = taskRepo;
  }

  public Set<Task> findAll() {
    return Set.copyOf(taskRepo.findAll());
  }

  public void saveAll(Set<Task> taskSet) {
    taskRepo.saveAll(taskSet);
  }

  public Map<String, Set<Task>> getMasterMenuMap(List<String> taskViews) {
    Map<String, Set<Task>> result = new HashMap<>();

    taskViews.forEach(taskView -> {
      List<Task> viewTasks = taskRepo.findAllForTaskView(taskView);
      result.put(taskView, Set.copyOf(viewTasks));
    });

    return result;
  }

}
