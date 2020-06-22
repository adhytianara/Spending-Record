package com.tkap11.spendingrecord.state.aturbudget;

public class AturAmountState extends AturState {

  public AturAmountState(String category) {
    this.category = category;
  }

  @Override
  public AturState userChooseCategory(String userMessage, String senderId) {
    return unknownMessage();
  }

  @Override
  public AturState userInsertMoney(int userMessage) {
    this.messageToUser = "Oke, budget yang disediakan sebesar Rp" + userMessage
            + ". Konfirmasi perubahan dengan menjawab 'Ya'"
            + " atau ketik 'Batal' untuk membatalkan tindakan";
    return new AturConfirmationState(this.category, userMessage);
  }

  @Override
  public AturState userConfirmation(String senderId) {
    return unknownMessage();
  }

  @Override
  public AturState unknownMessage() {
    this.messageToUser =
            "Nominal uang tidak sesuai. Pastikan kamu hanya memasukkan angka, contoh: 10000. "
             + "Jika ingin membatalkan tindakan, ketik 'Batal'";
    return this;
  }
}
