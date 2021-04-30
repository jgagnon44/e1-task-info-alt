package com.fossfloors.e1tasks.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fossfloors.e1tasks.backend.entity.VariantDetail;

@Repository
public interface VariantDetailRepository extends JpaRepository<VariantDetail, Long> {
}
