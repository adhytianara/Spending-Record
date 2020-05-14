package com.tkap11.spendingrecord.repository;


import com.tkap11.spendingrecord.database.UserDao;
import com.tkap11.spendingrecord.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDatabase {

    @Autowired
    private UserDao mUserDao;

    public int registerUser(String aUserId, String aDisplayName){
        if(findUserById(aUserId) == null)
        {
            return mUserDao.registerUser(aUserId, aDisplayName);
        }

        return -1;
    }

    public String findUserById(String aUserId){
        List<User> users= mUserDao.getByUserId("%"+aUserId+"%");

        if(users.size() > 0)
        {
            return users.get(0).getUserId();
        }
        return null;
    }
}