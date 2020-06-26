package com.tkap11.spendingrecord.database;

import static org.mockito.Mockito.when;

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

@ExtendWith(MockitoExtension.class)
class UserDaoImplementationTest {
  @Mock
  private JdbcTemplate jdbcTemplate;

  @Mock
  private ResultSet resultSet;

  @InjectMocks
  UserDaoImplementation userDaoImplementation = new UserDaoImplementation(getDataSource());

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
    List<User> users = userDaoImplementation.get();
    Assert.assertNull(users);
  }

  @Test
  void getByUserId() {
    List<User> users = userDaoImplementation.getByUserId("userId");
    Assert.assertNull(users);
  }

  @Test
  void registerUser() {
    int register = userDaoImplementation.registerUser("userId", "displayName");
    Assert.assertEquals(register, 0);
  }

  @Test
  void statusIngatkanbyUserId() {
    userDaoImplementation.setStatusIngatkanbyUserId("true", "userId");
    List<User> userList = userDaoImplementation.getStatusIngatkanbyUserId("userId");
    Assert.assertNull(userList);
    userList = userDaoImplementation.getAllUserIngatkanAktif();
    Assert.assertNull(userList);
  }

  @Test
  void extractData() throws SQLException {
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    List<User> users = userDaoImplementation.extractData(resultSet);
    Assert.assertNotNull(users);
  }
}