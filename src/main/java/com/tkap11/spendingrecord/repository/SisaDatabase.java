package com.tkap11.spendingrecord.repository;

import com.tkap11.spendingrecord.database.SpendingDao;
import com.tkap11.spendingrecord.model.Spending;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SisaDatabase {

    @Autowired
    private SpendingDao spendingDao;

    /**
     * Save user record to database.
     */
    public List<Spending> sisaBudget(String description) {
        String[] userData = description.split(";");
        return spendingDao.getSisa(userData[0], userData[1]);
    }
}