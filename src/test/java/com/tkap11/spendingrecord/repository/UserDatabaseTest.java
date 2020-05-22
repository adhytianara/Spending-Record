package com.tkap11.spendingrecord.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    int position = dbService.registerUser("userId", "adhytia");
    assertNotEquals(position, -1);
    verify(userDao, times(1)).registerUser("userId", "adhytia");

    List<User> users = new ArrayList<>();
    users.add(new User(Long.parseLong("1"), "userId", "adhytia"));
    when(userDao.getByUserId("%userId%"))
        .thenReturn(users);
    position = dbService.registerUser("userId", "adhytia");
    assertEquals(position, -1);
    verify(userDao, times(2)).getByUserId("%userId%");
  }

  @Test
  void findUserById() {
    List<User> users = new ArrayList<>();

    when(userDao.getByUserId("%userId%"))
        .thenReturn(users);
    String user = dbService.findUserById("userId");
    assertEquals(null, user);
    verify(userDao, times(1)).getByUserId("%userId%");

    users.add(new User(Long.parseLong("1"), "userId", "adhytia"));
    when(userDao.getByUserId("%userId%"))
        .thenReturn(users);
    user = dbService.findUserById("userId");
    assertEquals("userId", user);
    verify(userDao, times(2)).getByUserId("%userId%");
  }
}