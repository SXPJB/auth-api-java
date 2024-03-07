package com.fsociety.authapi.http.register.dto;

import com.fsociety.authapi.app.catalog.CatalogService;
import com.fsociety.authapi.domain.Catalog;
import com.fsociety.authapi.domain.Person;
import com.fsociety.authapi.domain.User;
import com.fsociety.authapi.utils.NotFoundException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import org.joda.time.LocalDateTime;

public class UserRequestBuilder {
  private final User user;

  public UserRequestBuilder() {
    this.user = new User();
  }

  public UserRequestBuilder fromUserRegisterDTO(UserRequestDTO dto) {
    user.setUsername(dto.getUsername());
    user.setPassword(dto.getPassword());

    Person person = new Person();
    person.setFirstName(dto.getFirstName());
    person.setLastName(dto.getLastName());
    person.setEmail(dto.getEmail());
    user.setPerson(person);
    return this;
  }

  public UserRequestBuilder withGender(String code, CatalogService genderService)
      throws NotFoundException {
    Catalog catalog = getGender(code, genderService);
    user.getPerson().setGender(catalog);
    return this;
  }

  public UserRequestBuilder withExpirationCode() {
    SecureRandom random = new SecureRandom();
    user.setConfirmationCode(new BigInteger(130, random).toString(32));
    // Set the confirmation code expiration time to 24 hours
    Timestamp expires = new Timestamp(LocalDateTime.now().plusDays(1).toDateTime().getMillis());
    user.setConfirmationCodeExpires(expires);
    return this;
  }

  public User build() {
    return user;
  }

  private Catalog getGender(String code, CatalogService genderService) throws NotFoundException {
    return genderService.findByGenderByCode(code);
  }
}
