package com.tkap11.spendingrecord.state.catatpengeluaran;

public class ConfirmationState extends CatatPengeluaranState {

  public ConfirmationState(InsertMoneyState state) {
    this.state = state;
  }

  @Override
  public CatatPengeluaranState userChooseCategory(String userMessage) {
    return unknownMessage();
  }

  @Override
  public CatatPengeluaranState userInsertMoney(String userMessage) {
    return unknownMessage();
  }

  @Override
  public CatatPengeluaranState userConfirmation(String userMessage) {
    messageToUser = "Pengeluaran kamu berhasil dicatat";
    description = "";
    return null;
  }

  @Override
  public CatatPengeluaranState userCancelOperation() {
    messageToUser = "Proses pencatatan dibatalkan";
    return null;
  }

  @Override
  public CatatPengeluaranState unknownMessage() {
    messageToUser = "Konfirmasi pencatatan dengan menjawab 'Ya' "
        + "atau ketik 'Batal' untuk membatalkan tindakan";
    return this;
  }

  @Override
  public CatatPengeluaranState otherServiceMessage() {
    messageToUser = "Untuk beralih ke fitur lain, klik tombol 'Batal' terlebih dahulu";
    return this;
  }

  @Override
  public String getDescription() {
    return state.getDescription() + ";" + description;
  }
}
