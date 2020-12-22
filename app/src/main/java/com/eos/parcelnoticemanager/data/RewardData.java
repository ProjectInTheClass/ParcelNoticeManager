package com.eos.parcelnoticemanager.data;

public class RewardData {
    String name;
    int score;
    String reason;

    public RewardData(String name){
        this.name = name;
    }

    public void plusScore(int score, String reason){
        this.score = score;
        this.reason = reason;
    }

    public void minusScore(int score, String reason){
        this.score = - score;
        this.reason = reason;
    }
}
