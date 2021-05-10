package com.fossfloors.e1tasks.backend.service;

import java.util.List;
import java.util.Set;

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

  public List<VariantDetail> findAll() {
    return repo.findAll();
  }

  public void saveAll(Set<VariantDetail> variantDetailSet) {
    repo.saveAll(variantDetailSet);
  }

  // @formatter:off
  public List<String> findVariantNames() {
    return entityManager
        .createQuery("select distinct t.variantName from VariantDetail t", String.class)
        .getResultList();
  }
  // @formatter:on

  // @formatter:off
  public List<VariantDetail> getRelationsForVariant(String variant) {
    return entityManager
        .createQuery("select t from VariantDetail t where t.variantName = :variant", VariantDetail.class)
        .setParameter("variant", variant)
        .getResultList();
  }
  // @formatter:on

  // @formatter:off
  public List<VariantDetail> getChildRelationsForParent(String parentID) {
    return entityManager
        .createQuery("select t from VariantDetail t where t.parentTaskID = :parentID", VariantDetail.class)
        .setParameter("parentID", parentID)
        .getResultList();
  }
  // @formatter:on

}
