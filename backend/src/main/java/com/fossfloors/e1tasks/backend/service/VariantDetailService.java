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

import com.fossfloors.e1tasks.backend.entity.VariantDetail;
import com.fossfloors.e1tasks.backend.repository.VariantDetailRepository;

@Service
@Transactional
public class VariantDetailService {

  private static final Logger           logger = LoggerFactory
      .getLogger(VariantDetailService.class);

  private final VariantDetailRepository repo;

  @PersistenceContext
  private EntityManager                 entityManager;

  @Autowired
  public VariantDetailService(VariantDetailRepository repo) {
    this.repo = repo;
  }

  public Set<VariantDetail> findAll() {
    return Set.copyOf(repo.findAll());
  }

  public void saveAll(Set<VariantDetail> variantDetailSet) {
    repo.saveAll(variantDetailSet);
  }

  // @formatter:off
  public List<String> getDistinctVariantNames() {
    return entityManager
        .createQuery("select distinct t.variantName from VariantDetail t", String.class)
        .getResultList();
  }
  // @formatter:on

  // @formatter:off
  public List<String> getTopLevelParentTaskIDs(String variantName) {
    List<String> distinctParentIDs = getDistinctParentTaskIDs(variantName);
    List<String> distinctChildIDs = getDistinctChildTaskIDs(variantName);
    return distinctParentIDs.stream()
        .filter(id -> !distinctChildIDs.contains(id))
        .collect(Collectors.toList());
  }
  // @formatter:on

  // @formatter:off
  public List<VariantDetail> getChildRelationsForVariantAndParent(String variantName, String parentID) {
    return entityManager
        .createQuery("select t from VariantDetail t where t.variantName = :variant" +
            " and t.parentTaskID = :parentID", VariantDetail.class)
        .setParameter("variant", variantName)
        .setParameter("parentID", parentID)
        .getResultList();
  }
  // @formatter:on

  // @formatter:off
  private List<String> getDistinctParentTaskIDs(String variantName) {
    return entityManager
        .createQuery("select distinct t.parentTaskID from VariantDetail t" +
            " where t.variantName = :variant", String.class)
        .setParameter("variant", variantName)
        .getResultList();
  }
  // @formatter:on

  // @formatter:off
  private List<String> getDistinctChildTaskIDs(String variantName) {
    return entityManager
        .createQuery("select distinct t.childTaskID from VariantDetail t" +
            " where t.variantName = :variant", String.class)
        .setParameter("variant", variantName)
        .getResultList();
  }
  // @formatter:on

  // @formatter:off
  public List<String> getDistinctTaskViewIDsForVariant(String variantName) {
    return entityManager
        .createQuery("select distinct t.taskView from VariantDetail t" +
            " where t.variantName = :name", String.class)
        .setParameter("name", variantName)
        .getResultList();
  }
  // @formatter:on

  // @formatter:off
  public List<VariantDetail> getTaskExclusionsForVariantAndTaskView
    (String variantName, String taskView) {
    return entityManager
        .createQuery("select t from VariantDetail t" +
            " where t.variantName = :name and t.taskView = :view", VariantDetail.class)
        .setParameter("name", variantName)
        .setParameter("view", taskView)
        .getResultList();
  }
  // @formatter:on

}
