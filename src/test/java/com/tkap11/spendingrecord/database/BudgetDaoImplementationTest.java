package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.Budget;
import com.tkap11.spendingrecord.model.Spending;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BudgetDaoImplementationTest {
  @Mock
  private JdbcTemplate jdbcTemplate;

  @Mock
  private ResultSet resultSet;

  @InjectMocks
  BudgetDaoImplementation dao = new BudgetDaoImplementation(getDataSource());

  DataSource getDataSource() {
    String dbUrl = System.getenv("JDBC_DATABASE_URL");
    String username = System.getenv("JDBC_DATABASE_USERNAME");
    String password = System.getenv("JDBC_DATABASE_PASSWORD");

    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName("org.postgresql.Driver");
    ds.setUrl(dbUrl);
    ds.setUsername(username);
    ds.setPassword(password);

    return ds;
  }

  @Test
  void getAll() {
    List<Budget> budgets = dao.getAll();
    Assert.assertNull(budgets);
  }

  @Test
  void getByUserId() {
    List<Budget> budgets = dao.getByUserId("userId");
    Assert.assertNull(budgets);
  }

  @Test
  void getSisa() {
    List<Budget> budgets = dao.getSisa("userId", "category");
    Assert.assertNull(budgets);
  }

  @Test
  void getBudget() {
    List<Budget> budgets = dao.getBudget("userId", "category");
    Assert.assertNull(budgets);
  }

  @Test
  void setBudget() {
    int setBudget = dao.setBudget("userId", "displayName", "juni",
        10000);
    Assert.assertEquals(setBudget, 0);
  }

  @Test
  void extractData() throws SQLException {
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    List<Budget> budgets = dao.extractData(resultSet);
    Assert.assertNotNull(budgets);
  }
}