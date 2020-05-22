package com.tkap11.spendingrecord.catatpengeluaran;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ChooseCategoryStateTest {
  CatatPengeluaranState currentState = new ChooseCategoryState("senderId", "displayName");

  @Test
  void userChooseCategory() {
    CatatPengeluaranState nextState = this.currentState.userChooseCategory("makanan");
    assertTrue(nextState instanceof InsertMoneyState);
  }

  @Test
  void userInsertMoney() {
    CatatPengeluaranState nextState = this.currentState.userInsertMoney("1000");
    assertTrue(nextState instanceof ChooseCategoryState);
  }

  @Test
  void userConfirmation() {
    CatatPengeluaranState nextState = this.currentState.userConfirmation("ya");
    assertTrue(nextState instanceof ChooseCategoryState);
  }

  @Test
  void userCancelOperation() {
    CatatPengeluaranState nextState = this.currentState.userCancelOperation();
    assertEquals(nextState, null);
  }

  @Test
  void unknownMessage() {
    CatatPengeluaranState nextState = this.currentState.unknownMessage();
    assertTrue(nextState instanceof ChooseCategoryState);
  }

  @Test
  void getDescription() {
    CatatPengeluaranState nextState = this.currentState.userChooseCategory("makanan");
    String description = currentState.getDescription();
    assertEquals("senderId;displayName;makanan", description);
  }
}