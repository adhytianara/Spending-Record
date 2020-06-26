package com.tkap11.spendingrecord.repository;

import com.tkap11.spendingrecord.database.BudgetDao;
import com.tkap11.spendingrecord.model.Budget;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BudgetDatabase {

  @Autowired
  private BudgetDao budgetDao;

  public int setBudget(String userId, String category, String period, int budget) {
    return budgetDao.setBudget(userId, category, period, budget);
  }

  public List<Budget> getBudget(String userId, String category) {
    return budgetDao.getBudget(userId, category);
  }

}
