package com.tkap11.spendingrecord.state.catatpengeluaran;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ConfirmationStateTest {
  CatatPengeluaranState currentState = new ConfirmationState(new InsertMoneyState(
          new ChooseCategoryState("senderId", "displayName")));

  @Test
  void userChooseCategory() {
    CatatPengeluaranState nextState = this.currentState.userChooseCategory("makanan");
    assertTrue(nextState instanceof ConfirmationState);
  }

  @Test
  void userInsertMoney() {
    CatatPengeluaranState nextState = this.currentState.userInsertMoney("1000");
    assertTrue(nextState instanceof ConfirmationState);
  }

  @Test
  void userConfirmation() {
    CatatPengeluaranState nextState = this.currentState.userConfirmation("ya");
    assertEquals(nextState, null);
  }

  @Test
  void userCancelOperation() {
    CatatPengeluaranState nextState = this.currentState.userCancelOperation();
    assertEquals(nextState, null);
  }

  @Test
  void unknownMessage() {
    CatatPengeluaranState nextState = this.currentState.unknownMessage();
    assertTrue(nextState instanceof ConfirmationState);
  }

  @Test
  void getDescription() {
    String description = currentState.getDescription();
    assertEquals("senderId;displayName;null;null", description);
  }
}