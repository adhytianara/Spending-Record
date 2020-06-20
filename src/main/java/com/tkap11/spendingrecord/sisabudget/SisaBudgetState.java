package com.tkap11.spendingrecord.sisabudget;

import java.util.Arrays;
import java.util.List;

public abstract class SisaBudgetState {
    protected SisaBudgetState state;
    protected String description;
    protected String messageToUser;
    private final List<String> categories = Arrays.asList(
            "makanan", "transportasi", "tagihan", "belanja", "lainnya");
    List<String> action = Arrays.asList(
            "menu", "atur budget", "sisa budget", "catat pengeluaran", "lihat laporan", "ingatkan saya");

    public abstract SisaBudgetState userChooseCategory(String userMessage);

    public abstract SisaBudgetState unknownMessage();

    public abstract SisaBudgetState userCancelOperation();

    public abstract SisaBudgetState otherServiceMessage();

    public abstract String getDescription();

    public String getMessageToUser() {
        return messageToUser;
    }

    /**
     * Check input and respond it with corresponding method.
     */
    public SisaBudgetState handleUserRequest(String userMessage){
        if (categories.contains(userMessage)) {
            return userChooseCategory(userMessage);
        } else if (userMessage.contains("batal")) {
            return userCancelOperation();
        } else if (action.contains(userMessage)) {
            return otherServiceMessage();
        }
        else {
            return unknownMessage();
        }
    }
}
