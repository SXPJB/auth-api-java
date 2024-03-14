package com.fsociety.authapi.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, UUID> {
  Optional<Catalog> findByCatalogAndCodeAndActiveIsTrue(String catalog, String code);

  Optional<List<Catalog>> findAllByCatalogAndActiveIsTrue(String catalog);
}
