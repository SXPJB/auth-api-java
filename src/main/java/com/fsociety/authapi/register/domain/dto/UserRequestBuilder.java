package com.fsociety.authapi.register.domain.dto;


import com.fsociety.authapi.catalog.app.CatalogService;
import com.fsociety.authapi.catalog.domain.Catalog;
import com.fsociety.authapi.register.domain.Person;
import com.fsociety.authapi.register.domain.User;
import com.fsociety.authapi.utils.NotFoundException;
import org.joda.time.LocalDateTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;

public class UserRequestBuilder {
    private final User user;


    public UserRequestBuilder() {
        this.user = new User();
    }

    public UserRequestBuilder fromUserRegisterDTO(UserRequestDTO dto) {
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        Person person = new Person();
        person.setFirst_name(dto.getFirstName());
        person.setLast_name(dto.getLastName());
        person.setEmail(dto.getEmail());
        user.setPerson(person);
        return this;
    }


    public UserRequestBuilder withGender(String code, CatalogService genderService) throws NotFoundException {
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
