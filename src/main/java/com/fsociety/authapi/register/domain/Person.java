package com.fsociety.authapi.register.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fsociety.authapi.catalog.domain.Catalog;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDateTime;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "person")
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 3, max = 80)
    private String first_name;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 3, max = 80)
    private String last_name;

    @Column(name = "email")
    @NotNull
    @Size(min = 3, max = 80)
    @Email(message = "Invalid email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gender", referencedColumnName = "id")
    private Catalog gender;

    @Column(name = "active")
    @NotNull
    private boolean active;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    @PrePersist
    public void defaultValues() {
        this.active = true;
        var now = new Timestamp(LocalDateTime.now().toDateTime().getMillis());
        this.created_at = now;
        this.updated_at = now;
    }

    @PreUpdate
    public void updateValues() {
        this.updated_at = new Timestamp(LocalDateTime.now().toDateTime().getMillis());
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }

}
