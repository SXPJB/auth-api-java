package com.fsociety.authapi.catalog.app;

import com.fsociety.authapi.catalog.domain.Catalog;
import com.fsociety.authapi.utils.NotFoundException;

public interface CatalogService {
    Catalog findByGenderByCode(String catalog) throws NotFoundException;
}
