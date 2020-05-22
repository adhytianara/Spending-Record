package com.tkap11.spendingrecord.catatpengeluaran;

public class ChooseCategoryState extends CatatPengeluaranState {

  public ChooseCategoryState(String senderId, String displayName) {
    this.description = senderId + ";" + displayName;
  }

  @Override
  public CatatPengeluaranState userChooseCategory(String userMessage) {
    description = description + ";" + userMessage;
    messageToUser = "Kategori berhasil dipilih. "
        + "Berapa uang yang kamu habiskan untuk " + userMessage + " ?";
    return new InsertMoneyState(this);
  }

  @Override
  public CatatPengeluaranState userInsertMoney(String userMessage) {
    return unknownMessage();
  }

  @Override
  public CatatPengeluaranState userConfirmation(String userMessage) {
    return unknownMessage();
  }

  @Override
  public CatatPengeluaranState userCancelOperation() {
    messageToUser = "Proses pencatatan dibatalkan";
    return null;
  }

  @Override
  public CatatPengeluaranState unknownMessage() {
    messageToUser = "Kategori tidak sesuai! Pilih salah satu dari lima kategori yang tersedia. "
        + "Jika ingin membatalkan tindakan, ketik 'Batal'";
    return this;
  }

  @Override
  public String getDescription() {
    return this.description;
  }
}
