package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.Spending;
import java.util.List;

public interface SpendingDao {
  List<Spending> get();

  List<Spending> getByUserId(String userId);

  int saveRecord(String userId, String displayName, String category,
                 String timestamp, String nominal);

  List<Spending> getSisa(String userId, String category);
}
