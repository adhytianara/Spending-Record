package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.Spending;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class SpendingDaoImplementation implements SpendingDao {

  private static final String SPENDING_RECORD_TABLE = "tbl_spending";
  private static final String SQL_SELECT_ALL = "SELECT * FROM " + SPENDING_RECORD_TABLE;
  private static final String SQL_SELECT_SISA = "SELECT * FROM " + SPENDING_RECORD_TABLE
      + " " + "WHERE LOWER(user_id) LIKE LOWER(?) AND LOWER(category) = LOWER(?);";
  private static final String SQL_GET_BY_USER_ID = SQL_SELECT_ALL
      + " WHERE LOWER(user_id) LIKE LOWER(?);";
  private static final String SQL_REGISTER = "INSERT INTO " + SPENDING_RECORD_TABLE
      + " (user_id, display_name, category, timestamp, nominal) VALUES (?, ?, ?, ?, ?);";
  private static final ResultSetExtractor<List<Spending>> MULTIPLE_RS_EXTRACTOR =
      new ResultSetExtractor<List<Spending>>() {
        @Override
        public List<Spending> extractData(ResultSet resultSet)
            throws SQLException, DataAccessException {
          List<Spending> list = new Vector<Spending>();
          while (resultSet.next()) {
            Spending sp = new Spending(
                resultSet.getLong("id"),
                resultSet.getString("user_id"),
                resultSet.getString("display_name"),
                resultSet.getString("category"),
                resultSet.getString("timestamp"),
                resultSet.getString("nominal"));
            list.add(sp);
          }
          return list;
        }
      };
  private JdbcTemplate jdbcTemplate;

  public SpendingDaoImplementation(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public List<Spending> get() {
    return jdbcTemplate.query(SQL_SELECT_ALL, MULTIPLE_RS_EXTRACTOR);
  }

  @Override
  public List<Spending> getByUserId(String userId) {
    return jdbcTemplate.query(SQL_GET_BY_USER_ID, new Object[]{"%" + userId + "%"},
        MULTIPLE_RS_EXTRACTOR);
  }

  @Override
  public List<Spending> getSisa(String userId, String category) {
    return jdbcTemplate.query(SQL_SELECT_SISA,
        new Object[]{"%" + userId + "%", category}, MULTIPLE_RS_EXTRACTOR);
  }

  @Override
  public int saveRecord(String userId, String displayName, String category,
                        String timestamp, String nominal) {
    return jdbcTemplate.update(SQL_REGISTER, userId, displayName, category, timestamp, nominal);
  }
}