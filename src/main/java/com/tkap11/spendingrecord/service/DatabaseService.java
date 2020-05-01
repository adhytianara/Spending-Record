package com.tkap11.spendingrecord.service;


import com.tkap11.spendingrecord.database.Dao;
import com.tkap11.spendingrecord.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseService {

    @Autowired
    private Dao mDao;

    public int registerUser(String aUserId, String aDisplayName){
        if(findUserById(aUserId) == null)
        {
            return mDao.registerUser(aUserId, aDisplayName);
        }

        return -1;
    }

    public String findUserById(String aUserId){
        List<User> users=mDao.getByUserId("%"+aUserId+"%");

        if(users.size() > 0)
        {
            return users.get(0).getUserId();
        }
        return null;
    }
}