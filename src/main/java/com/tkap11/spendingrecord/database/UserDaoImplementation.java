package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;


public class UserDaoImplementation implements UserDao {

  private static final String USER_TABLE = "tbl_user";
  private static final String SQL_SELECT_ALL = "SELECT * FROM " + USER_TABLE;
  private static final String SQL_GET_BY_USER_ID = SQL_SELECT_ALL
      + " WHERE LOWER(user_id) LIKE LOWER(?);";
  private static final String SQL_GET_STATUS_INGATKAN = SQL_SELECT_ALL
      + " WHERE LOWER(user_id) LIKE LOWER(?);";
  private static final String SQL_GET_ALL_STATUS_INGATKAN = SQL_SELECT_ALL
      + " WHERE status_ingatkan='true';";
  private static final String SQL_REGISTER = "INSERT INTO "
      + USER_TABLE + " (user_id, display_name, status_ingatkan) VALUES (?, ?, ?);";
  private static final String SQL_UPDATE_STATUS = "UPDATE " + USER_TABLE
      + " SET status_ingatkan = ? WHERE LOWER(user_id) LIKE LOWER(?);";
  private static final ResultSetExtractor<List<User>> MULTIPLE_RS_EXTRACTOR =
      UserDaoImplementation::extractData;
  private JdbcTemplate jdbcTemplate;

  public UserDaoImplementation(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  /**
   * Extract data for user table.
   */
  public static List<User> extractData(ResultSet resultSet) throws SQLException {
    List<User> list = new Vector<User>();
    while (resultSet.next()) {
      User p = new User(
          resultSet.getLong("id"),
          resultSet.getString("user_id"),
          resultSet.getString("display_name"),
          resultSet.getString("status_ingatkan"));
      list.add(p);
    }
    return list;
  }

  @Override
  public List<User> get() {
    return jdbcTemplate.query(SQL_SELECT_ALL, MULTIPLE_RS_EXTRACTOR);
  }

  @Override
  public List<User> getByUserId(String userId) {
    return jdbcTemplate.query(SQL_GET_BY_USER_ID, new Object[]{"%" + userId + "%"},
        MULTIPLE_RS_EXTRACTOR);
  }

  @Override
  public List<User> getStatusIngatkanbyUserId(String userId) {
    return jdbcTemplate.query(SQL_GET_STATUS_INGATKAN, new Object[]{"%" + userId + "%"},
        MULTIPLE_RS_EXTRACTOR);
  }

  @Override
  public int registerUser(String userId, String displayName) {
    return jdbcTemplate.update(SQL_REGISTER, userId, displayName, "false");
  }

  @Override
  public int setStatusIngatkanbyUserId(String status, String userId) {
    return jdbcTemplate.update(SQL_UPDATE_STATUS,status, "%" + userId + "%");
  }

  @Override
  public List<User> getAllUserIngatkanAktif() {
    return jdbcTemplate.query(SQL_GET_ALL_STATUS_INGATKAN, MULTIPLE_RS_EXTRACTOR);
  }
}
