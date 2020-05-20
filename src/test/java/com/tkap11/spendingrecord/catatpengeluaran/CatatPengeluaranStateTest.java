package com.tkap11.spendingrecord.catatpengeluaran;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatatPengeluaranStateTest {
    CatatPengeluaranState state = new ChooseCategoryState("senderId", "displayName");

    @Test
    void getMessageToUser() {
        state.userCancelOperation();
        assertEquals(state.getMessageToUser(),"Proses pencatatan dibatalkan");
    }

    @Test
    void handleUserRequest() {
        CatatPengeluaranState nextState = state.handleUserRequest("makanan");
        assertTrue(nextState instanceof InsertMoneyState);

        nextState =  state.handleUserRequest("1000");
        assertTrue(nextState instanceof ChooseCategoryState);

        nextState =  state.handleUserRequest("ya");
        assertTrue(nextState instanceof ChooseCategoryState);

        nextState =  state.handleUserRequest("adasd");
        assertTrue(nextState instanceof ChooseCategoryState);

        nextState =  state.handleUserRequest("batal");
        assertEquals(nextState, null);
    }
}