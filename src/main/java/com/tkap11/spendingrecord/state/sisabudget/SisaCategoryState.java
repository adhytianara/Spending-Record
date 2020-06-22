package com.tkap11.spendingrecord.state.sisabudget;

public class SisaCategoryState extends SisaBudgetState {

  public SisaCategoryState(String senderId) {
    this.description = senderId;
  }

  @Override
  public SisaBudgetState userChooseCategory(String userMessage) {
    description = description + ";" + userMessage;
    return null;
  }

  @Override
  public SisaBudgetState unknownMessage() {
    messageToUser = "Kategori tidak sesuai! Pilih salah satu dari lima kategori yang tersedia. "
        + "Jika ingin membatalkan tindakan, klik tombol 'Batal'";
    return this;
  }

  @Override
  public SisaBudgetState userCancelOperation() {
    messageToUser = "Tindakan menampilkan sisa budget dibatalkan";
    return null;
  }

  @Override
  public SisaBudgetState otherServiceMessage() {
    messageToUser = "Untuk beralih ke fitur lain, klik tombol 'Batal' terlebih dahulu";
    return this;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

}
