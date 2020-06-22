package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.Budget;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class BudgetDaoImplementation implements BudgetDao {

  private JdbcTemplate jdbcTemplate;

  private static final String BUDGET_TABLE = "tbl_budget";
  private static final String SQL_SELECT_ALL = "SELECT * FROM " + BUDGET_TABLE;
  private static final String SQL_SELECT_NOMINAL = "SELECT * FROM" + BUDGET_TABLE
      + " WHERE LOWER(user_id) LIKE LOWER(?), LOWER(category) = LOWER(?);";
  private static final String SQL_GET_BY_USER_ID = SQL_SELECT_ALL
      + " WHERE LOWER(user_id) LIKE LOWER(?);";
  private static final String SQL_UPSERT = "INSERT INTO " + BUDGET_TABLE
      + " (user_id, category, nominal) VALUES (?, ?, ?) ON CONFLICT UPDATE;";

  public BudgetDaoImplementation(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  private static final ResultSetExtractor<List<Budget>> MULTIPLE_RS_EXTRACTOR =
      new ResultSetExtractor<List<Budget>>() {
        @Override
        public List<Budget> extractData(ResultSet resultSet)
            throws SQLException, DataAccessException {
          List<Budget> list = new Vector<Budget>();
          while (resultSet.next()) {
            Budget sp = new Budget(
                resultSet.getString("user_id"),
                resultSet.getString("category"),
                resultSet.getLong("budget"));
            list.add(sp);
          }
          return list;
        }
      };

  @Override
  public List<Budget> getAll() {
    return jdbcTemplate.query(SQL_SELECT_ALL, MULTIPLE_RS_EXTRACTOR);
  }

  @Override
  public List<Budget> getByUserId(String userId) {
    return jdbcTemplate.query(SQL_GET_BY_USER_ID,
        new Object[]{"%" + userId + "%"}, MULTIPLE_RS_EXTRACTOR);
  }

  @Override
  public List<Budget> getBudget(String userId, String category) {
    return jdbcTemplate.query(SQL_SELECT_NOMINAL,
        new Object[]{"%" + userId + "%", category}, MULTIPLE_RS_EXTRACTOR);
  }

  @Override
  public int setBudget(String userId, String category, Long nominal) {
    return jdbcTemplate.update(SQL_UPSERT, new Object[]{userId, category, nominal});
  }
}