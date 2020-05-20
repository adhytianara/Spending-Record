package com.tkap11.spendingrecord.repository;

import com.tkap11.spendingrecord.database.SpendingDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpendingDatabaseTest {
    @Mock
    private SpendingDao spendingDao;

    @InjectMocks
    private SpendingDatabase spendingDatabase;

    @Test
    void saveRecord() {
        String description = "userId;displayName;category;nominal";
        spendingDatabase.saveRecord(description);
        when(spendingDao.saveRecord("userId", "displayName",
                "category", "timestamp", "nominal"))
                .thenReturn(1);
        spendingDao.saveRecord("userId", "displayName",
                "category", "timestamp", "nominal");
        verify(spendingDao).saveRecord("userId", "displayName",
                "category", "timestamp", "nominal");
    }
}