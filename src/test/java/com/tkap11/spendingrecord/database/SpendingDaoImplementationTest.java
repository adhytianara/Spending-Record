package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.Spending;
import com.tkap11.spendingrecord.model.User;
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
class SpendingDaoImplementationTest {
  @Mock
  private JdbcTemplate jdbcTemplate;

  @Mock
  private ResultSet resultSet;

  @InjectMocks
  SpendingDaoImplementation dao = new SpendingDaoImplementation(getDataSource());

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
  void get() {
    List<Spending> spendings = dao.get();
    Assert.assertNull(spendings);
  }

  @Test
  void getByUserId() {
    List<Spending> spendings = dao.getByUserId("userId");
    Assert.assertNull(spendings);
  }

  @Test
  void saveRecord() {
    int save = dao.saveRecord("userId", "displayName", "category",
        "timestamp", "nominal");
    Assert.assertEquals(save, 0);
  }

  @Test
  void extractData() throws SQLException {
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    List<Spending> spendings = dao.extractData(resultSet);
    Assert.assertNotNull(spendings);
  }
}