package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.Budget;
import java.util.List;

public interface BudgetDao {
  List<Budget> getAll();

  List<Budget> getByUserId(String userId);

  List<Budget> getBudget(String userId, String category);

  int setBudget(String userId, String category, int nominal);

  List<Budget> getSisa(String userId, String category);
}