package com.beraber.triviaquiz.controller;

import com.beraber.triviaquiz.dto.AnswerRequest;  // Bu satır eksikti
import com.beraber.triviaquiz.dto.AnswerResponse; // Bu satır eksikti
import com.beraber.triviaquiz.entity.Question;
import com.beraber.triviaquiz.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/category/{categoryId}")
    public List<Question> getQuestionsByCategory(@PathVariable Long categoryId) {
        return questionService.getQuestionsByCategory(categoryId);
    }

    @PostMapping
    public Question createQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    // --- YENİ EKLENEN KISIM ---
    @PostMapping("/check")
    public AnswerResponse checkAnswer(@RequestBody AnswerRequest request) {
        return questionService.checkAnswer(request.getQuestionId(), request.getSelectedOption());
    }

    // Joker Endpoint'i
    // Adres: GET http://localhost:8080/api/questions/joker/fifty-fifty/1
    @GetMapping("/joker/fifty-fifty/{questionId}")
    public List<String> getFiftyFiftyOptions(@PathVariable Long questionId) {
        return questionService.getTwoWrongOptions(questionId);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }

    // Tüm soruları getiren endpoint
    // Adres: GET /api/questions
    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/start")
    public List<Question> startCustomGame(
            @RequestParam Long categoryId,
            @RequestParam int easy,
            @RequestParam int medium,
            @RequestParam int hard) {
        return questionService.createCustomGame(categoryId, easy, medium, hard);
    }
}