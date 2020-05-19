package com.tkap11.spendingrecord.catatpengeluaran;


public class InsertMoneyState extends CatatPengeluaranState {

	public InsertMoneyState(CatatPengeluaranState state) {
		this.state = state;
	}

	@Override
	public CatatPengeluaranState userChooseCategory(String userMessage) {
		messageToUser = "Nominal uang tidak sesuai. Pastikan kamu hanya memasukkan angka, contoh: 10000. " +
				"Jika ingin membatalkan tindakan, ketik 'Batal'";
		return this;
	}

	@Override
	public CatatPengeluaranState userInsertMoney(String userMessage) {
		Integer.parseInt(userMessage);
		description = userMessage;
		messageToUser = "Oke, uang yang digunakan sebesar Rp" + userMessage +
				". Konfirmasi pencatatan dengan menjawab 'Ya' atau ketik 'Batal' untuk membatalkan tindakan";
		return new ConfirmationState(this);
	}

	@Override
	public CatatPengeluaranState userConfirmation(String userMessage) {
		messageToUser = "Nominal uang tidak sesuai. Pastikan kamu hanya memasukkan angka, contoh: 10000. " +
				"Jika ingin membatalkan tindakan, ketik 'Batal'";
		return this;
	}

	@Override
	public CatatPengeluaranState userCancelOperation() {
		messageToUser = "Proses pencatatan dibatalkan";
		return null;
	}

	@Override
	public CatatPengeluaranState unknownMessage() {
		messageToUser = "Nominal uang tidak sesuai. Pastikan kamu hanya memasukkan angka, contoh: 10000. " +
				"Jika ingin membatalkan tindakan, ketik 'Batal'";
		return this;
	}

	@Override
	public String getDescription() {
		return state.getDescription() + ";" + description;
	}
}
