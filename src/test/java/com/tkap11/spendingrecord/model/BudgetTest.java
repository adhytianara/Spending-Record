package com.tkap11.spendingrecord.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BudgetTest {
  Budget budget;

  @BeforeEach
  void setUp() {
    budget = new Budget("a123", "makanan", (long) 50000);
    budget.setSisabudget((long) 25000);
  }

  @Test
  void getBudget() {
    assertEquals((long) 50000, budget.getBudget());
  }

  @Test
  void setBudget() {
    budget.setBudget((long) 25000);
    assertEquals((long) 25000, budget.getBudget());
  }

  @Test
  void getSisabudget() {
    assertEquals((long) 25000, budget.getSisabudget());
  }

  @Test
  void setSisabudget() {
    budget.setSisabudget((long) 5000);
    assertEquals((long) 5000, budget.getSisabudget());
  }

  @Test
  void getUserId() {
    assertEquals("a123", budget.getUserId());
  }

  @Test
  void setUserId() {
    budget.setUserId("ahaha");
    assertEquals("ahaha", budget.getUserId());
  }

  @Test
  void getCategory() {
    assertEquals("makanan", budget.getCategory());
  }

  @Test
  void setCategory() {
    budget.setCategory("bukan makanan");
    assertEquals("bukan makanan", budget.getCategory());
  }
}