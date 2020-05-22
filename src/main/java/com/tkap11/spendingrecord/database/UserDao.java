package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.User;
import java.util.List;

public interface UserDao {
  public List<User> get();

  public List<User> getByUserId(String userId);

  public int registerUser(String userId, String displayName);
}