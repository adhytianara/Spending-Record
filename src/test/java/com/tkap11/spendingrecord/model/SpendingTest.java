package com.tkap11.spendingrecord.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SpendingTest {
    Spending spending;

    @BeforeEach
    void setUp() {
        spending = new Spending(Long.parseLong("1"), "userId", "adhytia",
                "makanan", "timestamp", "20000");
    }

    @Test
    void getCategory() {
        String category = spending.getCategory();
        assertEquals(category, "makanan");
    }

    @Test
    void setCategory() {
        String category = spending.getCategory();
        assertEquals(category, "makanan");
        spending.setCategory("tagihan");
        category = spending.getCategory();
        assertEquals(category, "tagihan");
    }

    @Test
    void getTimestamp() {
        String timestamp = spending.getTimestamp();
        assertEquals(timestamp, "timestamp");
    }

    @Test
    void setTimestamp() {
        String timestamp = spending.getTimestamp();
        assertEquals(timestamp, "timestamp");
        spending.setTimestamp("new timestamp");
        timestamp = spending.getTimestamp();
        assertEquals(timestamp, "new timestamp");
    }

    @Test
    void getNominal() {
        String nominal = spending.getNominal();
        assertEquals(nominal, "20000");
    }

    @Test
    void setNominal() {
        String nominal = spending.getNominal();
        assertEquals(nominal, "20000");
        spending.setNominal("30000");
        nominal = spending.getNominal();
        assertEquals(nominal, "30000");
    }

    @Test
    void getId() {
        Long id = spending.getId();
        assertEquals(id, 1);
    }

    @Test
    void setId() {
        Long id = spending.getId();
        assertEquals(id, 1);
        spending.setId(Long.parseLong("2"));
        id = spending.getId();
        assertEquals(id, 2);
    }

    @Test
    void getUserId() {
        String userId = spending.getUserId();
        assertEquals(userId, "userId");
    }

    @Test
    void setUserId() {
        String userId = spending.getUserId();
        assertEquals(userId, "userId");
        spending.setUserId("new userId");
        userId = spending.getUserId();
        assertEquals(userId, "new userId");
    }

    @Test
    void getDisplayName() {
        String displayName = spending.getDisplayName();
        assertEquals(displayName, "adhytia");
    }

    @Test
    void setDisplayName() {
        String displayName = spending.getDisplayName();
        assertEquals(displayName, "adhytia");
        spending.setDisplayName("spending record");
        displayName = spending.getDisplayName();
        assertEquals(displayName, "spending record");
    }
}