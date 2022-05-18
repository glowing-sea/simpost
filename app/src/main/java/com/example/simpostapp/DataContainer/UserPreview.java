package com.example.simpostapp.DataContainer;

/*
A user class for preview which contain username and signature only.
 */
public class UserPreview {
    private String username;
    private String signature;

    public UserPreview(String username, String signature) {
        this.username = username;
        this.signature = signature;
    }

    public String getUsername() {
        return username;
    }

    public String getSignature() {
        return signature;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
