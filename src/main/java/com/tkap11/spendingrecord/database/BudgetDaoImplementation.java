package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.Budget;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.sql.DataSource;

import com.tkap11.spendingrecord.model.Spending;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class BudgetDaoImplementation implements BudgetDao {

  private static final String BUDGET_TABLE = "tbl_budget";
  private static final String SQL_SELECT_ALL = "SELECT * FROM " + BUDGET_TABLE;
  private static final String SQL_SELECT_NOMINAL = "SELECT * FROM" + BUDGET_TABLE
          + " WHERE LOWER(user_id) LIKE LOWER(?), LOWER(category) = LOWER(?);";
  private static final String SQL_SELECT_SISA = "SELECT * FROM " + BUDGET_TABLE
      + " " + "WHERE LOWER(user_id) LIKE LOWER(?) AND LOWER(category) = LOWER(?);";
  private static final String SQL_GET_BY_USER_ID = SQL_SELECT_ALL
          + " WHERE LOWER(user_id) LIKE LOWER(?);";
  private static final String SQL_UPSERT = "INSERT INTO tbl_budget "
          + "(user_id, category, budget, sisa_budget) VALUES (?, ?, ?, ?)"
          + " ON CONFLICT (user_id, category) DO UPDATE "
          + "SET budget = EXCLUDED.budget, sisa_budget = EXCLUDED.sisa_budget;";

  private JdbcTemplate mjdbc;

  public BudgetDaoImplementation(DataSource dataSource) {
    mjdbc = new JdbcTemplate(dataSource);
  }

  private static final ResultSetExtractor<List<Budget>> MULTIPLE_RS_EXTRACTOR =
          new ResultSetExtractor<List<Budget>>() {
        @Override
        public List<Budget> extractData(ResultSet ars)
                throws SQLException, DataAccessException {
          List<Budget> list = new Vector<Budget>();
          while (ars.next()) {
            Budget sp = new Budget(
                    ars.getString("user_id"),
                    ars.getString("category"),
                    ars.getInt("budget"),
                    ars.getInt("sisa_budget"));
            list.add(sp);
          }
          return list;
        }
  };

  @Override
  public List<Budget> getAll() {
    return mjdbc.query(SQL_SELECT_ALL, MULTIPLE_RS_EXTRACTOR);
  }

  @Override
  public List<Budget> getByUserId(String userId) {
    return mjdbc.query(SQL_GET_BY_USER_ID, new Object[]{"%" + userId + "%"}, MULTIPLE_RS_EXTRACTOR);
  }

  @Override
  public List<Budget> getSisa(String userId, String category) {
    return mjdbc.query(SQL_SELECT_SISA,
        new Object[]{"%" + userId + "%", category}, MULTIPLE_RS_EXTRACTOR);
  }

  @Override
  public List<Budget> getBudget(String userId, String category) {
    return mjdbc.query(SQL_SELECT_NOMINAL, new Object[]{"%" + userId
            + "%", category}, MULTIPLE_RS_EXTRACTOR);
  }

  @Override
  public int setBudget(String userId, String category, int nominal) {
    return mjdbc.update(SQL_UPSERT, new Object[]{userId, category, nominal, nominal});
  }
}