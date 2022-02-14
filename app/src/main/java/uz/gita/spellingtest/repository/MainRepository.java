package uz.gita.spellingtest.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Keep;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uz.gita.spellingtest.app.LocalStorage;
import uz.gita.spellingtest.contract.FlagContract;
import uz.gita.spellingtest.data.QuestionData;

@Keep
public class MainRepository implements FlagContract.Model {
    private static final ArrayList<QuestionData> quizList = new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final LocalStorage localStorage;
    List<QuestionData> levelQuestions;

    public MainRepository(Context context) {
        localStorage = LocalStorage.getInstance(context);
        levelQuestions = new ArrayList<>();
    }

    @Override
    public void getDataFromFirebase(final FinishListener listener) {
        if (!quizList.isEmpty()) quizList.clear();

        db.collection("QuizList").orderBy("id", Query.Direction.ASCENDING).get().addOnCompleteListener(task -> {
            if (!quizList.isEmpty()) quizList.clear();
            String $4 ="asd";
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    quizList.add(document.toObject(QuestionData.class));
                }
            }
            if (quizList.isEmpty()) getLocalData();
            if (listener != null) listener.getDataFinishedListener();
        });

    }


    @Override
    public int getQuizSize() {
        return quizList.size() / 10;
    }

    @Override
    public int getStageResult(String key) {
        return localStorage.getStageResult(key);
    }

    @Override
    public void saveStageResult(String levelTitle, int correctCount) {
        localStorage.saveStageResult(levelTitle, correctCount);
    }

    @Override
    public void clearResults() {
        localStorage.clearResults();
    }


    @Override
    public void splitLevelQuestions(int startIndex) {
        levelQuestions = quizList.subList(startIndex, startIndex + 10);
        Collections.shuffle(levelQuestions);
    }

    @Override
    public QuestionData getQuizByID(int id) {
        //shuffle variants
        QuestionData quiz = levelQuestions.get(id);
        ArrayList<String> r = new ArrayList<>();
        r.add(quiz.getVariant1());
        r.add(quiz.getVariant2());
        r.add(quiz.getVariant3());
        r.add(quiz.getVariant4());
        Collections.shuffle(r);

        quiz.setVariant1(r.get(0));
        quiz.setVariant2(r.get(1));
        quiz.setVariant3(r.get(2));
        quiz.setVariant4(r.get(3));
        return quiz;
    }


    public void getLocalData() {
        quizList.add(new QuestionData(0, "Durıs jazılǵan sózdi tabıń", "Konstitutciya", "Konstituciya", "Konstitutsiya", "Konstutsiya", "Konstituciya"));
        quizList.add(new QuestionData(1, "Durıs jazılǵan sózdi tabıń", "Kodjeli", "Xójeli", "Xojeli", "Kudjeli", "Xojeli"));
        quizList.add(new QuestionData(2, "Durıs jazılǵan sózdi tabıń", "Nukus", "Nókis", "Nokis", "Nuks", "Nókis"));
        quizList.add(new QuestionData(3, "Durıs jazılǵan sózdi tabıń", "Koncert", "Konsert", "Kansert", "Kontsert", "Koncert"));
        quizList.add(new QuestionData(4, "Durıs jazılǵan sózdi tabıń", "Qaraqalpaqstan", "Karakalpakstan", "Qaraqalpaqıstan", "Qaraqalpaǵıstan", "Qaraqalpaqstan"));
        quizList.add(new QuestionData(5, "Durıs jazılǵan sózdi tabıń", "Saat", "Saǵad", "Sagat", "Saǵat", "Saat"));
        quizList.add(new QuestionData(6, "Durıs jazılǵan sózdi tabıń", "Institut", "Insitut", "Instut", "Ínstitut", "Institut"));
        quizList.add(new QuestionData(7, "Durıs jazılǵan sózdi tabıń", "Quanish", "Quwanısh", "Quanısh", "Quansh", "Quwanısh"));
        quizList.add(new QuestionData(8, "Durıs jazılǵan sózdi tabıń", "Ínformatsiya", "Informaciya", "Ínformaciya", "Informatsya", "Informaciya"));
        quizList.add(new QuestionData(9, "Durıs jazılǵan sózdi tabıń", "Qońırat", "Kungrad", "Końırat", "Qońrat", "Qońırat"));

        quizList.add(new QuestionData(10, "Durıs jazılǵan sózdi tabıń", "Ozbekstan", "Ózbekstan", "Uzbekistan", "Ózbekiston", "Ózbekstan"));
        quizList.add(new QuestionData(11, "Durıs jazılǵan sózdi tabıń", "Aqilli", "Aqıllı", "Aqıli", "Aqılli", "Aqıllı"));
        quizList.add(new QuestionData(12, "Durıs jazılǵan sózdi tabıń", "Assalawma áleykum", "Assalawma áleykım", "Assalawmu aleykum", "Asalawma aleykum", "Assalawma áleykum"));
        quizList.add(new QuestionData(13, "Durıs jazılǵan sózdi tabıń", "Oktyabr", "Oktyabir", "Oktabr", "Aktyabr", "Oktyabr"));
        quizList.add(new QuestionData(14, "Durıs jazılǵan sózdi tabıń", "Taqtakopir", "Taxtakópir", "Taxtakopir", "Taktakopir", "Taxtakópir"));
        quizList.add(new QuestionData(15, "Durıs jazılǵan sózdi tabıń", "Kolledj", "Kallej", "Kollej", "Koledj", "Kolledj"));
        quizList.add(new QuestionData(16, "Durıs jazılǵan sózdi tabıń", "Bagban", "Baǵman", "Baǵpan", "Bagpan", "Baǵman"));
        quizList.add(new QuestionData(17, "Durıs jazılǵan sózdi tabıń", "Sauaplı", "Sawablı", "Sawaplı", "Sauapli", "Sawaplı"));
        quizList.add(new QuestionData(18, "Durıs jazılǵan sózdi tabıń", "Aǵayinshilik", "Aǵaynshiliq", "Agayinshilik", "Agaynshilik", "Aǵayinshilik"));
        quizList.add(new QuestionData(19, "Durıs jazılǵan sózdi tabıń", "Operaciya", "Operacıya", "Aperatsiya", "Operatsiya", "Operaciya"));

        quizList.add(new QuestionData(20, "Durıs jazılǵan sózdi tabıń", "Madeniyat", "Mádenyat", "Madenyad", "Mádeniyat", "Mádeniyat"));
        quizList.add(new QuestionData(21, "Durıs jazılǵan sózdi tabıń", "Oqıwshılar", "Okıushılar", "Oqushılar", "Oquwshılar", "Oqıwshılar"));
        quizList.add(new QuestionData(22, "Durıs jazılǵan sózdi tabıń", "Rawajlanıw", "Rauajlanuw", "Rauajlanıw", "Rauajlanu", "Rawajlanıw"));
        quizList.add(new QuestionData(23, "Durıs jazılǵan sózdi tabıń", "Qorqıtıw", "Qorqıtw", "Korkıtuw", "Qorkıtuw", "Qorqıtıw"));
        quizList.add(new QuestionData(24, "Durıs jazılǵan sózdi tabıń", "Pitkeriwshi", "Pitkerúwshi", "Pıtkeriwshi", "Pitkerushi", "Pitkeriwshi"));
        quizList.add(new QuestionData(25, "Durıs jazılǵan sózdi tabıń", "Shomanay", "Shumanay", "Shuwmanay", "Chumanay", "Shomanay"));
        quizList.add(new QuestionData(26, "Durıs jazılǵan sózdi tabıń", "Lampochka", "Lampuchka", "Lampshkı", "Lamposhka", "Lampochka"));
        quizList.add(new QuestionData(27, "Durıs jazılǵan sózdi tabıń", "Pochtaxana", "Poshtaxana", "Pochtaqana", "Poshtakana", "Pochtaxana"));
        quizList.add(new QuestionData(28, "Durıs jazılǵan sózdi tabıń", "Ellikqala", "Ellikala", "Ellıkqala", "Elliqala", "Ellikqala"));
        quizList.add(new QuestionData(29, "Durıs jazılǵan sózdi tabıń", "Qanlıkól", "Kanlıkól", "Qanlıkul", "Qanlikul", "Qanlıkól"));

        quizList.add(new QuestionData(30, "Durıs jazılǵan sózdi tabıń", "Qaraózek", "Qaraúzek", "Karaózek", "Qaraózyek", "Qaraózek"));
        quizList.add(new QuestionData(31, "Durıs jazılǵan sózdi tabıń", "Tájribeli", "Tajribeli", "Tájiriybeli", "Tajrybeli", "Tájiriybeli"));
        quizList.add(new QuestionData(32, "Durıs jazılǵan sózdi tabıń", "Másláhát", "Maslahat", "Máslaxat", "Másláqát", "Másláhát"));
        quizList.add(new QuestionData(33, "Durıs jazılǵan sózdi tabıń", "Ximiya", "Kimya", "Xımya", "Xımıya", "Ximiya"));
        quizList.add(new QuestionData(34, "Durıs jazılǵan sózdi tabıń", "Qánige", "Qániyge", "Qanige", "Qanyge", "Qánige"));
        quizList.add(new QuestionData(35, "Durıs jazılǵan sózdi tabıń", "Adebiyat", "Ádebiyat", "Ádebıyat", "Adebyat", "Ádebiyat"));
        quizList.add(new QuestionData(36, "Durıs jazılǵan sózdi tabıń", "Aruwxan", "Arıwxan", "Aruxan", "Aruwkan", "Arıwxan"));
        quizList.add(new QuestionData(37, "Durıs jazılǵan sózdi tabıń", "Xalqabad", "Qalqabat", "Xalıqabad", "Xalkabat", "Xalqabad"));
        quizList.add(new QuestionData(38, "Durıs jazılǵan sózdi tabıń", "Ǵaresiz", "Ǵaresız", "Ǵarezsiz", "Ǵárezsiz", "Ǵárezsiz"));
        quizList.add(new QuestionData(39, "Durıs jazılǵan sózdi tabıń", "Muhabbat", "Muxabbat", "Muhabat", "Muxabbet", "Muhabbat"));

        quizList.add(new QuestionData(40, "Durıs jazılǵan sózdi tabıń", "Wálayat", "Viloyat", "Wáláyat", "Vılayat", "Wálayat"));
        quizList.add(new QuestionData(41, "Durıs jazılǵan sózdi tabıń", "Takiyatas", "Taqıyatas", "Taxyatas", "Taqyatas", "Taqıyatas"));
        quizList.add(new QuestionData(42, "Durıs jazılǵan sózdi tabıń", "Heshqashan", "Esh qashan", "Hesh qashan", "Eshqashan", "Heshqashan"));
        quizList.add(new QuestionData(43, "Durıs jazılǵan sózdi tabıń", "Ámiwdárya", "Ámudárya", "Amuwdarya", "Amiwdarya", "Ámiwdárya"));
        quizList.add(new QuestionData(44, "Durıs jazılǵan sózdi tabıń", "Baliq", "Balıq", "Balık", "Balik", "Balıq"));
        quizList.add(new QuestionData(45, "Durıs jazılǵan sózdi tabıń", "Balalik", "Balaliq", "Balalıq", "Balalq", "Balalıq"));
        quizList.add(new QuestionData(46, "Durıs jazılǵan sózdi tabıń", "Diyxan", "Dıyxan", "Dyqan", "Diyqan", "Diyqan"));
        quizList.add(new QuestionData(47, "Durıs jazılǵan sózdi tabıń", "Xabarlandırıw", "Qabarlandırıw", "Qabarlandıru", "Xabarlandıruw", "Xabarlandırıw"));
        quizList.add(new QuestionData(48, "Durıs jazılǵan sózdi tabıń", "Bilimlendiriw", "Bilimlendıruw", "Bilimlendiruw", "Bilmlendriw", "Bilimlendiriw"));
        quizList.add(new QuestionData(49, "Durıs jazılǵan sózdi tabıń", "Jeńimpaz", "Jeńilpaz", "Jeńimbaz", "Jenimpaz", "Jeńimpaz"));

        quizList.add(new QuestionData(50, "Durıs jazılǵan sózdi tabıń", "Sáwbetlesiw", "Sawbetlesiu", "Sawbetlesuw", "Sawbetlesw", "Sáwbetlesiw"));
        quizList.add(new QuestionData(51, "Durıs jazılǵan sózdi tabıń", "Iymansız", "Íymansz", "Imansız", "Íymansız", "Iymansız"));
        quizList.add(new QuestionData(52, "Durıs jazılǵan sózdi tabıń", "Moynaq", "Moynak", "Muynak", "Muynaq", "Moynaq"));
        quizList.add(new QuestionData(53, "Durıs jazılǵan sózdi tabıń", "Tarix", "Tariyh", "Taryx", "Tariyx", "Tariyx"));
        quizList.add(new QuestionData(54, "Durıs jazılǵan sózdi tabıń", "Qıyınshılıq", "Qıynshılıq", "Qıyınshılık", "Qıyınshilik", "Qıyınshılıq"));
        quizList.add(new QuestionData(55, "Durıs jazılǵan sózdi tabıń", "Yernazar Alakóz", "Ernazar-Alakóz", "Yernazar Alakoz", "Ernazar Alakóz", "Ernazar Alakóz"));
        quizList.add(new QuestionData(56, "Durıs jazılǵan sózdi tabıń", "Quwat", "Kuwat", "Quwwat", "Kuat", "Quwat"));
        quizList.add(new QuestionData(57, "Durıs jazılǵan sózdi tabıń", "Materiallıq", "Matereallık", "Materiallık", "Materiyallıq", "Materiallıq"));
        quizList.add(new QuestionData(58, "Durıs jazılǵan sózdi tabıń", "Mehriban", "Mexriban", "Mehrıban", "Mexriyban", "Mehriban"));
        quizList.add(new QuestionData(59, "Durıs jazılǵan sózdi tabıń", "Miymanxana", "Mehmanxana", "Mymanxana", "Mymankana", "Miymanxana"));

        quizList.add(new QuestionData(60, "Durıs jazılǵan sózdi tabıń", "Biratala", "Birotala", "Birotola", "Birátala", "Birotala"));
        quizList.add(new QuestionData(61, "Durıs jazılǵan sózdi tabıń", "Amnistiya", "Amniciya", "Ammisiya", "Amnissiya", "Amnistiya"));
        quizList.add(new QuestionData(62, "Durıs jazılǵan sózdi tabıń", "Begimot", "Begemot", "Bigemot", "Begiymot", "Begemot"));
        quizList.add(new QuestionData(63, "Durıs jazılǵan sózdi tabıń", "Biyopa", "Beywopa", "Biywopa", "Biywapa", "Biyopa"));
        quizList.add(new QuestionData(64, "Durıs jazılǵan sózdi tabıń", "Byudjet", "Buydjet", "Buyjet", "Byudjed", "Byudjet"));
        quizList.add(new QuestionData(65, "Durıs jazılǵan sózdi tabıń", "Dárhal", "Darhal", "Dárxal", "Darxál", "Dárhal"));
        quizList.add(new QuestionData(66, "Durıs jazılǵan sózdi tabıń", "Decimetr", "Dicimetr", "Dicemetr", "Deciymetr", "Decimetr"));
        quizList.add(new QuestionData(67, "Durıs jazılǵan sózdi tabıń", "Aeroport", "Aeropord", "Airoport", "Ayraport", "Aeroport"));
        quizList.add(new QuestionData(68, "Durıs jazılǵan sózdi tabıń", "Klaslas", "Kılaslas", "Klaslass", "Klasslas", "Klaslas"));
        quizList.add(new QuestionData(69, "Durıs jazılǵan sózdi tabıń", "Sociallıq", "Sotsialliq", "Sociyalliq", "Sotsiyallik", "Sociallıq"));

        quizList.add(new QuestionData(70, "Durıs jazılǵan sózdi tabıń", "Qumırsqa", "Qumirsqa", "Qumrsqa", "Qumırısqa", "Qumırsqa"));
        quizList.add(new QuestionData(71, "Durıs jazılǵan sózdi tabıń", "Sálem", "Sálam", "Salam", "Salem", "Sálem"));
        quizList.add(new QuestionData(72, "Durıs jazılǵan sózdi tabıń", "Ájapa", "Ajapá", "Ajyapa", "Ajapa", "Ájapa"));
        quizList.add(new QuestionData(73, "Durıs jazılǵan sózdi tabıń", "Úyretetuǵın", "Uyretetn", "Úyretetugın", "Úyretetin", "Úyretetuǵın"));
        quizList.add(new QuestionData(74, "Durıs jazılǵan sózdi tabıń", "Húkimet", "Úkimet", "Húkmet", "Húkımet", "Húkimet"));
        quizList.add(new QuestionData(75, "Durıs jazılǵan sózdi tabıń", "Raxmet", "Ráxmet", "Rahmet", "Rahmed", "Raxmet"));
        quizList.add(new QuestionData(76, "Durıs jazılǵan sózdi tabıń", "Klaviatura", "Klavyatura", "Klaviyatura", "Kılaviyatura", "Klaviatura"));
        quizList.add(new QuestionData(77, "Durıs jazılǵan sózdi tabıń", "Basshı", "Bashshı", "Basshi", "Bashı", "Basshı"));
        quizList.add(new QuestionData(78, "Durıs jazılǵan sózdi tabıń", "Topıraq", "Topiraq", "Topırak", "Topraq", "Topıraq"));
        quizList.add(new QuestionData(79, "Durıs jazılǵan sózdi tabıń", "Avtovokzal", "Avtovakzal", "Avtovagzal", "Aftovokzal", "Avtovokzal"));

        quizList.add(new QuestionData(80, "Durıs jazılǵan sózdi tabıń", "Samolyot", "Samalyot", "Somolyot", "Samalyut", "Samolyot"));
        quizList.add(new QuestionData(81, "Durıs jazılǵan sózdi tabıń", "Vertolyot", "Virtolyot", "Vertalyot", "Vertalyut", "Vertolyot"));
        quizList.add(new QuestionData(82, "Durıs jazılǵan sózdi tabıń", "Okean", "Akean", "Okeyan", "Akeyan", "Okean"));
        quizList.add(new QuestionData(83, "Durıs jazılǵan sózdi tabıń", "Sulıwxan", "Suluwxan", "Sulıwqan", "Sulwxan", "Sulıwxan"));
        quizList.add(new QuestionData(84, "Durıs jazılǵan sózdi tabıń", "Milliard", "Milyard", "Millyart", "Milliart", "Milliard"));
        quizList.add(new QuestionData(85, "Durıs jazılǵan sózdi tabıń", "Teatr", "Tiatr", "Tyatr", "Teatır", "Teatr"));
        quizList.add(new QuestionData(86, "Durıs jazılǵan sózdi tabıń", "Jas áwlad", "Jas awlad", "Jas awlat", "Jas áulad", "Jas áwlad"));
        quizList.add(new QuestionData(87, "Durıs jazılǵan sózdi tabıń", "Kilogramm", "Kilogram", "Kilagramm", "Kiylagram", "Kilogramm"));
        quizList.add(new QuestionData(88, "Durıs jazılǵan sózdi tabıń", "Televizor", "Televizr", "Telezivor", "Televiyzr", "Televizor"));
        quizList.add(new QuestionData(89, "Durıs jazılǵan sózdi tabıń", "Tuwılǵan kún", "Tuılǵan kun", "Tuwǵan kún", "Tuwılǵan kun", "Tuwılǵan kún"));

        quizList.add(new QuestionData(90, "Durıs jazılǵan sózdi tabıń", "Chempionat", "Chempiyonat", "Shempionat", "Chempyonat", "Chempionat"));
        quizList.add(new QuestionData(91, "Durıs jazılǵan sózdi tabıń", "Kegeyli", "Kegeylı", "Kigeyli", "Kegiyli", "Kegeyli"));
        quizList.add(new QuestionData(92, "Durıs jazılǵan sózdi tabıń", "Teorema", "Tiyorema", "Tearema", "Tiyarema", "Teorema"));
        quizList.add(new QuestionData(93, "Durıs jazılǵan sózdi tabıń", "Qaharman", "Qahraman", "Qáharman", "Qaqarman", "Qaharman"));
        quizList.add(new QuestionData(94, "Durıs jazılǵan sózdi tabıń", "Hákimiyat", "Hákimyat", "Hakimyat", "Hákimiat", "Hákimiyat"));
        quizList.add(new QuestionData(95, "Durıs jazılǵan sózdi tabıń", "Imkaniyat", "Ímkaniyat", "Ímkanyat", "Imkanyat", "Imkaniyat"));
        quizList.add(new QuestionData(96, "Durıs jazılǵan sózdi tabıń", "Járdemlesiw", "Jardemlesiw", "Járdemlesúw", "Jardemlesuw", "Járdemlesiw"));
        quizList.add(new QuestionData(97, "Durıs jazılǵan sózdi tabıń", "Toǵızınshı", "Toqqızınshı", "Toǵızınchı", "Togızınshı", "Toǵızınshı"));
        quizList.add(new QuestionData(98, "Durıs jazılǵan sózdi tabıń", "Medicina", "Medecina", "Meditsina", "Medetsina", "Medicina"));
        quizList.add(new QuestionData(99, "Durıs jazılǵan sózdi tabıń", "Mashina", "Mashiyna", "Mashın", "Machina", "Mashina"));

        quizList.add(new QuestionData(100, "Durıs jazılǵan sózdi tabıń", "Xalqabad", "Qalqabat", "Xalıqabad", "Xalkabat", "Xalqabad"));
        quizList.add(new QuestionData(101, "Durıs jazılǵan sózdi tabıń", "Miymanxana", "Mehmanxana", "Mymanxana", "Mymankana", "Miymanxana"));
        quizList.add(new QuestionData(102, "Durıs jazılǵan sózdi tabıń", "Biyopa", "Beywopa", "Biywopa", "Biywapa", "Biyopa"));
        quizList.add(new QuestionData(103, "Durıs jazılǵan sózdi tabıń", "Quanish", "Quwanısh", "Quanısh", "Quansh", "Quwanısh"));
        quizList.add(new QuestionData(104, "Durıs jazılǵan sózdi tabıń", "Másláhát", "Maslahat", "Máslaxat", "Másláqát", "Másláhát"));
        quizList.add(new QuestionData(105, "Durıs jazılǵan sózdi tabıń", "Awa", "Aua", "xxx", "xxx", "Awa"));
        quizList.add(new QuestionData(106, "Durıs jazılǵan sózdi tabıń", "Wálayat", "Viloyat", "Wáláyat", "Vılayat", "Wálayat"));
        quizList.add(new QuestionData(107, "Durıs jazılǵan sózdi tabıń", "Kilogramm", "Kilogram", "Kilagramm", "Kiylagram", "Kilogramm"));
        quizList.add(new QuestionData(108, "Durıs jazılǵan sózdi tabıń", "Telefon", "Telifon", "Telepon", "Tilifon", "Telefon"));
        quizList.add(new QuestionData(109, "Durıs jazılǵan sózdi tabıń", "Shımshıq", "Shımchıq", "Shımshık", "Chımshıq", "Shımshıq"));

        quizList.add(new QuestionData(110, "Durıs jazılǵan sózdi tabıń", "Multfilm", "Multfilim", "Murtpilm", "Multfiylim", "Multfilm"));
        quizList.add(new QuestionData(111, "Durıs jazılǵan sózdi tabıń", "Maqset", "Maxset", "Makset", "Maksed", "Maqset"));
        quizList.add(new QuestionData(112, "Durıs jazılǵan sózdi tabıń", "Respublikalıq", "Resbuplikalıq", "Respublikalik", "Respubliqalıq", "Respublikalıq"));
        quizList.add(new QuestionData(113, "Durıs jazılǵan sózdi tabıń", "Tańlaw", "Tanlaw", "Tańlauw", "Tanlau", "Tańlaw"));
        quizList.add(new QuestionData(114, "Durıs jazılǵan sózdi tabıń", "Prezident", "Preziydent", "Preziydend", "Prizident", "Prezident"));
        quizList.add(new QuestionData(115, "Durıs jazılǵan sózdi tabıń", "Filial", "Filiyal", "Fillial", "Filyal", "Filial"));
        quizList.add(new QuestionData(116, "Durıs jazılǵan sózdi tabıń", "Sáwbet", "Sawbet", "Sáubet", "Saúbet", "Sáwbet"));
        quizList.add(new QuestionData(117, "Durıs jazılǵan sózdi tabıń", "Emlewxana", "Emleuxana", "Yemlewxana", "Yemleuxana", "Emlewxana"));
        quizList.add(new QuestionData(118, "Durıs jazılǵan sózdi tabıń", "Dialog", "Dialok", "Diyalog", "Dıyalok", "Dialog"));
        quizList.add(new QuestionData(119, "Durıs jazılǵan sózdi tabıń", "Xabar", "Kabar", "Qabar", "xxx", "Xabar"));

        quizList.add(new QuestionData(120, "Durıs jazılǵan sózdi tabıń", "Támiyinlew", "Támiynlew", "Táminlew", "Tamynlew", "Támiyinlew"));
        quizList.add(new QuestionData(121, "Durıs jazılǵan sózdi tabıń", "Tigiwshi", "Tiginshi", "Tikinshi", "Tigúwshi", "Tigiwshi"));
        quizList.add(new QuestionData(122, "Durıs jazılǵan sózdi tabıń", "Heshkim", "Hesh kim", "Eshkim", "Eshgim", "Heshkim"));
        quizList.add(new QuestionData(123, "Durıs jazılǵan sózdi tabıń", "Densawlıq", "Den-sawlıq", "Den sawlıq", "Densawlık", "Densawlıq"));
        quizList.add(new QuestionData(124, "Durıs jazılǵan sózdi tabıń", "Ministrlik", "Ministirlik", "Ministrlıq", "Ministerlik", "Ministrlik"));
        quizList.add(new QuestionData(125, "Durıs jazılǵan sózdi tabıń", "Shańaraq", "Shanaraq", "Shańarak", "Semya", "Shańaraq"));
        quizList.add(new QuestionData(126, "Durıs jazılǵan sózdi tabıń", "Awıl", "Awil", "Auıl", "Awl", "Awıl"));
        quizList.add(new QuestionData(127, "Durıs jazılǵan sózdi tabıń", "Xalıqaralıq", "Xalqaralıq", "Xalqaralık", "Qalıqaralıq", "Xalıqaralıq"));
        quizList.add(new QuestionData(128, "Durıs jazılǵan sózdi tabıń", "Menińshe", "Menimshe", "Meninshe", "xxx", "Menińshe"));
        quizList.add(new QuestionData(129, "Durıs jazılǵan sózdi tabıń", "Haqqında", "Haqında", "Haqıda", "Hakkında", "Haqqında"));

        quizList.add(new QuestionData(130, "Durıs jazılǵan sózdi tabıń", "Biologiya", "Biyologiya", "Biologia", "Biologya", "Biologiya"));
        quizList.add(new QuestionData(131, "Durıs jazılǵan sózdi tabıń", "Turist", "Tuwrist", "Turis", "Tourist", "Turist"));
        quizList.add(new QuestionData(132, "Durıs jazılǵan sózdi tabıń", "Geografiya", "Giyografiya", "Geografya", "Geyografya", "Geografiya"));
        quizList.add(new QuestionData(133, "Durıs jazılǵan sózdi tabıń", "Awızeki", "Awız eki", "Awızyeki", "Awzeki", "Awızeki"));
        quizList.add(new QuestionData(134, "Durıs jazılǵan sózdi tabıń", "Prokuratura", "Prokratura", "Prakratuwra", "Prokuratuwra", "Prokuratura"));
        quizList.add(new QuestionData(135, "Durıs jazılǵan sózdi tabıń", "DÁRIXANA", "DÁRÍXANA", "DÁRXANA", "APTEKA", "DÁRIXANA"));
        quizList.add(new QuestionData(136, "Durıs jazılǵan sózdi tabıń", "Leytenant", "Letenant", "Litiynat", "Litenat", "Leytenant"));
        quizList.add(new QuestionData(137, "Durıs jazılǵan sózdi tabıń", "Qońıraw", "Qońraw", "Qonıraw", "Zvonok", "Qońıraw"));
        quizList.add(new QuestionData(138, "Durıs jazılǵan sózdi tabıń", "Dushpan", "Dushman", "Dushban", "xxx", "Dushpan"));
        quizList.add(new QuestionData(139, "Durıs jazılǵan sózdi tabıń", "Múnásibet", "Múnasibet", "Múnasiybet", "Múnásebet", "Múnásibet"));

        quizList.add(new QuestionData(140, "Durıs jazılǵan sózdi tabıń", "Dumalaq", "Domalak", "Domalaq", "Duwmalaq", "Dumalaq"));
        quizList.add(new QuestionData(141, "Durıs jazılǵan sózdi tabıń", "Nawrız", "Navruz", "Naurız", "Nawriz", "Nawrız"));
        quizList.add(new QuestionData(142, "Durıs jazılǵan sózdi tabıń", "Kirpitiken", "Qirpitken", "Qirptken", "Qirptiken", "Kirpitiken"));
        quizList.add(new QuestionData(143, "Durıs jazılǵan sózdi tabıń", "Velosiyped", "Velosipet", "Belsebet", "Velosiped", "Velosiped"));
        quizList.add(new QuestionData(144, "Durıs jazılǵan sózdi tabıń", "Dúyshembi", "Dúshembi", "Dushanba", "Понедельник", "Dúyshembi"));
        quizList.add(new QuestionData(145, "Durıs jazılǵan sózdi tabıń", "Sárshembi", "Sarshembı", "Sarshenbi", "Cреда", "Sárshembi"));
        quizList.add(new QuestionData(146, "Durıs jazılǵan sózdi tabıń", "Piyshembi", "Pishembi", "Payshanba", "Четверг", "Piyshembi"));
        quizList.add(new QuestionData(147, "Durıs jazılǵan sózdi tabıń", "Shembi", "Shenbi", "Shanba", "Суббота", "Shembi"));
        quizList.add(new QuestionData(148, "Durıs jazılǵan sózdi tabıń", "Ekshembi", "Yekshembi", "Yakshanba", "Воскресенье", "Ekshembi"));
        quizList.add(new QuestionData(149, "Durıs jazılǵan sózdi tabıń", "Dúkán", "Túkán", "Dukan", "Магазин", "Dúkán"));
    }
}

/*
    Map<String, Object> mainMap = new HashMap<>();
    Map<String, Object> map = new HashMap<>();


        //        Collections.shuffle(data);


        for (int i = 0; i < data.size(); i++) {
            map.put("id", data.get(i).getId());
            map.put("question", data.get(i).getQuestion());
            map.put("variant1", data.get(i).getVariant1());
            map.put("variant2", data.get(i).getVariant2());
            map.put("variant3", data.get(i).getVariant3());
            map.put("variant4", data.get(i).getVariant4());
            map.put("answer", data.get(i).getAnswer());


            db.collection("QuizList").document(String.valueOf(i)).set(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.v("TTTT", "success!");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.v("TTTT", e.getMessage());
                }
            });

        }
    }
*/