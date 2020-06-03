package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.Spending;
import java.util.List;

public interface SpendingDao {
    public List<Spending> get();
    public List<Spending> getByUserId(String userId);
    public int saveRecord(String userId, String displayName, String category, String timestamp, String nominal);
    public List<Spending> getNominal(String userId, String category);
}
