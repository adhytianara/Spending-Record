package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.Budget;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class BudgetDaoImplementation implements BudgetDao{

    private JdbcTemplate mJdbc;

    private final static String BUDGET_TABLE = "tbl_budget";
    private final static String SQL_SELECT_ALL="SELECT * FROM "+BUDGET_TABLE;
    private final static String SQL_SELECT_NOMINAL= "SELECT * FROM" + BUDGET_TABLE + " WHERE LOWER(user_id) LIKE LOWER(?), LOWER(category) = LOWER(?);";
    private final static String SQL_GET_BY_USER_ID=SQL_SELECT_ALL + " WHERE LOWER(user_id) LIKE LOWER(?);";
    private final static String SQL_UPSERT="INSERT INTO "+BUDGET_TABLE+
            " (user_id, category, nominal) VALUES (?, ?, ?) ON CONFLICT UPDATE;";

    public BudgetDaoImplementation(DataSource dataSource) {
        mJdbc=new JdbcTemplate(dataSource);
    }

    private final static ResultSetExtractor<List<Budget>> MULTIPLE_RS_EXTRACTOR=
            new ResultSetExtractor<List<Budget>>()
            {
                @Override
                public List<Budget> extractData(ResultSet aRs)
                        throws SQLException, DataAccessException
                {
                    List<Budget> list=new Vector<Budget>();
                    while(aRs.next())
                    {
                        Budget sp=new Budget(
                                aRs.getString("user_id"),
                                aRs.getString("category"),
                                aRs.getLong("budget"));
                        list.add(sp);
                    }
                    return list;
                }
            };

    @Override
    public List<Budget> getAll() {
        return mJdbc.query(SQL_SELECT_ALL, MULTIPLE_RS_EXTRACTOR);
    }

    @Override
    public List<Budget> getByUserId(String userId) {
        return mJdbc.query(SQL_GET_BY_USER_ID, new Object[]{"%"+userId+"%"}, MULTIPLE_RS_EXTRACTOR);
    }

    @Override
    public List<Budget> getBudget(String userId, String category) {
        return mJdbc.query(SQL_SELECT_NOMINAL, new Object[]{"%"+userId+"%", category}, MULTIPLE_RS_EXTRACTOR);
    }

    @Override
    public int setBudget(String userId, String category, Long nominal) {
        return mJdbc.update(SQL_UPSERT, new Object[]{userId, category, nominal});
    }
}