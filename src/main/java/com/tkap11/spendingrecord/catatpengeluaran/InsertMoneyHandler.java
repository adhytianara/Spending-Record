package com.tkap11.spendingrecord.catatpengeluaran;


public class InsertMoneyHandler extends CatatPengeluaranHandler {

	private CatatPengeluaranHandler handler;

	public InsertMoneyHandler(CatatPengeluaranHandler handler) {
		this.handler = handler;
	}

	@Override
	public CatatPengeluaranHandler handleUserRequest(String userMessage) {
		try {
			Integer.parseInt(userMessage);
			description = userMessage;
			messageToUser = "Oke, uang yang digunakan sebesar Rp" + userMessage +
					". Konfirmasi pencatatan dengan menjawab 'Ya' atau ketik 'Batal' untuk membatalkan tindakan";
			return new ConfirmationHandler(this);
		} catch (NumberFormatException e){
			if (userMessage.contains("batal")){
				messageToUser = "Proses pencatatan dibatalkan";
				return null;
			} else {
				messageToUser = "Nominal uang tidak sesuai. Pastikan kamu hanya memasukkan angka, contoh: 10000. " +
						"Jika ingin membatalkan tindakan, ketik 'Batal'";
				return this;
			}
		}
	}

	@Override
	public String getDescription() {
		return handler.getDescription() + ";" + description;
	}
}
