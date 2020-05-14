package com.tkap11.spendingrecord.catatpengeluaran;

import java.util.Arrays;
import java.util.List;

public class ChooseCategoryHandler extends CatatPengeluaranHandler {
	private final List<String> categories = Arrays.asList(
			"makanan", "transportasi", "tagihan", "belanja", "lainnya");

	public ChooseCategoryHandler(String senderId, String displayName) {
		this.description = senderId + ";" + displayName;
	}

	@Override
	public CatatPengeluaranHandler handleUserRequest(String userMessage) {
		if (categories.contains(userMessage)) {
			description = description + ";" + userMessage;
			messageToUser = "Kategori berhasil dipilih. " +
					"Berapa uang yang kamu habiskan untuk " + userMessage + " ?";
			return new InsertMoneyHandler(this);
		} else if (userMessage.contains("batal")){
			messageToUser = "Proses pencatatan dibatalkan";
			return null;
		} else {
			messageToUser = "Kategori tidak sesuai! Pilih salah satu dari lima kategori yang tersedia. " +
					"Jika ingin membatalkan tindakan, ketik 'Batal'";
			return this;
		}
	}

	@Override
	public String getDescription() {
		return this.description;
	}
}
