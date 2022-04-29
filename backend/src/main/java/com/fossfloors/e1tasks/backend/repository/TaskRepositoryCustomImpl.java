package com.fossfloors.e1tasks.backend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.annotations.QueryHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fossfloors.e1tasks.backend.entity.Task;

@Repository
@Transactional
public class TaskRepositoryCustomImpl implements TaskRepositoryCustom {

  private static final Logger logger = LoggerFactory.getLogger(TaskRepositoryCustomImpl.class);

  @PersistenceContext
  private EntityManager       entityManager;

  @Override
  // @formatter:off
  public List<Task> findAll() {
    return entityManager
        .createQuery("select distinct t from Task t " +
            "left join fetch t.toReferences " +
            "left join fetch t.fromReferences", Task.class)
        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
        .getResultList();
  }
  // @formatter:on

  @Override
  // @formatter:off
  public List<Task> findAllForTaskView(String taskView) {
    return entityManager
        .createQuery("select distinct t from Task t " +
            "left join fetch t.toReferences " +
            "left join fetch t.fromReferences " +
            "where t.taskView = :taskView", Task.class)
        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
        .setParameter("taskView", taskView)
        .getResultList();
  }
  // @formatter:on

}
