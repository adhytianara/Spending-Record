package com.tkap11.spendingrecord.sisabudget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SisaCategoryStateTest {
    SisaBudgetState currentState = new SisaCategoryState("senderId");

    @Test
    void userChooseCategory() {
        SisaBudgetState nextState = this.currentState.userChooseCategory("belanja");
        assertEquals(nextState, null);
    }

    @Test
    void unknownMessage() {
        SisaBudgetState nextState = this.currentState.unknownMessage();
        assertTrue(nextState instanceof SisaCategoryState);
    }

    @Test
    void userCancelOperation() {
        SisaBudgetState nextState = this.currentState.userCancelOperation();
        assertEquals(nextState, null);
    }

    @Test
    void otherServiceMessage() {
        SisaBudgetState nextState = this.currentState.unknownMessage();
        assertTrue(nextState instanceof SisaCategoryState);
    }

    @Test
    void getDescription() {
        SisaBudgetState nextState = this.currentState.userChooseCategory("belanja");
        String description = currentState.getDescription();
        assertEquals("senderId;belanja", description);
    }
}
