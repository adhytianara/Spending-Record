package com.tkap11.spendingrecord.state.aturbudget;

public class AturConfirmationState extends AturState {

	public AturConfirmationState(String category, long amount) {
		this.category = category;
		this.amount = amount;
	}

	@Override
	public AturState userChooseCategory(String userMessage, String senderId) {
		return unknownMessage();
	}

	@Override
	public AturState userInsertMoney(long userMessage) {
		return unknownMessage();
	}

	@Override
	public AturState userConfirmation(String senderId) {
		this.messageToUser = "Budget berhasil diubah";

		return null;
	}

	@Override
	public AturState unknownMessage() {
		this.messageToUser = "Konfirmasi perubahan budget dengan menjawab 'Ya' " +
				"atau ketik 'Batal' untuk membatalkan tindakan";
		return this;
	}

}
