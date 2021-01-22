package com.fossfloors.e1tasks.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;

@Repository
public interface TaskMasterRepository extends JpaRepository<TaskMaster, Long> {

  // List<E1Task2> findAll();

  TaskMaster findByInternalTaskID(String id);

  // List<E1Task2> findAllRootTasks();

  // boolean hasChildren(E1Task2 item);
  //
  // int getChildCount(E1Task2 parent);
  //
  // List<E1Task2> getChildren(E1Task2 parent);

}
