package com.tkap11.spendingrecord.service;


import com.tkap11.spendingrecord.database.SpendingDao;
import com.tkap11.spendingrecord.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpendingDatabaseService {

    @Autowired
    private SpendingDao spendingDao;

    public int saveRecord(String userId, String displayName, String category, String timestamp, String nominal){
        return spendingDao.saveRecord(userId, displayName, category, timestamp, nominal);
    }
}