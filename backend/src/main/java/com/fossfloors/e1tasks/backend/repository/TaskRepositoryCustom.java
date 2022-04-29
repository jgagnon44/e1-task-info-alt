package com.fossfloors.e1tasks.backend.repository;

import java.util.List;

import com.fossfloors.e1tasks.backend.entity.Task;

public interface TaskRepositoryCustom {

  List<Task> findAll();

  List<Task> findAllForTaskView(String taskView);

}
