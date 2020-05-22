package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class UserDaoImplementation implements UserDao {

    private JdbcTemplate mJdbc;

    private final static String USER_TABLE="tbl_user";
    private final static String SQL_SELECT_ALL="SELECT * FROM "+USER_TABLE;
    private final static String SQL_GET_BY_USER_ID=SQL_SELECT_ALL + " WHERE LOWER(user_id) LIKE LOWER(?);";
    private final static String SQL_REGISTER="INSERT INTO "+USER_TABLE+" (user_id, display_name) VALUES (?, ?);";

    private final static ResultSetExtractor<List<User>> MULTIPLE_RS_EXTRACTOR=new ResultSetExtractor< List<User> >()
    {
        @Override
        public List<User> extractData(ResultSet aRs)
                throws SQLException, DataAccessException
        {
            List<User> list=new Vector<User>();
            while(aRs.next())
            {
                User p=new User(
                        aRs.getLong("id"),
                        aRs.getString("user_id"),
                        aRs.getString("display_name"));
                list.add(p);
            }
            return list;
        }
    };

    public UserDaoImplementation(DataSource dataSource) {
        mJdbc=new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> get() {
        return mJdbc.query(SQL_SELECT_ALL, MULTIPLE_RS_EXTRACTOR);
    }

    @Override
    public List<User> getByUserId(String userId) {
        return mJdbc.query(SQL_GET_BY_USER_ID, new Object[]{"%"+ userId +"%"}, MULTIPLE_RS_EXTRACTOR);
    }

    @Override
    public int registerUser(String userId, String displayName) {
        return mJdbc.update(SQL_REGISTER, new Object[]{userId, displayName});
    }
}
