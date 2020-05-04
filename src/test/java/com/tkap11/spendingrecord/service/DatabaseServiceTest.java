package com.tkap11.spendingrecord.service;

import com.tkap11.spendingrecord.database.Dao;
import com.tkap11.spendingrecord.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class DatabaseServiceTest {

    @Mock
    private Dao mDao;

    @InjectMocks
    private DatabaseService dbService;

    @Test
    void registerUser() {
        when(mDao.registerUser("userId", "adhytia"))
                .thenReturn(1);
        dbService.registerUser("userId", "adhytia");
        verify(mDao, times(1)).registerUser("userId", "adhytia");
    }

    @Test
    void findUserById() {
        List<User> users = new ArrayList<>();
        users.add(new User(Long.parseLong("1"), "userId", "adhytia"));
        when(mDao.getByUserId("%userId%"))
                .thenReturn(users);
        dbService.findUserById("userId");
        verify(mDao, times(1)).getByUserId("%userId%");
    }
}