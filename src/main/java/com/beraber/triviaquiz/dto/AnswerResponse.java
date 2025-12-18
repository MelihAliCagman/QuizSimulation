package com.beraber.triviaquiz.dto;

public class AnswerResponse {
    private boolean correct;
    private String message;
    private String correctAnswer;
    private int pointsChange; // O sorudan kazanılan veya kaybedilen puan

    public AnswerResponse(boolean correct, String message, String correctAnswer, int pointsChange) {
        this.correct = correct;
        this.message = message;
        this.correctAnswer = correctAnswer;
        this.pointsChange = pointsChange;
    }

    // Getter ve Setterlar
    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public int getPointsChange() { return pointsChange; }
    public void setPointsChange(int pointsChange) { this.pointsChange = pointsChange; }
}