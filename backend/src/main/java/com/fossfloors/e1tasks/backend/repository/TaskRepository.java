package com.fossfloors.e1tasks.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fossfloors.e1tasks.backend.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, TaskRepositoryCustom {

  List<Task> findAll();

  List<Task> findAllForTaskView(String taskView);

}
