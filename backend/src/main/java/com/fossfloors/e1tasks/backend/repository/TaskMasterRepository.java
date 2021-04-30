package com.fossfloors.e1tasks.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;

@Repository
public interface TaskMasterRepository
    extends JpaRepository<TaskMaster, Long>, TaskMasterRepositoryCustom {

  TaskMaster findByInternalTaskID(String id);

  List<TaskMaster> findAll();

}
