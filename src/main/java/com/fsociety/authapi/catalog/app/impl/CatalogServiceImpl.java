package com.fsociety.authapi.catalog.app.impl;

import com.fsociety.authapi.catalog.app.CatalogService;
import com.fsociety.authapi.catalog.domain.Catalog;
import com.fsociety.authapi.catalog.domain.CatalogRepository;
import com.fsociety.authapi.catalog.domain.Gender;
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
        return catalogRepository.findByCatalogAndCodeAndActiveIsTrue(genderCatalog, code)
                .orElseThrow(() -> new NotFoundException("Gender", "Catalog not found"));
    }
}
