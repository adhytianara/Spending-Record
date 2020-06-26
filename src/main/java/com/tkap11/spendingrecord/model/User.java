package com.tkap11.spendingrecord.model;

public class User {
  private Long id;
  private String userId;
  private String displayName;
  private String ingatkan;

  /**
   * Represent user.
   */
  public User(Long id, String userId, String displayName, String ingatkan) {
    this.id = id;
    this.userId = userId;
    this.displayName = displayName;
    this.ingatkan = ingatkan;
  }

  public String getIngatkan() {
    return ingatkan;
  }

  public void setIngatkan(String ingatkan) {
    this.ingatkan = ingatkan;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
}