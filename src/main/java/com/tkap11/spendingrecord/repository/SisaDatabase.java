package com.tkap11.spendingrecord.repository;

import com.tkap11.spendingrecord.database.BudgetDao;
import com.tkap11.spendingrecord.model.Budget;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SisaDatabase {

  @Autowired
  private BudgetDao budgetDao;

  /**
   * Save user record to database.
   */
  public List<Budget> sisaBudget(String description) {
    String[] userData = description.split(";");
    return budgetDao.getSisa(userData[0], userData[1]);
  }
}