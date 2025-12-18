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

        // ADMIN KULLANICISI (Yoksa oluştur)
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123");
            admin.setRole("ADMIN");
            admin.setTotalScore(0);
            userRepository.save(admin);
        }

        // KATEGORİ VE SORULAR (Eğer kategori yoksa doldur)
        if (categoryRepository.count() == 0) {

            // --- 1. TARİH ---
            Category tarih = createCategory("Tarih", "Geçmişten günümüze önemli olaylar.");
            addQuestion(tarih, "İstanbul kaç yılında fethedildi?", "1453", "1071", "1299", "1923", "A", 1, null);
            addQuestion(tarih, "Türkiye Cumhuriyeti'nin kurucusu kimdir?", "İsmet İnönü", "Atatürk", "Kazım Karabekir", "Celal Bayar", "B", 1, null);
            addQuestion(tarih, "Malazgirt Savaşı hangi yıl yapıldı?", "1071", "1299", "1453", "1922", "A", 2, null);
            addQuestion(tarih, "Plevne Kahramanı kimdir?", "Gazi Osman Paşa", "Seyit Onbaşı", "Kazım Karabekir", "Fevzi Çakmak", "A", 3, null);

            // --- 2. BİLİM ---
            Category bilim = createCategory("Bilim", "Fizik, Kimya, Biyoloji ve Uzay.");
            addQuestion(bilim, "Suyun formülü nedir?", "H2O", "CO2", "NaCl", "He", "A", 1, null);
            addQuestion(bilim, "Hangi gezegen 'Kızıl Gezegen' olarak bilinir?", "Mars", "Venüs", "Jüpiter", "Satürn", "A", 1, null);
            addQuestion(bilim, "DNA'nın yapısını kim keşfetti?", "Watson ve Crick", "Darwin", "Pasteur", "Tesla", "A", 2, null);
            addQuestion(bilim, "Işık hızı saniyede yaklaşık kaç km'dir?", "300.000", "150.000", "1.000.000", "3.000", "A", 3, null);

            // --- 3. SPOR ---
            Category spor = createCategory("Spor", "Futbol, Basketbol ve Olimpiyatlar.");
            addQuestion(spor, "Futbol maçı kaç kişiyle oynanır?", "11", "10", "12", "7", "A", 1, null);
            addQuestion(spor, "Basketbolda bir takım sahada kaç kişiyle yer alır?", "5", "6", "7", "11", "A", 1, null);
            addQuestion(spor, "2010 Dünya Kupası şampiyonu kimdir?", "İspanya", "Hollanda", "Almanya", "Brezilya", "A", 2, null);
            addQuestion(spor, "NBA tarihinin en çok sayı atan oyuncusu kimdir?", "LeBron James", "Kareem Abdul-Jabbar", "Michael Jordan", "Kobe Bryant", "A", 3, null);

            // --- 4. SANAT (GERİ GELDİ!) ---
            Category sanat = createCategory("Sanat", "Resim, Müzik ve Sinema.");
            // Örnek resimli soru
            addQuestion(sanat, "'Mona Lisa' tablosu kime aittir?", "Van Gogh", "Picasso", "Da Vinci", "Michelangelo", "C", 1, "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg/300px-Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg");
            addQuestion(sanat, "'Yıldızlı Gece' tablosunun ressamı kimdir?", "Salvador Dali", "Vincent van Gogh", "Rembrandt", "Monet", "B", 2, null);
            addQuestion(sanat, "Wolfgang Amadeus Mozart hangi ülkenin vatandaşıydı?", "Almanya", "Avusturya", "İtalya", "Fransa", "B", 2, null);
            addQuestion(sanat, "'Çığlık' tablosu kime aittir?", "Edvard Munch", "Klimt", "Warhol", "Matisse", "A", 3, null);

            // --- 5. EDEBİYAT (GERİ GELDİ!) ---
            Category edebiyat = createCategory("Edebiyat", "Romanlar, Şiirler ve Yazarlar.");
            addQuestion(edebiyat, "'Suç ve Ceza' kitabının yazarı kimdir?", "Tolstoy", "Dostoyevski", "Gogol", "Çehov", "B", 1, null);
            addQuestion(edebiyat, "İstiklal Marşı'mızın şairi kimdir?", "Mehmet Akif Ersoy", "Namık Kemal", "Ziya Gökalp", "Orhan Veli", "A", 1, null);
            addQuestion(edebiyat, "Harry Potter serisinin yazarı kimdir?", "J.R.R. Tolkien", "J.K. Rowling", "George R.R. Martin", "Stephen King", "B", 2, null);
            addQuestion(edebiyat, "'Romeo ve Juliet' eserini kim yazmıştır?", "Charles Dickens", "William Shakespeare", "Victor Hugo", "Mark Twain", "B", 2, null);

            // --- 6. GENEL KÜLTÜR ---
            Category genelKultur = createCategory("Genel Kültür", "Tarih, coğrafya, güncel olaylar ve ortaya karışık sorular.");

            // KOLAY (1)
            addQuestion(genelKultur, "Bir yıl kaç gündür?", "360", "365", "366", "350", "B", 1, null);
            addQuestion(genelKultur, "Türkiye'nin başkenti neresidir?", "İstanbul", "Ankara", "İzmir", "Bursa", "B", 1, "https://upload.wikimedia.org/wikipedia/commons/thumb/0/03/Anitkabir_front.jpg/320px-Anitkabir_front.jpg"); // Anıtkabir resmiyle
            addQuestion(genelKultur, "İnternette arama yapmak için en çok kullanılan motor hangisidir?", "Bing", "Yahoo", "Google", "DuckDuckGo", "C", 1, null);

            // ORTA (2)
            addQuestion(genelKultur, "Satranç tahtasında toplam kaç kare vardır?", "32", "64", "100", "16", "B", 2, null);
            addQuestion(genelKultur, "Japonya'nın para birimi nedir?", "Dolar", "Euro", "Yen", "Won", "C", 2, null);
            addQuestion(genelKultur, "Resimdeki ünlü yapı hangi şehirdedir?", "Paris", "Londra", "Roma", "New York", "A", 2, "https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Tour_Eiffel_Wikimedia_Commons_%28cropped%29.jpg/300px-Tour_Eiffel_Wikimedia_Commons_%28cropped%29.jpg"); // Eyfel Kulesi resmi

            // ZOR (3)
            addQuestion(genelKultur, "Dünyanın en derin noktası olan Mariana Çukuru hangi okyanustadır?", "Atlas Okyanusu", "Hint Okyanusu", "Büyük Okyanus", "Arktik Okyanusu", "C", 3, null);
            addQuestion(genelKultur, "Aspirinin hammaddesi olan ağaç hangisidir?", "Söğüt", "Çam", "Meşe", "Kavak", "A", 3, null);
            addQuestion(genelKultur, "Magna Carta hangi ülkede imzalanmıştır?", "Fransa", "İngiltere", "Almanya", "İtalya", "B", 3, null);

            System.out.println("--- TÜM KATEGORİLER VE SORULAR YÜKLENDİ ---");
        }
    }

    private Category createCategory(String name, String desc) {
        Category c = new Category();
        c.setName(name);
        c.setDescription(desc);
        return categoryRepository.save(c);
    }

    // GÜNCELLENMİŞ METOD: String imageUrl parametresi eklendi
    private void addQuestion(Category cat, String text, String a, String b, String c, String d, String correct, int difficulty, String imgUrl) {
        Question q = new Question();
        q.setCategory(cat);
        q.setQuestionText(text);
        q.setOptionA(a); q.setOptionB(b); q.setOptionC(c); q.setOptionD(d);
        q.setCorrectAnswer(correct);
        q.setDifficulty(difficulty);
        q.setImageUrl(imgUrl); // Resim URL'sini kaydet
        questionRepository.save(q);
    }
}