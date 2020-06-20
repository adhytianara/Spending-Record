package com.tkap11.spendingrecord.state.aturbudget;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AturConfirmationStateTest {
    AturConfirmationState state = new AturConfirmationState("makanan", 32098420);

    @Test
    void userChooseCategory() {
        AturState nextState = state.handleUserRequest("makanan", "bla");
        assertTrue(nextState instanceof AturConfirmationState);
    }

    @Test
    void userInsertMoney() {
        AturState nextState = state.handleUserRequest("48521982", "bla");
        assertTrue(nextState instanceof AturConfirmationState);
    }

    @Test
    void userConfirmation() {
        AturState nextState = state.handleUserRequest("ya", "bla");
        assertTrue(nextState == null);
    }

    @Test
    void unknownMessage() {
        AturState nextState = state.handleUserRequest("agefjhababf,masm,", "bla");
        assertTrue(nextState instanceof AturConfirmationState);
    }
}