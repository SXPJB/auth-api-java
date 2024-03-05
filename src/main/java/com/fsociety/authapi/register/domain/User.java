package com.fsociety.authapi.register.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDateTime;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    Person person;

    @Column(name = "username")
    @NotNull
    @Size(min = 4, max = 80)
    private String username;

    @Column(name = "password")
    @NotNull
    @Size(min = 8, max = 80)
    private String password;

    @Column(name = "token")
    private String token;

    @Column(name = "is_confirmed")
    private Boolean isConfirmed;

    @Column(name = "confirmation_code")
    private String confirmationCode;

    @Column(name = "confirmation_code_expires")
    private Timestamp confirmationCodeExpires;

    @Column(name = "active")
    @NotNull
    private Boolean active;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    @PrePersist
    public void defaultValues() {
        this.active = true;
        this.isConfirmed = true;
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
