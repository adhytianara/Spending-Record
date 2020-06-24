package com.tkap11.spendingrecord.model;

public class Budget {

  private int budget;
  private int sisaBudget;
  private String userId;
  private String category;

  /**
   * Budget constructor.
   * @param userId user id
   * @param category budget category
   * @param budget budget nominal
   */
  public Budget(String userId, String category, int budget) {
    this.userId = userId;
    this.category = category;
    this.budget = budget;
  }

  public Budget(String userId, String category, int budget, int sisaBudget) {
    this.userId = userId;
    this.category = category;
    this.budget = budget;
    this.sisaBudget = sisaBudget;
  }

  public int getBudget() {
    return budget;
  }

  public void setBudget(int budget) {
    this.budget = budget;
  }

  public int getSisabudget() {
    return this.sisaBudget;
  }

  public void setSisabudget(int sisabudget) {
    this.sisaBudget = sisabudget;
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