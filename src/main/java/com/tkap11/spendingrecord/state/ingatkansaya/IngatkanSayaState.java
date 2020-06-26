package com.tkap11.spendingrecord.state.ingatkansaya;

import com.linecorp.bot.model.message.Message;
import com.tkap11.spendingrecord.state.State;
import java.util.Arrays;
import java.util.List;

public abstract class IngatkanSayaState implements State {
  private final List<String> action = Arrays.asList(
      "menu", "atur budget", "sisa budget", "catat pengeluaran",
      "lihat laporan", "ingatkan saya", "reset data");
  protected IngatkanSayaState state;
  protected String messageToUser;
  protected String userId;
  protected Message message;

  public abstract IngatkanSayaState userConfirmation(String userMessage);

  public abstract IngatkanSayaState userCancelOperation();

  public abstract IngatkanSayaState unknownMessage();

  public abstract IngatkanSayaState otherServiceMessage();

  public Message getMessagetoUser() {
    return this.message;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * Check input and respond it with corresponding method.
   */
  public IngatkanSayaState handleUserRequest(String userMessage) {
    if (userMessage.equals("ya")) {
      return userConfirmation(userMessage);
    } else if (userMessage.equals("tidak")) {
      return userCancelOperation();
    } else if (action.contains(userMessage)) {
      return otherServiceMessage();
    } else {
      return unknownMessage();
    }
  }

  public abstract void getUserIngatkanResponse();
}
