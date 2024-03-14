package com.fsociety.authapi.http.catalog;

import com.fsociety.authapi.app.catalog.CatalogService;
import com.fsociety.authapi.http.catalog.dto.GenderResponseDTO;
import com.fsociety.authapi.utils.NotFoundException;
import com.fsociety.authapi.utils.ResponseBody;
import com.fsociety.authapi.utils.ResponseEntityBuilder;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/catalog")
@AllArgsConstructor
public class CatalogEndpoint {

  private final CatalogService catalogService;

  @GetMapping("/findAllGenders")
  public ResponseEntity<ResponseBody<List<GenderResponseDTO>>> getGenders()
      throws NotFoundException {
    var catalogs = catalogService.findAllGenders();
    return new ResponseEntityBuilder<List<GenderResponseDTO>>()
        .withSuccess(true)
        .withData(catalogs)
        .build();
  }
}
