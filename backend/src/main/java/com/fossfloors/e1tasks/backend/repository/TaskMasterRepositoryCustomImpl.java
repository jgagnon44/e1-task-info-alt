package com.fossfloors.e1tasks.backend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fossfloors.e1tasks.backend.entity.TaskMaster;

@Repository
@Transactional
public class TaskMasterRepositoryCustomImpl implements TaskMasterRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  // @SuppressWarnings("unchecked")
  // @Override
  // public List<E1Task2> findAll() {
  // return entityManager.createQuery("select distinct t from E1Task t left join fetch
  // t.childTasks")
  // .setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
  // }

  @SuppressWarnings("unchecked")
  @Override
  public List<TaskMaster> findAllRootTasks() {
    return entityManager.createQuery(
        "select distinct t from E1Task t left join fetch t.childTasks c where t.parentTask is null")
        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
  }

  // @Override
  // public boolean hasChildren(E1Task2 item) {
  // // TODO Auto-generated method stub
  // return false;
  // }
  //
  // @Override
  // public int getChildCount(E1Task2 parent) {
  // // TODO Auto-generated method stub
  // return 0;
  // }
  //
  // @Override
  // public List<E1Task2> getChildren(E1Task2 parent) {
  // // TODO Auto-generated method stub
  // return null;
  // }

}
