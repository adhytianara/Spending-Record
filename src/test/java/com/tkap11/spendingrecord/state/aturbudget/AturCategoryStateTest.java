package com.tkap11.spendingrecord.state.aturbudget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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

    nextState = state.handleUserRequest("menu", "bla");
    assertTrue(nextState instanceof AturCategoryState);
  }
}