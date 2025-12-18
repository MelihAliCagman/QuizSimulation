package com.beraber.triviaquiz.config;

import com.beraber.triviaquiz.entity.Category;
import com.beraber.triviaquiz.entity.Question;
import com.beraber.triviaquiz.entity.User;
import com.beraber.triviaquiz.repository.CategoryRepository;
import com.beraber.triviaquiz.repository.QuestionRepository;
import com.beraber.triviaquiz.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public DataSeeder(CategoryRepository categoryRepository, QuestionRepository questionRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // ADMIN KULLANICISI
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123");
            admin.setRole("ADMIN");
            admin.setTotalScore(0);
            userRepository.save(admin);
        }

        // KATEGORİ VE SORULAR
        // Eğer kategori yoksa (sıfırlandıysa) doldur
        if (categoryRepository.count() == 0) {

            // --- 1. TARİH ---
            Category tarih = createCategory("Tarih", "Geçmişten günümüze önemli olaylar.");
            // Kolay (1)
            addQuestion(tarih, "İstanbul kaç yılında fethedildi?", "1453", "1071", "1299", "1923", "A", 1);
            addQuestion(tarih, "Türkiye Cumhuriyeti'nin ilk cumhurbaşkanı kimdir?", "İsmet İnönü", "Atatürk", "Celal Bayar", "Kenan Evren", "B", 1);
            addQuestion(tarih, "Cumhuriyet kaç yılında ilan edildi?", "1920", "1923", "1919", "1938", "B", 1);
            // ... Buraya daha fazla kolay soru ekleyebilirsin ...

            // Orta (2)
            addQuestion(tarih, "Malazgirt Savaşı hangi yıl yapıldı?", "1071", "1299", "1453", "1922", "A", 2);
            addQuestion(tarih, "Kanuni Sultan Süleyman kaç yıl tahtta kaldı?", "46", "30", "25", "10", "A", 2);
            addQuestion(tarih, "Osmanlı Devleti'nin kurucusu kimdir?", "Osman Bey", "Orhan Bey", "Fatih Sultan Mehmet", "Yavuz Sultan Selim", "A", 2);

            // Zor (3)
            addQuestion(tarih, "Plevne Kahramanı kimdir?", "Gazi Osman Paşa", "Seyit Onbaşı", "Kazım Karabekir", "Fevzi Çakmak", "A", 3);
            addQuestion(tarih, "Lale Devri hangi padişah zamanında yaşanmıştır?", "III. Ahmet", "I. Mahmut", "IV. Murat", "II. Abdülhamit", "A", 3);
            addQuestion(tarih, "İlk Osmanlı halifesi kimdir?", "Yavuz Sultan Selim", "Kanuni", "Fatih", "II. Murat", "A", 3);


            // --- 2. BİLİM ---
            Category bilim = createCategory("Bilim", "Fizik, Kimya, Biyoloji ve Uzay.");
            // Kolay
            addQuestion(bilim, "Suyun formülü nedir?", "H2O", "CO2", "NaCl", "He", "A", 1);
            addQuestion(bilim, "Hangi gezegen 'Kızıl Gezegen' olarak bilinir?", "Mars", "Venüs", "Jüpiter", "Satürn", "A", 1);

            // Orta
            addQuestion(bilim, "Periyodik tabloda 'Fe' hangi elementtir?", "Demir", "Flor", "Fosfor", "Fransiyum", "A", 2);
            addQuestion(bilim, "DNA'nın yapısını kim keşfetti?", "Watson ve Crick", "Darwin", "Pasteur", "Tesla", "A", 2);

            // Zor
            addQuestion(bilim, "Işık hızı saniyede yaklaşık kaç km'dir?", "300.000", "150.000", "1.000.000", "3.000", "A", 3);
            addQuestion(bilim, "Termodinamiğin ikinci yasası ne ile ilgilidir?", "Entropi", "Enerji Korunumu", "Yerçekimi", "Hareket", "A", 3);


            // --- 3. SPOR ---
            Category spor = createCategory("Spor", "Futbol, Basketbol ve Olimpiyatlar.");
            addQuestion(spor, "Futbol maçı kaç kişiyle oynanır?", "11", "10", "12", "7", "A", 1);
            addQuestion(spor, "Basketbolda bir takım sahada kaç kişiyle yer alır?", "5", "6", "7", "11", "A", 1);

            addQuestion(spor, "2010 Dünya Kupası şampiyonu kimdir?", "İspanya", "Hollanda", "Almanya", "Brezilya", "A", 2);
            addQuestion(spor, "Hangi spor dalında 'Ace' terimi kullanılır?", "Tenis", "Futbol", "Basketbol", "Yüzme", "A", 2);

            addQuestion(spor, "NBA tarihinin en çok sayı atan oyuncusu kimdir?", "LeBron James", "Kareem Abdul-Jabbar", "Michael Jordan", "Kobe Bryant", "A", 3);
            addQuestion(spor, "Olimpiyat halkalarındaki mavi renk hangi kıtayı temsil eder?", "Avrupa", "Asya", "Afrika", "Amerika", "A", 3);

            // ... Diğer kategorileri (Sanat, Edebiyat) aynı mantıkla ekleyebilirsin ...

            System.out.println("--- TÜM SORULAR ZORLUK SEVİYELERİYLE YÜKLENDİ ---");
        }
    }

    private Category createCategory(String name, String desc) {
        Category c = new Category();
        c.setName(name);
        c.setDescription(desc);
        return categoryRepository.save(c);
    }

    // GÜNCELLENMİŞ METOD: int difficulty parametresi eklendi
    private void addQuestion(Category cat, String text, String a, String b, String c, String d, String correct, int difficulty) {
        Question q = new Question();
        q.setCategory(cat);
        q.setQuestionText(text);
        q.setOptionA(a); q.setOptionB(b); q.setOptionC(c); q.setOptionD(d);
        q.setCorrectAnswer(correct);
        q.setDifficulty(difficulty); // Zorluk seviyesini set ediyoruz
        questionRepository.save(q);
    }
}