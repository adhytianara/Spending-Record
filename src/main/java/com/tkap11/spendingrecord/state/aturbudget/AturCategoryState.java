package com.tkap11.spendingrecord.state.aturbudget;

public class AturCategoryState extends AturState {

  @Override
  public AturState userChooseCategory(String userMessage, String senderId) {
    this.messageToUser = "Berapa budget kamu untuk kategori " + userMessage + " ?";
    return new AturAmountState(userMessage);
  }

  @Override
  public AturState userInsertMoney(long userMessage) {
    return unknownMessage();
  }

  @Override
  public AturState userConfirmation(String senderId) {
    return unknownMessage();
  }

  @Override
  public AturState unknownMessage() {
    this.messageToUser = "Kategori tidak sesuai! Pilih salah satu dari "
        + "lima kategori yang tersedia. "
        + "Jika ingin membatalkan tindakan, ketik 'Batal'";
    return this;
  }

}