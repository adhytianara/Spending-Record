package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.Spending;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class SpendingDaoImplementation implements SpendingDao {

    private JdbcTemplate mJdbc;

    private final static String SPENDING_RECORD_TABLE="tbl_spending";
    private final static String SQL_SELECT_ALL="SELECT * FROM "+SPENDING_RECORD_TABLE;
    private final static String SQL_GET_BY_USER_ID=SQL_SELECT_ALL + " WHERE LOWER(user_id) LIKE LOWER(?);";
    private final static String SQL_REGISTER="INSERT INTO "+SPENDING_RECORD_TABLE+
                                " (user_id, display_name, category, timestamp, nominal) VALUES (?, ?, ?, ?, ?);";

    private final static ResultSetExtractor<List<Spending>> MULTIPLE_RS_EXTRACTOR=
                        new ResultSetExtractor<List<Spending>>()
    {
        @Override
        public List<Spending> extractData(ResultSet aRs)
                throws SQLException, DataAccessException
        {
            List<Spending> list=new Vector<Spending>();
            while(aRs.next())
            {
                Spending sp=new Spending(
                        aRs.getLong("id"),
                        aRs.getString("user_id"),
                        aRs.getString("display_name"),
                        aRs.getString("category"),
                        aRs.getString("timestamp"),
                        aRs.getString("nominal"));
                list.add(sp);
            }
            return list;
        }
    };

    public SpendingDaoImplementation(DataSource dataSource) {
        mJdbc=new JdbcTemplate(dataSource);
    }

    @Override
    public List<Spending> get() {
        return mJdbc.query(SQL_SELECT_ALL, MULTIPLE_RS_EXTRACTOR);
    }

    @Override
    public List<Spending> getByUserId(String aUserId) {
        return mJdbc.query(SQL_GET_BY_USER_ID, new Object[]{"%"+aUserId+"%"}, MULTIPLE_RS_EXTRACTOR);
    }

    @Override
    public int saveRecord(String userId, String displayName, String category, String timestamp, String nominal) {
        return mJdbc.update(SQL_REGISTER, new Object[]{userId, displayName, category, timestamp, nominal});
    }
}