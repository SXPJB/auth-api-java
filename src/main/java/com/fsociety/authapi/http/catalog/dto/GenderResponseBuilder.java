package com.fsociety.authapi.http.catalog.dto;

import com.fsociety.authapi.domain.Catalog;

public class GenderResponseBuilder {
  private final GenderResponseDTO gender;

  public GenderResponseBuilder() {
    this.gender = new GenderResponseDTO();
  }

  public GenderResponseBuilder fromCatalog(Catalog catalog) {
    this.gender.setCode(catalog.getCode());
    this.gender.setDisplay(catalog.getDescription());
    return this;
  }

  public GenderResponseDTO build() {
    return this.gender;
  }
}
