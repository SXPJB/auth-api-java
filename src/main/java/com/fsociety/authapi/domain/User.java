package com.fsociety.authapi.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDateTime;

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

  private String username;
  private String password;
  private String token;
  private Boolean isConfirmed;
  private String confirmationCode;
  private Timestamp confirmationCodeExpires;
  private Boolean active;
  private Timestamp createdAt;
  private Timestamp updatedAt;

  @PrePersist
  public void defaultValues() {
    this.active = true;
    this.isConfirmed = true;
    var now = new Timestamp(LocalDateTime.now().toDateTime().getMillis());
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PreUpdate
  public void updateValues() {
    this.updatedAt = new Timestamp(LocalDateTime.now().toDateTime().getMillis());
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
