package com.fsociety.authapi.catalog.domain;

import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Gender {
    MALE("M", "Male"),
    FEMALE("F", "Female"),
    ;

    private final String catalog = "c_gender";
    private final String code;
    private final String description;


    Gender(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final Map<String, Gender> CATALOG_TO_GENDERS = Stream.of(values())
            .collect(Collectors.toMap(Gender::getCatalog, gender -> gender));

    public static String getCatalogByCode(String code) {
        return CATALOG_TO_GENDERS.get(code).getCatalog();
    }
}
