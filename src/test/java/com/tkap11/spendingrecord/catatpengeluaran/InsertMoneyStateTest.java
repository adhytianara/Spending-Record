package com.tkap11.spendingrecord.catatpengeluaran;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class InsertMoneyStateTest {
  CatatPengeluaranState currentState = new InsertMoneyState(
        new ChooseCategoryState("senderId", "displayName"));

  @Test
  void userChooseCategory() {
    CatatPengeluaranState nextState = this.currentState.userChooseCategory("makanan");
    assertTrue(nextState instanceof InsertMoneyState);
  }

  @Test
  void userInsertMoney() {
    CatatPengeluaranState nextState = this.currentState.userInsertMoney("1000");
    assertTrue(nextState instanceof ConfirmationState);
  }

  @Test
  void userConfirmation() {
    CatatPengeluaranState nextState = this.currentState.userConfirmation("ya");
    assertTrue(nextState instanceof InsertMoneyState);
  }

  @Test
  void userCancelOperation() {
    CatatPengeluaranState nextState = this.currentState.userCancelOperation();
    assertEquals(nextState, null);
  }

  @Test
  void unknownMessage() {
    CatatPengeluaranState nextState = this.currentState.unknownMessage();
    assertTrue(nextState instanceof InsertMoneyState);
  }

  @Test
  void getDescription() {
    String description = currentState.getDescription();
    assertEquals("senderId;displayName;null", description);
  }
}