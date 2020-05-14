package com.tkap11.spendingrecord.catatpengeluaran;

public class ConfirmationHandler extends CatatPengeluaranHandler {


	private CatatPengeluaranHandler handler;

	public ConfirmationHandler(InsertMoneyHandler handler) {
		this.handler = handler;
	}

	@Override
	public CatatPengeluaranHandler handleUserRequest(String userMessage) {
		if (userMessage.contains("ya")){
			messageToUser = "Pengeluaran kamu berhasil dicatat";
			description = "";
			return null;
		} else if(userMessage.contains("batal")){
			messageToUser = "Proses pencatatan dibatalkan";
			return null;
		} else {
			messageToUser = "Konfirmasi pencatatan dengan menjawab 'Ya' " +
					"atau ketik 'Batal' untuk membatalkan tindakan";
			return this;
		}
	}

	@Override
	public String getDescription() {
		return handler.getDescription() + ";" + description;
	}
}
