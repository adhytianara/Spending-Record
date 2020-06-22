package com.tkap11.spendingrecord.model;

public class Budget {

  private Long budget;
  private Long sisabudget;
  private String userId;
  private String category;

  /**
   * User budget.
   */
  public Budget(String userId, String category, Long budget) {
    this.userId = userId;
    this.category = category;
    this.budget = budget;
  }

  public Long getBudget() {
    return budget;
  }

  public void setBudget(Long budget) {
    this.budget = budget;
  }

  public Long getSisabudget() {
    return sisabudget;
  }

  public void setSisabudget(Long sisabudget) {
    this.sisabudget = sisabudget;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
}