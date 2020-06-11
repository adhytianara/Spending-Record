package com.tkap11.spendingrecord.state.aturbudget;

import com.tkap11.spendingrecord.state.catatpengeluaran.CatatPengeluaranState;
import com.tkap11.spendingrecord.state.catatpengeluaran.ChooseCategoryState;
import com.tkap11.spendingrecord.state.catatpengeluaran.InsertMoneyState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AturCategoryStateTest {
    AturCategoryState state = new AturCategoryState();

    @Test
    void handleUserRequest() {
        AturState nextState = state.handleUserRequest("makanan", "bla");
        assertTrue(nextState instanceof AturAmountState);

        nextState = state.handleUserRequest("1000", "bla");
        assertTrue(nextState instanceof AturCategoryState);

        nextState = state.handleUserRequest("ya", "bla");
        assertTrue(nextState instanceof AturCategoryState);

        nextState = state.handleUserRequest("adasd", "bla");
        assertTrue(nextState instanceof AturCategoryState);

        nextState = state.handleUserRequest("batal", "bla");
        assertEquals(nextState, null);
    }
}