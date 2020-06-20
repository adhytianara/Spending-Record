package com.tkap11.spendingrecord.sisabudget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SisaBudgetStateTest {
    SisaBudgetState state = new SisaCategoryState("senderId");

    @Test
    void getMessageToUser() {
        state.userCancelOperation();
        assertEquals(state.getMessageToUser(), "Tindakan menampilkan sisa budget dibatalkan");
    }

    @Test
    void handleUserRequest() {
        SisaBudgetState nextState = state.handleUserRequest("belanja");
        assertEquals(nextState, null);

        nextState = state.handleUserRequest("test");
        assertTrue(nextState instanceof SisaCategoryState);

        nextState = state.handleUserRequest("batal");
        assertEquals(nextState, null);

        nextState = state.handleUserRequest("menu");
        assertTrue(nextState instanceof SisaCategoryState);
    }
}
