package com.tkap11.spendingrecord.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
  User user;

  @BeforeEach
  void setUp() {
    user = new User(Long.parseLong("1"), "userid", "adhytia");
  }

  @Test
  void getId() {
    Long id = user.getId();
    assertEquals(id, 1);
  }

  @Test
  void setId() {
    user.setId(Long.parseLong("2"));
    Long id = user.getId();
    assertEquals(id, 2);
  }

  @Test
  void getUserId() {
    String idUser = user.getUserId();
    assertEquals(idUser, "userid");
  }

  @Test
  void setUserId() {
    user.setUserId("changeUserId");
    String idUser = user.getUserId();
    assertEquals(idUser, "changeUserId");
  }

  @Test
  void getDisplayName() {
    String displayName = user.getDisplayName();
    assertEquals(displayName, "adhytia");
  }

  @Test
  void setDisplayName() {
    user.setDisplayName("spendingrecord");
    String displayName = user.getDisplayName();
    assertEquals(displayName, "spendingrecord");
  }
}