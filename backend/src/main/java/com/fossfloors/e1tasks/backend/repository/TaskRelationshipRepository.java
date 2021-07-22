package com.fossfloors.e1tasks.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fossfloors.e1tasks.backend.entity.TaskRelationship;

@Repository
public interface TaskRelationshipRepository extends JpaRepository<TaskRelationship, Long> {

  TaskRelationship findByParentTaskID(String id);

  TaskRelationship findByChildTaskID(String id);

}
