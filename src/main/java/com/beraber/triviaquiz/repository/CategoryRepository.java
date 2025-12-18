package com.beraber.triviaquiz.repository;

import com.beraber.triviaquiz.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

// <Category, Long> -> Hangi Entity ile çalışıyor ve ID tipi ne?
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Şimdilik buraya ekstra bir şey yazmamıza gerek yok.
    // JpaRepository bizim için save, findAll, findById gibi metodları zaten içeriyor.
}