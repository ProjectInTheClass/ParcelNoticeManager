package com.eos.parcelnoticemanager;

class RewardFileControl {
    String name;
    int score;
    String reason;

    public RewardFileControl(String name){
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
