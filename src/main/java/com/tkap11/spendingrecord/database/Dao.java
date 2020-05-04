package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.User;
import java.util.List;

public interface Dao
{
    public List<User> get();
    public List<User> getByUserId(String aUserId);
    public int registerUser(String aUserId, String aDisplayName);
}