package com.fossfloors.e1tasks.backend.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fossfloors.e1tasks.backend.entity.TaskRelationship;
import com.fossfloors.e1tasks.backend.repository.TaskRelationshipRepository;

@Service
@Transactional
public class TaskRelationshipService {

  private static final Logger              logger = LoggerFactory
      .getLogger(TaskRelationshipService.class);

  private final TaskRelationshipRepository repo;

  @PersistenceContext
  private EntityManager                    entityManager;

  @Autowired
  public TaskRelationshipService(TaskRelationshipRepository repo) {
    this.repo = repo;
  }

  public Set<TaskRelationship> findAll() {
    return Set.copyOf(repo.findAll());
  }

  public void saveAll(Set<TaskRelationship> taskRelationshipSet) {
    repo.saveAll(taskRelationshipSet);
  }

  // @formatter:off
  public List<TaskRelationship> getRelationsForTaskView(String taskView) {
    return entityManager
        .createQuery("select t from TaskRelationship t where t.taskView = :view", TaskRelationship.class)
        .setParameter("view", taskView)
        .getResultList();
  }
  // @formatter:on

  //
  // get task relations by task view
  //

  @Deprecated
  // @formatter:off
  public List<String> getParentTaskIDs() {
    return entityManager
        .createQuery("select t.parentTaskID from TaskRelationship t", String.class)
        .getResultList();
  }
  // @formatter:on

  // @formatter:off
  public List<String> getDistinctParentTaskIDs() {
    return entityManager
        .createQuery("select distinct t.parentTaskID from TaskRelationship t", String.class)
        .getResultList();
  }
  // @formatter:on

  // @formatter:off
  public List<String> getDistinctParentTaskIDs(String taskView) {
    return entityManager
        .createQuery("select distinct t.parentTaskID from TaskRelationship t where t.taskView = :view", String.class)
        .setParameter("view", taskView)
        .getResultList();
  }
  // @formatter:on

  @Deprecated
  // @formatter:off
  public List<String> getChildTaskIDs() {
    return entityManager
        .createQuery("select t.childTaskID from TaskRelationship t", String.class)
        .getResultList();
  }
  // @formatter:on

  // @formatter:off
  public List<String> getDistinctChildTaskIDs() {
    return entityManager
        .createQuery("select distinct t.childTaskID from TaskRelationship t", String.class)
        .getResultList();
  }
  // @formatter:on

  // @formatter:off
  public List<String> getDistinctChildTaskIDs(String taskView) {
    return entityManager
        .createQuery("select distinct t.childTaskID from TaskRelationship t where t.taskView = :view", String.class)
        .setParameter("view", taskView)
        .getResultList();
  }
  // @formatter:on

  // @formatter:off
  public List<String> getTopLevelParentTaskIDs() {
    List<String> distinctParentIDs = getDistinctParentTaskIDs();
    List<String> distinctChildIDs = getDistinctChildTaskIDs();
    return distinctParentIDs.stream()
        .filter(id -> !distinctChildIDs.contains(id))
        .collect(Collectors.toList());
  }
  // @formatter:on

  // @formatter:off
  public List<String> getTopLevelTaskIDsByTaskView(String taskView) {
    List<String> distinctParentIDs = getDistinctParentTaskIDs(taskView);
    List<String> distinctChildIDs = getDistinctChildTaskIDs(taskView);
    return distinctParentIDs.stream()
        .filter(t -> !distinctChildIDs.contains(t))
        .collect(Collectors.toList());
  }
  // @formatter:on

  // @formatter:off
  public List<TaskRelationship> getChildRelationsForParent(String parentID) {
    return entityManager
        .createQuery("select t from TaskRelationship t where t.parentTaskID = :parentID" +
            " order by t.presentationSeq asc", TaskRelationship.class)
        .setParameter("parentID", parentID)
        .getResultList();
  }
  // @formatter:on

  // @formatter:off
  public List<String> getDistinctTaskViewIDs() {
    return entityManager
        .createQuery("select distinct t.taskView from TaskRelationship t", String.class)
        .getResultList();
  }
  // @formatter:on

}
