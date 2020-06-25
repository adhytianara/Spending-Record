package com.tkap11.spendingrecord.repository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tkap11.spendingrecord.database.BudgetDao;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SisaDatabaseTest {
  @Mock
  private BudgetDao budgetDao;

  @InjectMocks
  private SisaDatabase sisaDatabase;

  @Test
  void sisaBudget() {
    String description = "userId;category";
    sisaDatabase.sisaBudget(description);
    when(budgetDao.getSisa("userId", "category"))
        .thenReturn(new ArrayList<>());
    verify(budgetDao).getSisa("userId", "category");
    Assert.assertTrue(sisaDatabase.sisaBudget(description) instanceof ArrayList);
  }

}
