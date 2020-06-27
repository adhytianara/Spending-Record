package com.tkap11.spendingrecord.state.aturbudget;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.tkap11.spendingrecord.state.catatpengeluaran.CatatPengeluaranState;
import com.tkap11.spendingrecord.state.catatpengeluaran.ConfirmationState;
import org.junit.jupiter.api.Test;

class AturAmountStateTest {
  AturAmountState state = new AturAmountState("makanan");

  @Test
  void userChooseCategory() {
    AturState nextState = state.handleUserRequest("makanan", "bla");
    assertTrue(nextState instanceof AturAmountState);
  }

  @Test
  void userInsertMoney() {
    AturState nextState = state.handleUserRequest("10000", "bla");
    assertTrue(nextState instanceof AturConfirmationState);
  }

  @Test
  void userConfirmation() {
    AturState nextState = state.handleUserRequest("ya", "bla");
    assertTrue(nextState instanceof AturAmountState);
  }

  @Test
  void unknownMessage() {
    AturState nextState = state.handleUserRequest("ahfbskjbskjfbas", "bla");
    assertTrue(nextState instanceof AturAmountState);
  }

  @Test
  void otherServiceMessage() {
    AturState nextState = state.otherServiceMessage();
    assertTrue(nextState instanceof AturAmountState);
  }
}