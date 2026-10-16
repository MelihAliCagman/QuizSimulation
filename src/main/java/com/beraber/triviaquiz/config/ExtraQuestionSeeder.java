package com.beraber.triviaquiz.config;

import com.beraber.triviaquiz.entity.Category;
import com.beraber.triviaquiz.entity.Question;
import com.beraber.triviaquiz.repository.CategoryRepository;
import com.beraber.triviaquiz.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Her kategori için ek soruları PostgreSQL'e yazar.
 * Tekrar çalıştırılabilir (idempotent): aynı soru metni zaten varsa atlanır.
 */
@Component
@Order(2)
public class ExtraQuestionSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private int added = 0;

    public ExtraQuestionSeeder(CategoryRepository categoryRepository, QuestionRepository questionRepository) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) {
        seedHistory();
        seedScience();
        seedSports();
        seedArt();
        seedLiterature();
        seedGeneral();
        System.out.println("--- EK SORULAR KONTROL EDILDI, EKLENEN: " + added + " ---");
    }

    private void seedHistory() {
        Category c = category("Tarih", "Geçmişten günümüze önemli olaylar.");
        q(c, "Osmanlı Devleti'nin kurucusu kimdir?", "Osman Bey", "Orhan Bey", "Ertuğrul Gazi", "Yıldırım Bayezid", 1);
        q(c, "Türkiye Cumhuriyeti hangi yıl ilan edildi?", "1923", "1919", "1921", "1938", 1);
        q(c, "Mustafa Kemal Atatürk hangi şehirde doğmuştur?", "Selanik", "İstanbul", "Ankara", "Manastır", 1);
        q(c, "Atatürk'ün Samsun'a çıkarak Kurtuluş Savaşı'nı başlattığı tarih hangisidir?", "19 Mayıs 1919", "23 Nisan 1920", "30 Ağustos 1922", "29 Ekim 1923", 1);
        q(c, "Çanakkale Deniz Zaferi hangi yıl kazanılmıştır?", "1915", "1914", "1916", "1918", 2);
        q(c, "Kanuni Sultan Süleyman hangi padişahın oğludur?", "Yavuz Sultan Selim", "II. Bayezid", "Fatih Sultan Mehmet", "I. Murad", 2);
        q(c, "Türkiye'de harf devrimi (Latin alfabesine geçiş) hangi yıl yapıldı?", "1928", "1923", "1934", "1925", 2);
        q(c, "Roma İmparatorluğu'nun ilk imparatoru kimdir?", "Augustus", "Jül Sezar", "Neron", "Konstantin", 3);
        q(c, "Tarihin bilinen ilk yazılı barış antlaşması olan Kadeş Antlaşması hangi iki devlet arasında yapılmıştır?", "Mısır ve Hititler", "Mısır ve Asurlular", "Persler ve Yunanlar", "Romalılar ve Kartacalılar", 3);
        q(c, "Osmanlı Devleti'ne ağır şartlar getiren Sevr Antlaşması hangi yıl imzalandı?", "1920", "1918", "1923", "1919", 3);
        q(c, "1402 Ankara Savaşı'nda Yıldırım Bayezid'i yenen hükümdar kimdir?", "Timur", "Cengiz Han", "Attila", "Kubilay Han", 3);
        q(c, "Berlin Duvarı hangi yıl yıkılmıştır?", "1989", "1991", "1985", "1961", 3);
    }

    private void seedScience() {
        Category c = category("Bilim", "Fizik, Kimya, Biyoloji ve Uzay.");
        q(c, "Güneş Sistemi'ndeki en büyük gezegen hangisidir?", "Jüpiter", "Satürn", "Neptün", "Dünya", 1);
        q(c, "Bitkiler fotosentez sırasında havadan hangi gazı alır?", "Karbondioksit", "Oksijen", "Azot", "Hidrojen", 1);
        q(c, "İnsan vücudundaki en büyük organ hangisidir?", "Deri", "Karaciğer", "Akciğer", "Beyin", 1);
        q(c, "Periyodik tabloda altının sembolü nedir?", "Au", "Ag", "Fe", "Go", 2);
        q(c, "Yetişkin bir insanın vücudunda kaç kemik bulunur?", "206", "201", "212", "300", 2);
        q(c, "Apollo 11 görevi ile insanlar Ay'a hangi yıl ilk kez ayak bastı?", "1969", "1965", "1972", "1961", 2);
        q(c, "Güneş'ten çıkan ışık Dünya'ya yaklaşık ne kadar sürede ulaşır?", "8 dakika", "8 saniye", "1 saat", "24 saat", 2);
        q(c, "Hangi vitamin güneş ışığı sayesinde ciltte üretilir?", "D vitamini", "C vitamini", "A vitamini", "B12 vitamini", 2);
        q(c, "Bir atomun çekirdeğinde bulunan yüksüz (nötr) parçacık hangisidir?", "Nötron", "Proton", "Elektron", "Foton", 3);
        q(c, "Mohs sertlik ölçeğine göre en sert mineral hangisidir?", "Elmas", "Kuvars", "Topaz", "Talk", 3);
        q(c, "DNA'yı oluşturan dört bazın doğru sıralaması hangisidir?", "Adenin, Timin, Guanin, Sitozin", "Adenin, Urasil, Guanin, Sitozin", "Adenin, Timin, Glikoz, Sitozin", "Alanin, Timin, Guanin, Sitozin", 3);
        q(c, "Hubble Uzay Teleskobu hangi yıl yörüngeye fırlatıldı?", "1990", "1985", "1995", "2001", 3);
    }

    private void seedSports() {
        Category c = category("Spor", "Futbol, Basketbol ve Olimpiyatlar.");
        q(c, "Voleybolda bir takım sahada kaç oyuncuyla yer alır?", "6", "5", "7", "11", 1);
        q(c, "Yaz Olimpiyat Oyunları kaç yılda bir düzenlenir?", "4", "2", "3", "5", 1);
        q(c, "Wimbledon turnuvası hangi spor dalında oynanır?", "Tenis", "Golf", "Kriket", "Badminton", 1);
        q(c, "Formula 1'de geleneksel olarak kırmızı renkli araçlarıyla tanınan takım hangisidir?", "Ferrari", "McLaren", "Mercedes", "Williams", 1);
        q(c, "Maraton koşusunun resmî mesafesi kaç kilometredir?", "42,195", "21,097", "50", "35", 2);
        q(c, "2002 Dünya Kupası'nda Türkiye milli takımı kaçıncı olmuştur?", "Üçüncü", "İkinci", "Dördüncü", "Birinci", 2);
        q(c, "Michael Phelps hangi spor dalında olimpiyat madalyalarıyla tanınır?", "Yüzme", "Atletizm", "Cimnastik", "Su topu", 2);
        q(c, "Judo hangi ülkede ortaya çıkmış bir spordur?", "Japonya", "Çin", "Kore", "Tayland", 2);
        q(c, "Brezilya milli futbol takımı Dünya Kupası'nı toplam kaç kez kazanmıştır?", "5", "4", "6", "3", 2);
        q(c, "İlk FIFA Dünya Kupası hangi ülkede düzenlenmiştir?", "Uruguay", "Brezilya", "İtalya", "Fransa", 3);
        q(c, "Usain Bolt'un 100 metrede kırdığı dünya rekoru kaç saniyedir?", "9,58", "9,69", "9,72", "9,48", 3);
        q(c, "Rafael Nadal en çok şampiyonluğu hangi Grand Slam turnuvasında kazanmıştır?", "Roland Garros", "Wimbledon", "US Open", "Avustralya Açık", 3);
    }

    private void seedArt() {
        Category c = category("Sanat", "Resim, Müzik ve Sinema.");
        q(c, "Müzikte temel nota sayısı kaçtır?", "7", "5", "8", "12", 1);
        q(c, "Kırmızı ve sarı boyanın karışımından hangi renk elde edilir?", "Turuncu", "Yeşil", "Mor", "Kahverengi", 1);
        q(c, "Standart bir piyanoda kaç tuş bulunur?", "88", "76", "64", "100", 2);
        q(c, "'Son Akşam Yemeği' tablosunun ressamı kimdir?", "Leonardo da Vinci", "Raffaello", "Caravaggio", "Botticelli", 2);
        q(c, "Beethoven hangi ülkenin bestecisidir?", "Almanya", "Avusturya", "Fransa", "İtalya", 2);
        q(c, "'Guernica' tablosu hangi sanatçıya aittir?", "Pablo Picasso", "Salvador Dali", "Joan Miró", "Francisco Goya", 2);
        q(c, "Titanic filminin yönetmeni kimdir?", "James Cameron", "Steven Spielberg", "Christopher Nolan", "Ridley Scott", 2);
        q(c, "Sistina Şapeli'nin tavan freskolarını kim yapmıştır?", "Michelangelo", "Raffaello", "Leonardo da Vinci", "Botticelli", 2);
        q(c, "Salvador Dali hangi sanat akımının en ünlü temsilcilerindendir?", "Sürrealizm", "Kübizm", "Empresyonizm", "Barok", 3);
        q(c, "Claude Monet'nin 'Gün Doğumu: Izlenim' tablosu hangi akıma adını vermiştir?", "Empresyonizm", "Ekspresyonizm", "Fovizm", "Dadaizm", 3);
        q(c, "Osman Hamdi Bey'in en ünlü eseri hangisidir?", "Kaplumbağa Terbiyecisi", "Balıkçı Çocuk", "Zeybek", "Sultanahmet Meydanı", 3);
        q(c, "'Kuğu Gölü' balesinin bestecisi kimdir?", "Çaykovski", "Mozart", "Stravinski", "Ravel", 3);
    }

    private void seedLiterature() {
        Category c = category("Edebiyat", "Romanlar, Şiirler ve Yazarlar.");
        q(c, "'Çalıkuşu' romanının yazarı kimdir?", "Reşat Nuri Güntekin", "Halide Edib Adıvar", "Yakup Kadri Karaosmanoğlu", "Peyami Safa", 1);
        q(c, "'Küçük Prens' kitabının yazarı kimdir?", "Antoine de Saint-Exupéry", "Victor Hugo", "Jules Verne", "Albert Camus", 1);
        q(c, "Pinokyo yalan söylediğinde vücudunun hangi kısmı uzar?", "Burnu", "Kulağı", "Eli", "Saçı", 1);
        q(c, "'İnce Memed' romanının yazarı kimdir?", "Yaşar Kemal", "Orhan Kemal", "Fakir Baykurt", "Kemal Tahir", 2);
        q(c, "'Don Kişot' romanının yazarı kimdir?", "Cervantes", "Dante", "Goethe", "Dumas", 2);
        q(c, "'Kürk Mantolu Madonna' romanının yazarı kimdir?", "Sabahattin Ali", "Sait Faik Abasıyanık", "Oğuz Atay", "Ahmet Hamdi Tanpınar", 2);
        q(c, "'1984' adlı distopya romanının yazarı kimdir?", "George Orwell", "Aldous Huxley", "Ray Bradbury", "H. G. Wells", 2);
        q(c, "'İlyada' ve 'Odysseia' destanlarının yazarı olarak kabul edilen kişi kimdir?", "Homeros", "Sofokles", "Aristo", "Vergilius", 2);
        q(c, "Orhan Pamuk Nobel Edebiyat Ödülü'nü hangi yıl almıştır?", "2006", "1996", "2001", "2012", 3);
        q(c, "'İlahi Komedya' adlı eserin yazarı kimdir?", "Dante Alighieri", "Petrarca", "Boccaccio", "Machiavelli", 3);
        q(c, "'Yüzyıllık Yalnızlık' romanının yazarı kimdir?", "Gabriel García Márquez", "Jorge Luis Borges", "Pablo Neruda", "Paulo Coelho", 3);
        q(c, "Türk edebiyatının ilk edebi romanı kabul edilen 'Taaşşuk-ı Talat ve Fitnat'ın yazarı kimdir?", "Şemsettin Sami", "Namık Kemal", "Ahmet Mithat Efendi", "Recaizade Mahmut Ekrem", 3);
    }

    private void seedGeneral() {
        Category c = category("Genel Kültür", "Tarih, coğrafya, güncel olaylar ve ortaya karışık sorular.");
        q(c, "Dünyanın en büyük okyanusu hangisidir?", "Büyük Okyanus", "Atlas Okyanusu", "Hint Okyanusu", "Arktik Okyanusu", 1);
        q(c, "Türk bayrağındaki renkler hangileridir?", "Kırmızı ve beyaz", "Kırmızı ve sarı", "Mavi ve beyaz", "Yeşil ve kırmızı", 1);
        q(c, "Dünyanın en kalabalık kıtası hangisidir?", "Asya", "Afrika", "Avrupa", "Güney Amerika", 1);
        q(c, "Hem Asya hem Avrupa kıtasında toprakları bulunan Türkiye'nin şehri hangisidir?", "İstanbul", "Ankara", "Edirne", "Çanakkale", 1);
        q(c, "Türkiye'nin en yüksek dağı hangisidir?", "Ağrı Dağı", "Erciyes", "Uludağ", "Kaçkar Dağı", 2);
        q(c, "Türkiye'nin en uzun nehri hangisidir?", "Kızılırmak", "Fırat", "Sakarya", "Yeşilırmak", 2);
        q(c, "Brezilya'nın resmî dili hangisidir?", "Portekizce", "İspanyolca", "İngilizce", "Fransızca", 2);
        q(c, "Dünyanın en küçük ülkesi hangisidir?", "Vatikan", "Monako", "San Marino", "Lihtenştayn", 2);
        q(c, "Türkiye'nin yüzölçümü bakımından en büyük gölü hangisidir?", "Van Gölü", "Tuz Gölü", "Beyşehir Gölü", "Eğirdir Gölü", 2);
        q(c, "Avustralya'nın başkenti neresidir?", "Canberra", "Sidney", "Melbourne", "Perth", 3);
        q(c, "Eyfel Kulesi hangi yıl tamamlanmıştır?", "1889", "1789", "1925", "1850", 3);
        q(c, "Dünyanın en derin gölü hangisidir?", "Baykal Gölü", "Victoria Gölü", "Superior Gölü", "Titicaca Gölü", 3);
        q(c, "Rubik Küpü'nü kim icat etmiştir?", "Ernő Rubik", "Alexey Pajitnov", "Nikola Tesla", "Rudolf Diesel", 3);
    }

    private Category category(String name, String description) {
        Category existing = categoryRepository.findByName(name);
        if (existing != null) return existing;
        Category c = new Category();
        c.setName(name);
        c.setDescription(description);
        return categoryRepository.save(c);
    }

    /** Doğru şık, soru metnine bağlı sabit bir konuma (A-D) yerleştirilir; böylece cevap hep aynı harfte olmaz. */
    private void q(Category cat, String text, String correct, String wrong1, String wrong2, String wrong3, int difficulty) {
        if (questionRepository.existsByQuestionText(text)) return;

        String[] options = {wrong1, wrong2, wrong3};
        int pos = Math.floorMod(text.hashCode(), 4);
        String[] slots = new String[4];
        slots[pos] = correct;
        for (int i = 0, w = 0; i < 4; i++) {
            if (slots[i] == null) slots[i] = options[w++];
        }

        Question question = new Question();
        question.setCategory(cat);
        question.setQuestionText(text);
        question.setOptionA(slots[0]);
        question.setOptionB(slots[1]);
        question.setOptionC(slots[2]);
        question.setOptionD(slots[3]);
        question.setCorrectAnswer(String.valueOf((char) ('A' + pos)));
        question.setDifficulty(difficulty);
        questionRepository.save(question);
        added++;
    }
}
