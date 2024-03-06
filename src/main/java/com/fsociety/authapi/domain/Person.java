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
@Table(name = "person")
@Getter
@Setter
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String firstName;
  private String lastName;
  private String email;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_gender", referencedColumnName = "id")
  private Catalog gender;

  private boolean active;
  private Timestamp created_at;
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
