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
    users.add(new User(Long.parseLong("1"), "userId", "adhytia"));
    when(userDao.getByUserId("%userId%"))
        .thenReturn(users);
    dbService.findUserById("userId");
    verify(userDao, times(1)).getByUserId("%userId%");
  }
}