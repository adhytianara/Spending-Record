package com.tkap11.spendingrecord.repository;


import com.tkap11.spendingrecord.database.UserDao;
import com.tkap11.spendingrecord.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDatabase {

  @Autowired
  private UserDao userDao;

  /**
   * Save new user to database.
   */
  public int registerUser(String userId, String displayName) {
    if (findUserById(userId) == null) {
      return userDao.registerUser(userId, displayName);
    }
    return -1;
  }

  /**
   * Find user in datase table.
   */
  public String findUserById(String userId) {
    List<User> users = userDao.getByUserId("%" + userId + "%");

    if (users.size() > 0) {
      return users.get(0).getUserId();
    }
    return null;
  }
}