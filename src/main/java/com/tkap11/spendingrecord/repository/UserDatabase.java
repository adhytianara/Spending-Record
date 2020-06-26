package com.tkap11.spendingrecord.repository;


import com.tkap11.spendingrecord.database.UserDao;
import com.tkap11.spendingrecord.model.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

  public List<User> getAllUsers() {
    List<User> users = userDao.get();
    return users;
  }

  public List<User> getStatusIngatkanbyUserId(String userId) {
    return userDao.getStatusIngatkanbyUserId(userId);
  }

  public Set<String> getAllUserIngatkanAktif() {
    List<User> users = userDao.getAllUserIngatkanAktif();
    List<String> userIdList = new ArrayList<>();
    for (User user : users) {
      userIdList.add(user.getUserId());
    }
    return new HashSet(userIdList);
  }

  public int setStatusIngatkan(String status, String userId) {
    return userDao.setStatusIngatkanbyUserId(status, userId);
  }
}