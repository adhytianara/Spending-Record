package com.tkap11.spendingrecord.repository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tkap11.spendingrecord.database.UserDao;
import com.tkap11.spendingrecord.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class UserDatabaseTest {

  @Mock
  private UserDao userDao;

  @InjectMocks
  private UserDatabase dbService;

  @Test
  void registerUser() {
    when(userDao.registerUser("userId", "adhytia"))
        .thenReturn(1);
    dbService.registerUser("userId", "adhytia");
    verify(userDao, times(1)).registerUser("userId", "adhytia");
  }

  @Test
  void findUserById() {
    List<User> users = new ArrayList<>();
    users.add(new User(Long.parseLong("1"), "userId", "adhytia", "false"));
    when(userDao.getByUserId("%userId%"))
        .thenReturn(users);
    dbService.findUserById("userId");
    verify(userDao, times(1)).getByUserId("%userId%");
  }

  @Test
  void getAllUsers() {
    List<User> users = new ArrayList<>();
    users.add(new User(Long.parseLong("1"), "userId", "adhytia", "false"));
    when(userDao.get()).thenReturn(users);
    dbService.getAllUsers();
    verify(userDao, times(1)).get();
  }

  @Test
  void getStatusIngatkanbyUserId() {
    List<User> users = new ArrayList<>();
    users.add(new User(Long.parseLong("1"), "userId", "adhytia", "false"));
    when(userDao.getStatusIngatkanbyUserId("userId")).thenReturn(users);
    when(userDao.setStatusIngatkanbyUserId("status", "userId"))
        .thenReturn(1);
    dbService.setStatusIngatkan("status", "userId");
    dbService.getStatusIngatkanbyUserId("userId");
    verify(userDao, times(1)).getStatusIngatkanbyUserId("userId");
  }

  @Test
  void getAllUserIngatkanAktif() {
    List<User> users = new ArrayList<>();
    users.add(new User(Long.parseLong("1"), "userId", "adhytia", "false"));
    when(userDao.getAllUserIngatkanAktif()).thenReturn(users);
    dbService.getAllUserIngatkanAktif();
    verify(userDao, times(1)).getAllUserIngatkanAktif();
  }
}