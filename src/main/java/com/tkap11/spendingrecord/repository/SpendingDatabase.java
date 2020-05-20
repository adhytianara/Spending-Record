package com.tkap11.spendingrecord.repository;


import com.tkap11.spendingrecord.database.SpendingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class SpendingDatabase {

    @Autowired
    private SpendingDao spendingDao;

    public int saveRecord(String description){
        String[] userData = description.split(";");
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime  = localDateTime.plusHours(7);
        return spendingDao.saveRecord(userData[0], userData[1], userData[2], String.valueOf(localDateTime), userData[3]);
    }
}