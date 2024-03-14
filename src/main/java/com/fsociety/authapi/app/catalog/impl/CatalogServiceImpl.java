package com.fsociety.authapi.app.catalog.impl;

import com.fsociety.authapi.app.catalog.CatalogService;
import com.fsociety.authapi.domain.Catalog;
import com.fsociety.authapi.domain.CatalogRepository;
import com.fsociety.authapi.http.catalog.dto.GenderResponseDTO;
import com.fsociety.authapi.utils.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CatalogServiceImpl implements CatalogService {
  private final CatalogRepository catalogRepository;

  public CatalogServiceImpl(CatalogRepository catalogRepository) {
    this.catalogRepository = catalogRepository;
  }

  @Override
  public Catalog findByGenderByCode(String code) throws NotFoundException {
    var genderCatalogKey = "c_gender";
    return catalogRepository
        .findByCatalogAndCodeAndActiveIsTrue(genderCatalogKey, code)
        .orElseThrow(() -> new NotFoundException("Gender", "Catalog not found"));
  }

  @Override
  public List<GenderResponseDTO> findAllGenders() throws NotFoundException {
    var genderCatalogKey = "c_gender";
    var catalogs = catalogRepository.findAllByCatalogAndActiveIsTrue(genderCatalogKey);

    if (catalogs.isEmpty()) {
      throw new NotFoundException("Catalog not found");
    }

    return GenderResponseDTO.mapFromCatalog(catalogs.get());
  }
}
