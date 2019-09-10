package com.example.project;

public class feedback_JC {
    int id;
    String feedback;

    public feedback_JC( String feedback) {

        this.feedback = feedback;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
