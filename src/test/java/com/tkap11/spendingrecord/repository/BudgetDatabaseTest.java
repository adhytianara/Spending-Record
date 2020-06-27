package com.tkap11.spendingrecord.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import com.tkap11.spendingrecord.database.BudgetDao;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BudgetDatabaseTest {

  @InjectMocks
  private BudgetDatabase budgetDatabase;

  @Mock
  private BudgetDao budgetDao;

  @Test
  void setBudget() {
    budgetDatabase.setBudget("userId",
    "category", "2020-06", 50000);
    when(budgetDao.setBudget("userId",
        "category", "2020-06", 50000))
        .thenReturn(1);
    budgetDao.setBudget("userId",
        "category", "2020-06", 50000);
    verify(budgetDao, times(2)).setBudget("userId",
        "category", "2020-06", 50000);
  }

  @Test
  void getBudget() {
    budgetDatabase.getBudget("userId", "category");
    when(budgetDao.getBudget("userId", "category"))
        .thenReturn(new ArrayList<>());
    verify(budgetDao).getBudget("userId", "category");
    Assert.assertTrue(budgetDatabase.getBudget("userId", "category") instanceof ArrayList);
  }
}