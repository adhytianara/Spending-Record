package com.tkap11.spendingrecord.state.aturbudget;

import java.util.Arrays;
import java.util.List;

import com.tkap11.spendingrecord.state.State;
import org.springframework.stereotype.Service;

@Service
public abstract class AturState implements State{
	private final List<String> categories = Arrays.asList(
			"makanan", "transportasi", "tagihan", "belanja", "lainnya");
	public String category;
	public long amount;
	public String messageToUser;

	public abstract AturState userChooseCategory(String userMessage, String senderId);
	public abstract AturState userInsertMoney(long userMessage);
	public abstract AturState userConfirmation(String senderId);
	public abstract AturState unknownMessage();
	public AturState userCancelOperation() {
		this.messageToUser = "Proses pengaturan budget dibatalkan";
		return null;
	}
	

	public AturState handleUserRequest(String userMessage, String senderId){
		if (categories.contains(userMessage)) {
			return userChooseCategory(userMessage, senderId);
		} else if (isNominal(userMessage)){
			return userInsertMoney(Long.parseLong(userMessage));
		} else if (userMessage.contains("ya")){
			return userConfirmation(senderId);
		} else if (userMessage.contains("batal")){
			return userCancelOperation();
		} else {
			return unknownMessage();
		}
	}

	private boolean isNominal(String userMessage) {
		try {
			Integer.parseInt(userMessage);
			return true;
		} catch (NumberFormatException e){
			return false;
		}
	}
}