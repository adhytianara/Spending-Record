package com.tkap11.spendingrecord.repository;

import com.tkap11.spendingrecord.catatpengeluaran.ChooseCategoryState;
import com.tkap11.spendingrecord.database.UserDao;
import com.tkap11.spendingrecord.model.User;
import com.tkap11.spendingrecord.repository.UserDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class UserDatabaseTest {

    @Mock
    private UserDao mUserDao;

    @InjectMocks
    private UserDatabase dbService;

    @Test
    void registerUser() {
        List<User> users = new ArrayList<>();

        when(mUserDao.registerUser("userId", "adhytia"))
                .thenReturn(1);
        int position = dbService.registerUser("userId", "adhytia");
        assertNotEquals(position, -1);
        verify(mUserDao, times(1)).registerUser("userId", "adhytia");

        users.add(new User(Long.parseLong("1"), "userId", "adhytia"));
        when(mUserDao.getByUserId("%userId%"))
                .thenReturn(users);
        position = dbService.registerUser("userId", "adhytia");
        assertEquals(position, -1);
        verify(mUserDao, times(2)).getByUserId("%userId%");
    }

    @Test
    void findUserById() {
        List<User> users = new ArrayList<>();

        when(mUserDao.getByUserId("%userId%"))
                .thenReturn(users);
        String user = dbService.findUserById("userId");
        assertEquals(null, user);
        verify(mUserDao, times(1)).getByUserId("%userId%");

        users.add(new User(Long.parseLong("1"), "userId", "adhytia"));
        when(mUserDao.getByUserId("%userId%"))
                .thenReturn(users);
        user = dbService.findUserById("userId");
        assertEquals("userId", user);
        verify(mUserDao, times(2)).getByUserId("%userId%");
    }
}