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
  private final List<String> action = Arrays.asList(
      "menu", "atur budget", "sisa budget", "catat pengeluaran",
      "lihat laporan", "ingatkan saya", "reset data");
  protected List<String> urls = Arrays.asList(
      "https://i.ibb.co/pvp4SgK/0zswty.png",
      "https://i.ibb.co/bBp825Z/o6wnt2.png",
      "https://i.ibb.co/PD95Cs0/a7d1of.png",
      "https://i.ibb.co/YBSKc6K/2uxzif.png",
      "https://i.ibb.co/C8smqFf/0rxrkq.png",
      "https://i.ibb.co/VVpC512/etgtaw.png"
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
