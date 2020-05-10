package com.tkap11.spendingrecord.model;


public class Spending {
    private Long id;
    private String userId, displayName, category, timestamp, nominal;

    public Spending(Long id, String userId, String displayName, String category, String timestamp, String nominal)
    {
        this.id=id;
        this.userId=userId;
        this.displayName=displayName;
        this.category=category;
        this.timestamp=timestamp;
        this.nominal=nominal;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}