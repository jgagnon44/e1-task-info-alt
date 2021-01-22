package com.fossfloors.e1tasks.backend.repository;

import java.util.List;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;

public interface TaskMasterRepositoryCustom {

  // List<E1Task2> findAll();

  List<TaskMaster> findAllRootTasks();

  // boolean hasChildren(E1Task2 item);
  //
  // int getChildCount(E1Task2 parent);
  //
  // List<E1Task2> getChildren(E1Task2 parent);

}
