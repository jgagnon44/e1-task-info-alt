package com.fossfloors.e1tasks.backend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.annotations.QueryHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fossfloors.e1tasks.backend.beans.TaskFilter;
import com.fossfloors.e1tasks.backend.entity.TaskMaster;
import com.fossfloors.e1tasks.backend.entity.TaskType;

@Repository
@Transactional
public class TaskMasterRepositoryCustomImpl implements TaskMasterRepositoryCustom {

  private static final Logger logger = LoggerFactory
      .getLogger(TaskMasterRepositoryCustomImpl.class);

  @PersistenceContext
  private EntityManager       entityManager;

  @Override
  // @formatter:off
  public List<TaskMaster> findAll() {
    return entityManager
        .createQuery("select distinct t from TaskMaster t left join fetch t.childTasks", TaskMaster.class)
        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
        .getResultList();
  }
  // @formatter:on

  @Override
  // @formatter:off
  public List<TaskMaster> findAllRootTasks() {
    return entityManager.createQuery(
        "select distinct t from TaskMaster t left join fetch t.childTasks where t.parentTask is null", TaskMaster.class)
        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
        .getResultList();
  }
  // @formatter:on

  @Override
  // @formatter:off
  public List<TaskMaster> filter(TaskFilter filter) {
    List<TaskMaster> tasks = entityManager
        .createQuery("select distinct t from TaskMaster t left join fetch t.childTasks where 1=1 "
            + buildFilterWhereClause(filter), TaskMaster.class)
        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
        .getResultList();
    return tasks;
  }
  // @formatter:on

  private String buildFilterWhereClause(TaskFilter filter) {
    StringBuilder sb = new StringBuilder();

    if (filter.getTaskID() != null && !filter.getTaskID().isEmpty()) {
      sb.append(" and upper(t.taskID) like upper('%" + filter.getTaskID() + "%') ");
    }

    if (filter.getName() != null && !filter.getName().isEmpty()) {
      sb.append(" and upper(t.name) like upper('%" + filter.getName() + "%') ");
    }

    if (filter.getObjectName() != null && !filter.getObjectName().isEmpty()) {
      sb.append(" and upper(t.objectName) like upper('%" + filter.getObjectName() + "%') ");
    }

    if (filter.getVersion() != null && !filter.getVersion().isEmpty()) {
      sb.append(" and upper(t.version) like upper('%" + filter.getVersion() + "%') ");
    }

    if (filter.getFormName() != null && !filter.getFormName().isEmpty()) {
      sb.append(" and upper(t.formName) like upper('%" + filter.getFormName() + "%') ");
    }

    switch (filter.getType()) {
      case ALL:
        break;
      case BATCH:
        sb.append(" and t.type = '" + TaskType.BATCH.getCodeValue() + "' ");
        break;
      case FOLDER:
        sb.append(" and t.type = '" + TaskType.FOLDER.getCodeValue() + "' ");
        break;
      case INTERACTIVE:
        sb.append(" and t.type = '" + TaskType.INTERACTIVE.getCodeValue() + "' ");
        break;
      case TASK_VIEW:
        sb.append(" and t.type = '" + TaskType.TASK_VIEW.getCodeValue() + "' ");
        break;
      case URL:
        sb.append(" and t.type = '" + TaskType.URL.getCodeValue() + "' ");
        break;
      case USER_DEFINED:
        sb.append(" and t.type = '" + TaskType.USER_DEFINED.getCodeValue() + "' ");
        break;
    }

    logger.debug("WHERE CLAUSE: {}", sb.toString());
    return sb.toString();
  }

}
