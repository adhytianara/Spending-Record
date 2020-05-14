package com.tkap11.spendingrecord.catatpengeluaran;

public abstract class CatatPengeluaranHandler {
	protected CatatPengeluaranHandler handler;
	protected String description, messageToUser;

	public abstract CatatPengeluaranHandler handleUserRequest(String userMessage);
	public abstract String getDescription();
	public String getMessageToUser() {
		return messageToUser;
	}
}
