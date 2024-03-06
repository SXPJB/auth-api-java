package com.fsociety.authapi.app.catalog;

import com.fsociety.authapi.domain.Catalog;
import com.fsociety.authapi.utils.NotFoundException;

public interface CatalogService {
  Catalog findByGenderByCode(String catalog) throws NotFoundException;
}
