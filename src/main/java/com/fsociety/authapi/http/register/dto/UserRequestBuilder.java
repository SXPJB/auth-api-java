package com.fsociety.authapi.http.register.dto;

import static com.fsociety.authapi.utils.PasswordUtils.encryptPassword;

import com.fsociety.authapi.app.catalog.CatalogService;
import com.fsociety.authapi.domain.Catalog;
import com.fsociety.authapi.domain.Person;
import com.fsociety.authapi.domain.User;
import com.fsociety.authapi.utils.NotFoundException;

public class UserRequestBuilder {
  private final User user;

  public UserRequestBuilder() {
    this.user = new User();
  }

  public UserRequestBuilder fromUser(User user) {
    this.user.setUsername(user.getUsername());
    this.user.setPassword(user.getPassword());
    this.user.setPerson(user.getPerson());
    return this;
  }

  public UserRequestBuilder fromUserRegisterDTO(UserRequestDTO dto) {
    user.setUsername(dto.getUsername());
    user.setPassword(encryptPassword(dto.getPassword()));

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
    user.generateConfirmCode();
    user.setConfirmCodeExpiresIn2h();
    return this;
  }

  public User build() {
    return user;
  }

  private Catalog getGender(String code, CatalogService genderService) throws NotFoundException {
    return genderService.findByGenderByCode(code);
  }
}
