package com.fsociety.authapi.catalog.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, UUID> {
    Optional<Catalog> findByCatalogAndCodeAndActiveIsTrue(String catalog, String code);
}
