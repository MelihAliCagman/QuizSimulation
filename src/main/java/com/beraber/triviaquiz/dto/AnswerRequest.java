package com.beraber.triviaquiz.dto;

public class AnswerRequest {
    private Long questionId;
    private String selectedOption; // Örn: "A", "B"

    // Getter ve Setterlar
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
}