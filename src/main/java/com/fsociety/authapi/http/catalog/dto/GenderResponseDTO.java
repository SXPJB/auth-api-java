package com.fsociety.authapi.http.catalog.dto;

import com.fsociety.authapi.domain.Catalog;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenderResponseDTO {
  String code;
  String display;

  public static List<GenderResponseDTO> mapFromCatalog(List<Catalog> catalog) {
    return catalog.stream().map(GenderResponseDTO::mapFromCatalog).collect(Collectors.toList());
  }

  private static GenderResponseDTO mapFromCatalog(Catalog catalog) {
    return new GenderResponseBuilder().fromCatalog(catalog).build();
  }
}
