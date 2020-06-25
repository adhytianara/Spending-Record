package com.tkap11.spendingrecord.state.lihatlaporan;

import com.linecorp.bot.model.message.Message;
import com.tkap11.spendingrecord.state.State;
import java.util.Arrays;
import java.util.List;

public abstract class LihatLaporanState implements State {
  private final List<String> categories = Arrays.asList(
      "lihat detail makanan", "lihat detail transportasi", "lihat detail persentase",
      "lihat detail tagihan", "lihat detail belanja", "lihat detail lainnya",
      "lihat detail semua kategori");
  private List<String> action = Arrays.asList(
      "menu", "atur budget", "sisa budget", "catat pengeluaran", "lihat laporan", "ingatkan saya");
  protected List<String> urls = Arrays.asList(
      "https://files.catbox.moe/0zswty.png",
      "https://files.catbox.moe/o6wnt2.png",
      "https://files.catbox.moe/a7d1of.png",
      "https://files.catbox.moe/2uxzif.png",
      "https://files.catbox.moe/0rxrkq.png",
      "https://files.catbox.moe/etgtaw.png"
  );
  protected LihatLaporanState state;
  protected String messageToUser;
  protected String userId;
  protected Message message;

  public abstract LihatLaporanState userChooseCategory(String userMessage);

  public abstract LihatLaporanState userCancelOperation();

  public abstract LihatLaporanState unknownMessage();

  public abstract LihatLaporanState otherServiceMessage();

  public Message getMessagetoUser() {
    return this.message;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * Check input and respond it with corresponding method.
   */
  public LihatLaporanState handleUserRequest(String userMessage) {
    if (categories.contains(userMessage)) {
      return userChooseCategory(userMessage.split(" ")[2]);
    } else if (action.contains(userMessage)) {
      return otherServiceMessage();
    } else if (userMessage.contains("batal")) {
      return userCancelOperation();
    } else {
      return unknownMessage();
    }
  }
}
