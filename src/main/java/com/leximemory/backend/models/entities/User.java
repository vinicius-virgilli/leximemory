package com.leximemory.backend.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String email;
  private String password;
  private Timestamp registrationDate;

  public User() {
  }

  public User(Integer id, String name, String email, String password, Timestamp registrationDate) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.registrationDate = registrationDate;
  }

  public User(String name, String email, String encodedPassword, Timestamp timestamp) {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Timestamp getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(Timestamp registrationDate) {
    this.registrationDate = registrationDate;
  }
}
