package com.eos.parcelnoticemanager.data;

import com.google.gson.annotations.SerializedName;

public class TokenVO {
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}