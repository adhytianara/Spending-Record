package com.tkap11.spendingrecord.state.aturbudget;

public class AturConfirmationState extends AturState {

  public AturConfirmationState(String category, int amount) {
    this.category = category;
    this.amount = amount;
  }

  @Override
  public AturState userChooseCategory(String userMessage, String senderId) {
    return unknownMessage();
  }

  @Override
  public AturState userInsertMoney(int userMessage) {
    return unknownMessage();
  }

  @Override
  public AturState userConfirmation(String senderId) {
    this.messageToUser = "Budget berhasil diubah";

    return null;
  }

  @Override
  public AturState otherServiceMessage() {
    messageToUser = "Untuk beralih ke fitur lain, klik tombol 'Batal' terlebih dahulu";
    return this;
  }

  @Override
  public AturState unknownMessage() {
    this.messageToUser = "Konfirmasi perubahan budget dengan menjawab 'Ya' "
            + "atau ketik 'Batal' untuk membatalkan tindakan";
    return this;
  }

}
