package com.beraber.triviaquiz.service;

import com.beraber.triviaquiz.dto.AnswerResponse;
import com.beraber.triviaquiz.entity.Question;
import com.beraber.triviaquiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    // --- YENİ: ÖZEL OYUN OLUŞTURMA ---
    public List<Question> createCustomGame(Long categoryId, int easyCount, int mediumCount, int hardCount) {
        // O kategorideki tüm soruları çek
        List<Question> allQuestions = questionRepository.findByCategoryId(categoryId);

        // Zorluklara göre filtrele
        List<Question> easy = allQuestions.stream().filter(q -> q.getDifficulty() == 1).collect(Collectors.toList());
        List<Question> medium = allQuestions.stream().filter(q -> q.getDifficulty() == 2).collect(Collectors.toList());
        List<Question> hard = allQuestions.stream().filter(q -> q.getDifficulty() == 3).collect(Collectors.toList());

        // Karıştır
        Collections.shuffle(easy);
        Collections.shuffle(medium);
        Collections.shuffle(hard);

        // İstenilen sayıda al (Eğer yeterli soru yoksa, olduğu kadarını al)
        List<Question> gameQuestions = new ArrayList<>();
        gameQuestions.addAll(easy.subList(0, Math.min(easyCount, easy.size())));
        gameQuestions.addAll(medium.subList(0, Math.min(mediumCount, medium.size())));
        gameQuestions.addAll(hard.subList(0, Math.min(hardCount, hard.size())));

        // Oyun içindeki soruları da karıştır ki zorluk sırasına göre gelmesin
        Collections.shuffle(gameQuestions);

        return gameQuestions;
    }

    // --- GÜNCELLENMİŞ CEVAP KONTROLÜ (PUAN VE CEZA) ---
    public AnswerResponse checkAnswer(Long questionId, String selectedOption) {
        Question question = questionRepository.findById(questionId).orElse(null);

        if (question == null) {
            return new AnswerResponse(false, "Soru bulunamadı!", null, 0);
        }

        boolean isCorrect = question.getCorrectAnswer().equalsIgnoreCase(selectedOption);
        int points = 0;
        int difficulty = question.getDifficulty();

        if (isCorrect) {
            // Zorluğa göre puan kazanma
            if (difficulty == 1) points = 10;
            else if (difficulty == 2) points = 20;
            else points = 30; // Zor

            return new AnswerResponse(true, "Tebrikler!", question.getCorrectAnswer(), points);
        } else {
            // Zorluğa göre puan kaybetme (Ceza)
            if (difficulty == 1) points = -5;
            else if (difficulty == 2) points = -10;
            else points = -15; // Zor

            return new AnswerResponse(false, "Yanlış Cevap!", question.getCorrectAnswer(), points);
        }
    }

    // %50 Joker
    public List<String> getTwoWrongOptions(Long questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) return Collections.emptyList();

        String correctAnswer = question.getCorrectAnswer();
        List<String> allOptions = new ArrayList<>(List.of("A", "B", "C", "D"));
        allOptions.remove(correctAnswer);
        Collections.shuffle(allOptions);
        return allOptions.subList(0, 2);
    }

    // Eski method uyumluluğu için (Admin panelinde kullanılıyorsa)
    public List<Question> getQuestionsByCategory(Long categoryId) {
        return questionRepository.findByCategoryId(categoryId);
    }
}