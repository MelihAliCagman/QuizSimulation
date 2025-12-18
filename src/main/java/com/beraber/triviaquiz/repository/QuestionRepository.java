package com.beraber.triviaquiz.repository;

import com.beraber.triviaquiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // Spring Data JPA'nın "Sihirli" metod isimlendirmesi:
    // "findBy" dedikten sonra değişken adını (CategoryId) yazarsan
    // arka planda "SELECT * FROM questions WHERE category_id = ?" sorgusunu otomatik yazar.
    List<Question> findByCategoryId(Long categoryId);
}