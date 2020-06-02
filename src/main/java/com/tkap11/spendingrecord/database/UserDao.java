package com.tkap11.spendingrecord.database;

import com.tkap11.spendingrecord.model.User;
import java.util.List;

public interface UserDao {
  List<User> get();

  List<User> getByUserId(String userId);

  int registerUser(String userId, String displayName);
}