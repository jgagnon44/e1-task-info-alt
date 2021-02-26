package com.fossfloors.e1tasks.backend.repository;

import java.util.List;

import com.fossfloors.e1tasks.backend.beans.TaskFilter;
import com.fossfloors.e1tasks.backend.entity.TaskMaster;

public interface TaskMasterRepositoryCustom {

  List<TaskMaster> findAll();

  List<TaskMaster> findAllRootTasks();

  List<TaskMaster> filter(TaskFilter filter);

}
