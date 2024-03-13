package com.fsociety.authapi.app.catalog.impl;

import com.fsociety.authapi.app.catalog.CatalogService;
import com.fsociety.authapi.domain.Catalog;
import com.fsociety.authapi.domain.CatalogRepository;
import com.fsociety.authapi.utils.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CatalogServiceImpl implements CatalogService {
  private final CatalogRepository catalogRepository;

  public CatalogServiceImpl(CatalogRepository catalogRepository) {
    this.catalogRepository = catalogRepository;
  }

  @Override
  public Catalog findByGenderByCode(String code) throws NotFoundException {
    var genderCatalog = "c_gender";
    return catalogRepository
        .findByCatalogAndCodeAndActiveIsTrue(genderCatalog, code)
        .orElseThrow(() -> new NotFoundException("Gender", "Catalog not found"));
  }
}
