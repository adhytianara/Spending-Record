package com.tkap11.spendingrecord.state.catatpengeluaran;

import com.tkap11.spendingrecord.state.State;

import java.util.Arrays;
import java.util.List;

public abstract class CatatPengeluaranState implements State {
  private final List<String> categories = Arrays.asList(
      "makanan", "transportasi", "tagihan", "belanja", "lainnya");
  protected CatatPengeluaranState state;
  protected String description;
  protected String messageToUser;

  public abstract CatatPengeluaranState userChooseCategory(String userMessage);

  public abstract CatatPengeluaranState userInsertMoney(String userMessage);

  public abstract CatatPengeluaranState userConfirmation(String userMessage);

  public abstract CatatPengeluaranState userCancelOperation();

  public abstract CatatPengeluaranState unknownMessage();

  public abstract String getDescription();

  public String getMessageToUser() {
    return messageToUser;
  }

  /**
   * Check input and respond it with corresponding method.
   */
  public CatatPengeluaranState handleUserRequest(String userMessage) {
    if (categories.contains(userMessage)) {
      return userChooseCategory(userMessage);
    } else if (isNominal(userMessage)) {
      return userInsertMoney(userMessage);
    } else if (userMessage.contains("ya")) {
      return userConfirmation(userMessage);
    } else if (userMessage.contains("batal")) {
      return userCancelOperation();
    } else {
      return unknownMessage();
    }
  }

  private boolean isNominal(String userMessage) {
    try {
      Integer.parseInt(userMessage);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}