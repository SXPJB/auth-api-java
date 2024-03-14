package com.fsociety.authapi.unit.app.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.fsociety.authapi.app.catalog.impl.CatalogServiceImpl;
import com.fsociety.authapi.domain.Catalog;
import com.fsociety.authapi.domain.CatalogRepository;
import com.fsociety.authapi.utils.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CatalogServiceImplTests {

  @Mock private CatalogRepository catalogRepository;
  @InjectMocks private CatalogServiceImpl catalogServiceImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findByGenderByCodeSuccess() throws NotFoundException {
    var code = "F";
    var catalog = "c_gender";
    var gender = new Catalog(UUID.randomUUID(), "c_gender", code, "Female", true);

    when(catalogRepository.findByCatalogAndCodeAndActiveIsTrue(catalog, code))
        .thenReturn(Optional.of(gender));

    var result = catalogServiceImpl.findByGenderByCode(code);

    assertEquals(gender, result);
  }

  @Test
  void findByGenderByCodeFailure() {
    var code = "X";
    var catalog = "c_gender";
    when(catalogRepository.findByCatalogAndCodeAndActiveIsTrue(catalog, code))
        .thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> catalogServiceImpl.findByGenderByCode(code));
  }

  @Test
  void findAllGendersSuccess() throws NotFoundException {
    var catalog = "c_gender";
    var catalogs = buildListCatalog();
    when(catalogRepository.findAllByCatalogAndActiveIsTrue(catalog))
        .thenReturn(Optional.of(catalogs));

    var result = catalogServiceImpl.findAllGenders();
    assertEquals(catalogs.size(), result.size());
  }

  @Test
  void findAllGendersFailure() {
    var catalog = "wrongCatalog";
    when(catalogRepository.findAllByCatalogAndActiveIsTrue(catalog)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> catalogServiceImpl.findAllGenders());
  }

  private List<Catalog> buildListCatalog() {
    return List.of(
        new Catalog(UUID.randomUUID(), "c_gender", "M", "Male", true),
        new Catalog(UUID.randomUUID(), "c_gender", "F", "Female", true));
  }
}
