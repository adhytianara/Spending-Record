package com.tkap11.spendingrecord.model;

public class User {
    private Long id;
    private String userId;
    private String displayName;

    public User(Long aId, String aUserId, String aDisplayName)
    {
        id=aId;
        userId=aUserId;
        displayName=aDisplayName;
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