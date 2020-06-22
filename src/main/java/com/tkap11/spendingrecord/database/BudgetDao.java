package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.Budget;
import java.util.List;

public interface BudgetDao {
  public List<Budget> getAll();

  public List<Budget> getByUserId(String userId);

  public List<Budget> getBudget(String userId, String category);

  public int setBudget(String userId, String category, int nominal);
}