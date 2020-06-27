package com.tkap11.spendingrecord.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BudgetTest {
  Budget budget;
  Budget budget1;

  @BeforeEach
  void setUp() {
    budget = new Budget("a123", "makanan", (int) 50000);
    budget1 = new Budget("b123", "makanan", 10000, 1000, "juni");
    budget.setSisabudget((int) 25000);
  }

  @Test
  void getBudget() {
    assertEquals((int) 50000, budget.getBudget());
  }

  @Test
  void setBudget() {
    budget.setBudget((int) 25000);
    assertEquals((int) 25000, budget.getBudget());
  }

  @Test
  void getSisabudget() {
    assertEquals((int) 25000, budget.getSisabudget());
  }

  @Test
  void setSisabudget() {
    budget.setSisabudget((int) 5000);
    assertEquals((int) 5000, budget.getSisabudget());
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

  @Test
  void getPeriod() {
    assertEquals("juni", budget1.getPeriod());
  }

  @Test
  void setPeriod() {
    budget.setPeriod("juli");
    assertEquals("juli", budget.getPeriod());
  }
}