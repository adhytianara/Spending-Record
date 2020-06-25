package com.tkap11.spendingrecord.state.aturbudget;

import com.tkap11.spendingrecord.state.State;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public abstract class AturState implements State {
  private final List<String> categories = Arrays.asList(
          "makanan", "transportasi", "tagihan", "belanja", "lainnya");
  private final List<String> action = Arrays.asList(
      "menu", "atur budget", "sisa budget", "catat pengeluaran",
      "lihat laporan", "ingatkan saya", "reset data");
  public String category;
  public int amount;
  public String messageToUser;

  public abstract AturState userChooseCategory(String userMessage, String senderId);

  public abstract AturState userInsertMoney(int userMessage);

  public abstract AturState userConfirmation(String senderId);

  public abstract AturState unknownMessage();

  public abstract AturState otherServiceMessage();

  public AturState userCancelOperation() {
    this.messageToUser = "Proses pengaturan budget dibatalkan";
    return null;
  }

  /**
   * handle user message.
   * @param userMessage message content from user
   * @param senderId user sender id
   * @return new state
   */
  public AturState handleUserRequest(String userMessage, String senderId) {
    if (categories.contains(userMessage)) {
      return userChooseCategory(userMessage, senderId);
    } else if (isNominal(userMessage)) {
      return userInsertMoney(Integer.parseInt(userMessage));
    } else if (userMessage.contains("ya")) {
      return userConfirmation(senderId);
    } else if (userMessage.contains("batal")) {
      return userCancelOperation();
    } else if (action.contains(userMessage)) {
      return otherServiceMessage();
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
