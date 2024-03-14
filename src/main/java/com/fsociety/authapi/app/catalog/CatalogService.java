package com.fsociety.authapi.app.catalog;

import com.fsociety.authapi.domain.Catalog;
import com.fsociety.authapi.http.catalog.dto.GenderResponseDTO;
import com.fsociety.authapi.utils.NotFoundException;
import java.util.List;

public interface CatalogService {
  Catalog findByGenderByCode(String catalog) throws NotFoundException;

  List<GenderResponseDTO> findAllGenders() throws NotFoundException;
}
