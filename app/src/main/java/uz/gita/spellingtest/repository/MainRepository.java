package uz.gita.spellingtest.repository;

import androidx.annotation.Keep;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uz.gita.spellingtest.app.LocalStorage;
import uz.gita.spellingtest.contract.FlagContract;
import uz.gita.spellingtest.data.QuestionData;

@Keep
public class MainRepository implements FlagContract.Model {
    private static final ArrayList<QuestionData> quizList = new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final LocalStorage localStorage;
    List<QuestionData> levelQuestions;

    public MainRepository() {
        localStorage = LocalStorage.getInstance();
        levelQuestions = new ArrayList<>();
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

    String response = "Error";

    @Override
    public void submitTest(QuestionData questionData, WriteDataFinishListener writeDataFinishListener) {
        Map<String, Object> map = new HashMap<>();

        map.put("id", questionData.getId());
        map.put("isValid", questionData.getIsValid());
        map.put("question", questionData.getQuestion());
        map.put("variant1", questionData.getVariant1());
        map.put("variant2", questionData.getVariant2());
        map.put("variant3", questionData.getVariant3());
        map.put("variant4", questionData.getVariant4());
        map.put("answer", questionData.getAnswer());

        db.collection("QuizListNew").document().set(map)
                .addOnSuccessListener(unused -> {
                    response = "Test tekseriwge jiberildi";
                    if (writeDataFinishListener != null)
                        writeDataFinishListener.submitTestFinishedListener();
                }).addOnFailureListener(e -> {
            response = e.getMessage();
            if (writeDataFinishListener != null)
                writeDataFinishListener.submitTestFinishedListener();
        });
    }

    @Override
    public void getDataFromFirebase(final ReadDataFinishListener listener) {
        db.collection("QuizListNew")
                .whereEqualTo("isValid", true).orderBy("id", Query.Direction.ASCENDING)
                .get().addOnCompleteListener(task -> {
            if (!quizList.isEmpty()) quizList.clear();
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    quizList.add(document.toObject(QuestionData.class));
                }
            }

            if (quizList.isEmpty()) getLocalData();
            if (listener != null) listener.getDataFinishedListener();
        }).addOnFailureListener(e -> {});
    }

    @Override
    public void cancelGetDataRequest() {

    }

    public void getLocalData() {
        String question = "Durıs jazılǵan sózdi tabıń";
        quizList.add(new QuestionData(0, true, question, "Konstitutciya", "Konstituciya", "Konstitutsiya", "Konstutsiya", "Konstituciya"));
        quizList.add(new QuestionData(1, true, question, "Kodjeli", "Xójeli", "Xojeli", "Kudjeli", "Xojeli"));
        quizList.add(new QuestionData(2, true, question, "Nukus", "Nókis", "Nokis", "Nuks", "Nókis"));
        quizList.add(new QuestionData(3, true, question, "Koncert", "Konsert", "Kansert", "Kontsert", "Koncert"));
        quizList.add(new QuestionData(4, true, question, "Qaraqalpaqstan", "Karakalpakstan", "Qaraqalpaqıstan", "Qaraqalpaǵıstan", "Qaraqalpaqstan"));
        quizList.add(new QuestionData(5, true, question, "Saat", "Saǵad", "Sagat", "Saǵat", "Saat"));
        quizList.add(new QuestionData(6, true, question, "Institut", "Insitut", "Instut", "Ínstitut", "Institut"));
        quizList.add(new QuestionData(7, true, question, "Quanish", "Quwanısh", "Quanısh", "Quansh", "Quwanısh"));
        quizList.add(new QuestionData(8, true, question, "Ínformatsiya", "Informaciya", "Ínformaciya", "Informatsya", "Informaciya"));
        quizList.add(new QuestionData(9, true, question, "Qońırat", "Kungrad", "Końırat", "Qońrat", "Qońırat"));

        quizList.add(new QuestionData(10, true, question, "Ozbekstan", "Ózbekstan", "Uzbekistan", "Ózbekiston", "Ózbekstan"));
        quizList.add(new QuestionData(11, true, question, "Aqilli", "Aqıllı", "Aqıli", "Aqılli", "Aqıllı"));
        quizList.add(new QuestionData(12, true, question, "Assalawma áleykum", "Assalawma áleykım", "Assalawmu aleykum", "Asalawma aleykum", "Assalawma áleykum"));
        quizList.add(new QuestionData(13, true, question, "Oktyabr", "Oktyabir", "Oktabr", "Aktyabr", "Oktyabr"));
        quizList.add(new QuestionData(14, true, question, "Taqtakopir", "Taxtakópir", "Taxtakopir", "Taktakopir", "Taxtakópir"));
        quizList.add(new QuestionData(15, true, question, "Kolledj", "Kallej", "Kollej", "Koledj", "Kolledj"));
        quizList.add(new QuestionData(16, true, question, "Bagban", "Baǵman", "Baǵpan", "Bagpan", "Baǵman"));
        quizList.add(new QuestionData(17, true, question, "Sauaplı", "Sawablı", "Sawaplı", "Sauapli", "Sawaplı"));
        quizList.add(new QuestionData(18, true, question, "Aǵayinshilik", "Aǵaynshiliq", "Agayinshilik", "Agaynshilik", "Aǵayinshilik"));
        quizList.add(new QuestionData(19, true, question, "Operaciya", "Operacıya", "Aperatsiya", "Operatsiya", "Operaciya"));

        quizList.add(new QuestionData(20, true, question, "Madeniyat", "Mádenyat", "Madenyad", "Mádeniyat", "Mádeniyat"));
        quizList.add(new QuestionData(21, true, question, "Oqıwshılar", "Okıushılar", "Oqushılar", "Oquwshılar", "Oqıwshılar"));
        quizList.add(new QuestionData(22, true, question, "Rawajlanıw", "Rauajlanuw", "Rauajlanıw", "Rauajlanu", "Rawajlanıw"));
        quizList.add(new QuestionData(23, true, question, "Qorqıtıw", "Qorqıtw", "Korkıtuw", "Qorkıtuw", "Qorqıtıw"));
        quizList.add(new QuestionData(24, true, question, "Pitkeriwshi", "Pitkerúwshi", "Pıtkeriwshi", "Pitkerushi", "Pitkeriwshi"));
        quizList.add(new QuestionData(25, true, question, "Shomanay", "Shumanay", "Shuwmanay", "Chumanay", "Shomanay"));
        quizList.add(new QuestionData(26, true, question, "Lampochka", "Lampuchka", "Lampshkı", "Lamposhka", "Lampochka"));
        quizList.add(new QuestionData(27, true, question, "Pochtaxana", "Poshtaxana", "Pochtaqana", "Poshtakana", "Pochtaxana"));
        quizList.add(new QuestionData(28, true, question, "Ellikqala", "Ellikala", "Ellıkqala", "Elliqala", "Ellikqala"));
        quizList.add(new QuestionData(29, true, question, "Qanlıkól", "Kanlıkól", "Qanlıkul", "Qanlikul", "Qanlıkól"));

        quizList.add(new QuestionData(30, true, question, "Qaraózek", "Qaraúzek", "Karaózek", "Qaraózyek", "Qaraózek"));
        quizList.add(new QuestionData(31, true, question, "Tájribeli", "Tajribeli", "Tájiriybeli", "Tajrybeli", "Tájiriybeli"));
        quizList.add(new QuestionData(32, true, question, "Másláhát", "Maslahat", "Máslaxat", "Másláqát", "Másláhát"));
        quizList.add(new QuestionData(33, true, question, "Ximiya", "Kimya", "Xımya", "Xımıya", "Ximiya"));
        quizList.add(new QuestionData(34, true, question, "Qánige", "Qániyge", "Qanige", "Qanyge", "Qánige"));
        quizList.add(new QuestionData(35, true, question, "Adebiyat", "Ádebiyat", "Ádebıyat", "Adebyat", "Ádebiyat"));
        quizList.add(new QuestionData(36, true, question, "Aruwxan", "Arıwxan", "Aruxan", "Aruwkan", "Arıwxan"));
        quizList.add(new QuestionData(37, true, question, "Xalqabad", "Qalqabat", "Xalıqabad", "Xalkabat", "Xalqabad"));
        quizList.add(new QuestionData(38, true, question, "Ǵaresiz", "Ǵaresız", "Ǵarezsiz", "Ǵárezsiz", "Ǵárezsiz"));
        quizList.add(new QuestionData(39, true, question, "Muhabbat", "Muxabbat", "Muhabat", "Muxabbet", "Muhabbat"));

        quizList.add(new QuestionData(40, true, question, "Wálayat", "Viloyat", "Wáláyat", "Vılayat", "Wálayat"));
        quizList.add(new QuestionData(41, true, question, "Takiyatas", "Taqıyatas", "Taxyatas", "Taqyatas", "Taqıyatas"));
        quizList.add(new QuestionData(42, true, question, "Heshqashan", "Esh qashan", "Hesh qashan", "Eshqashan", "Heshqashan"));
        quizList.add(new QuestionData(43, true, question, "Ámiwdárya", "Ámudárya", "Amuwdarya", "Amiwdarya", "Ámiwdárya"));
        quizList.add(new QuestionData(44, true, question, "Baliq", "Balıq", "Balık", "Balik", "Balıq"));
        quizList.add(new QuestionData(45, true, question, "Balalik", "Balaliq", "Balalıq", "Balalq", "Balalıq"));
        quizList.add(new QuestionData(46, true, question, "Diyxan", "Dıyxan", "Dyqan", "Diyqan", "Diyqan"));
        quizList.add(new QuestionData(47, true, question, "Xabarlandırıw", "Qabarlandırıw", "Qabarlandıru", "Xabarlandıruw", "Xabarlandırıw"));
        quizList.add(new QuestionData(48, true, question, "Bilimlendiriw", "Bilimlendıruw", "Bilimlendiruw", "Bilmlendriw", "Bilimlendiriw"));
        quizList.add(new QuestionData(49, true, question, "Jeńimpaz", "Jeńilpaz", "Jeńimbaz", "Jenimpaz", "Jeńimpaz"));

        quizList.add(new QuestionData(50, true, question, "Sáwbetlesiw", "Sawbetlesiu", "Sawbetlesuw", "Sawbetlesw", "Sáwbetlesiw"));
        quizList.add(new QuestionData(51, true, question, "Iymansız", "Íymansz", "Imansız", "Íymansız", "Iymansız"));
        quizList.add(new QuestionData(52, true, question, "Moynaq", "Moynak", "Muynak", "Muynaq", "Moynaq"));
        quizList.add(new QuestionData(53, true, question, "Tarix", "Tariyh", "Taryx", "Tariyx", "Tariyx"));
        quizList.add(new QuestionData(54, true, question, "Qıyınshılıq", "Qıynshılıq", "Qıyınshılık", "Qıyınshilik", "Qıyınshılıq"));
        quizList.add(new QuestionData(55, true, question, "Yernazar Alakóz", "Ernazar-Alakóz", "Yernazar Alakoz", "Ernazar Alakóz", "Ernazar Alakóz"));
        quizList.add(new QuestionData(56, true, question, "Quwat", "Kuwat", "Quwwat", "Kuat", "Quwat"));
        quizList.add(new QuestionData(57, true, question, "Materiallıq", "Matereallık", "Materiallık", "Materiyallıq", "Materiallıq"));
        quizList.add(new QuestionData(58, true, question, "Mehriban", "Mexriban", "Mehrıban", "Mexriyban", "Mehriban"));
        quizList.add(new QuestionData(59, true, question, "Miymanxana", "Mehmanxana", "Mymanxana", "Mymankana", "Miymanxana"));

        quizList.add(new QuestionData(60, true, question, "Biratala", "Birotala", "Birotola", "Birátala", "Birotala"));
        quizList.add(new QuestionData(61, true, question, "Amnistiya", "Amniciya", "Ammisiya", "Amnissiya", "Amnistiya"));
        quizList.add(new QuestionData(62, true, question, "Begimot", "Begemot", "Bigemot", "Begiymot", "Begemot"));
        quizList.add(new QuestionData(63, true, question, "Biyopa", "Beywopa", "Biywopa", "Biywapa", "Biyopa"));
        quizList.add(new QuestionData(64, true, question, "Byudjet", "Buydjet", "Buyjet", "Byudjed", "Byudjet"));
        quizList.add(new QuestionData(65, true, question, "Dárhal", "Darhal", "Dárxal", "Darxál", "Dárhal"));
        quizList.add(new QuestionData(66, true, question, "Decimetr", "Dicimetr", "Dicemetr", "Deciymetr", "Decimetr"));
        quizList.add(new QuestionData(67, true, question, "Aeroport", "Aeropord", "Airoport", "Ayraport", "Aeroport"));
        quizList.add(new QuestionData(68, true, question, "Klaslas", "Kılaslas", "Klaslass", "Klasslas", "Klaslas"));
        quizList.add(new QuestionData(69, true, question, "Sociallıq", "Sotsialliq", "Sociyalliq", "Sotsiyallik", "Sociallıq"));

        quizList.add(new QuestionData(70, true, question, "Qumırsqa", "Qumirsqa", "Qumrsqa", "Qumırısqa", "Qumırsqa"));
        quizList.add(new QuestionData(71, true, question, "Sálem", "Sálam", "Salam", "Salem", "Sálem"));
        quizList.add(new QuestionData(72, true, question, "Ájapa", "Ajapá", "Ajyapa", "Ajapa", "Ájapa"));
        quizList.add(new QuestionData(73, true, question, "Úyretetuǵın", "Uyretetn", "Úyretetugın", "Úyretetin", "Úyretetuǵın"));
        quizList.add(new QuestionData(74, true, question, "Húkimet", "Úkimet", "Húkmet", "Húkımet", "Húkimet"));
        quizList.add(new QuestionData(75, true, question, "Raxmet", "Ráxmet", "Rahmet", "Rahmed", "Raxmet"));
        quizList.add(new QuestionData(76, true, question, "Klaviatura", "Klavyatura", "Klaviyatura", "Kılaviyatura", "Klaviatura"));
        quizList.add(new QuestionData(77, true, question, "Basshı", "Bashshı", "Basshi", "Bashı", "Basshı"));
        quizList.add(new QuestionData(78, true, question, "Topıraq", "Topiraq", "Topırak", "Topraq", "Topıraq"));
        quizList.add(new QuestionData(79, true, question, "Avtovokzal", "Avtovakzal", "Avtovagzal", "Aftovokzal", "Avtovokzal"));

        quizList.add(new QuestionData(80, true, question, "Samolyot", "Samalyot", "Somolyot", "Samalyut", "Samolyot"));
        quizList.add(new QuestionData(81, true, question, "Vertolyot", "Virtolyot", "Vertalyot", "Vertalyut", "Vertolyot"));
        quizList.add(new QuestionData(82, true, question, "Okean", "Akean", "Okeyan", "Akeyan", "Okean"));
        quizList.add(new QuestionData(83, true, question, "Sulıwxan", "Suluwxan", "Sulıwqan", "Sulwxan", "Sulıwxan"));
        quizList.add(new QuestionData(84, true, question, "Milliard", "Milyard", "Millyart", "Milliart", "Milliard"));
        quizList.add(new QuestionData(85, true, question, "Teatr", "Tiatr", "Tyatr", "Teatır", "Teatr"));
        quizList.add(new QuestionData(86, true, question, "Jas áwlad", "Jas awlad", "Jas awlat", "Jas áulad", "Jas áwlad"));
        quizList.add(new QuestionData(87, true, question, "Kilogramm", "Kilogram", "Kilagramm", "Kiylagram", "Kilogramm"));
        quizList.add(new QuestionData(88, true, question, "Televizor", "Televizr", "Telezivor", "Televiyzr", "Televizor"));
        quizList.add(new QuestionData(89, true, question, "Tuwılǵan kún", "Tuılǵan kun", "Tuwǵan kún", "Tuwılǵan kun", "Tuwılǵan kún"));

        quizList.add(new QuestionData(90, true, question, "Chempionat", "Chempiyonat", "Shempionat", "Chempyonat", "Chempionat"));
        quizList.add(new QuestionData(91, true, question, "Kegeyli", "Kegeylı", "Kigeyli", "Kegiyli", "Kegeyli"));
        quizList.add(new QuestionData(92, true, question, "Teorema", "Tiyorema", "Tearema", "Tiyarema", "Teorema"));
        quizList.add(new QuestionData(93, true, question, "Qaharman", "Qahraman", "Qáharman", "Qaqarman", "Qaharman"));
        quizList.add(new QuestionData(94, true, question, "Hákimiyat", "Hákimyat", "Hakimyat", "Hákimiat", "Hákimiyat"));
        quizList.add(new QuestionData(95, true, question, "Imkaniyat", "Ímkaniyat", "Ímkanyat", "Imkanyat", "Imkaniyat"));
        quizList.add(new QuestionData(96, true, question, "Járdemlesiw", "Jardemlesiw", "Járdemlesúw", "Jardemlesuw", "Járdemlesiw"));
        quizList.add(new QuestionData(97, true, question, "Toǵızınshı", "Toqqızınshı", "Toǵızınchı", "Togızınshı", "Toǵızınshı"));
        quizList.add(new QuestionData(98, true, question, "Medicina", "Medecina", "Meditsina", "Medetsina", "Medicina"));
        quizList.add(new QuestionData(99, true, question, "Mashina", "Mashiyna", "Mashın", "Machina", "Mashina"));

        quizList.add(new QuestionData(100, true, question, "Xalqabad", "Qalqabat", "Xalıqabad", "Xalkabat", "Xalqabad"));
        quizList.add(new QuestionData(101, true, question, "Miymanxana", "Mehmanxana", "Mymanxana", "Mymankana", "Miymanxana"));
        quizList.add(new QuestionData(102, true, question, "Biyopa", "Beywopa", "Biywopa", "Biywapa", "Biyopa"));
        quizList.add(new QuestionData(103, true, question, "Quanish", "Quwanısh", "Quanısh", "Quansh", "Quwanısh"));
        quizList.add(new QuestionData(104, true, question, "Másláhát", "Maslahat", "Máslaxat", "Másláqát", "Másláhát"));
        quizList.add(new QuestionData(105, true, question, "Awa", "Aua", "xxx", "xxx", "Awa"));
        quizList.add(new QuestionData(106, true, question, "Wálayat", "Viloyat", "Wáláyat", "Vılayat", "Wálayat"));
        quizList.add(new QuestionData(107, true, question, "Kilogramm", "Kilogram", "Kilagramm", "Kiylagram", "Kilogramm"));
        quizList.add(new QuestionData(108, true, question, "Telefon", "Telifon", "Telepon", "Tilifon", "Telefon"));
        quizList.add(new QuestionData(109, true, question, "Shımshıq", "Shımchıq", "Shımshık", "Chımshıq", "Shımshıq"));

        quizList.add(new QuestionData(110, true, question, "Multfilm", "Multfilim", "Murtpilm", "Multfiylim", "Multfilm"));
        quizList.add(new QuestionData(111, true, question, "Maqset", "Maxset", "Makset", "Maksed", "Maqset"));
        quizList.add(new QuestionData(112, true, question, "Respublikalıq", "Resbuplikalıq", "Respublikalik", "Respubliqalıq", "Respublikalıq"));
        quizList.add(new QuestionData(113, true, question, "Tańlaw", "Tanlaw", "Tańlauw", "Tanlau", "Tańlaw"));
        quizList.add(new QuestionData(114, true, question, "Prezident", "Preziydent", "Preziydend", "Prizident", "Prezident"));
        quizList.add(new QuestionData(115, true, question, "Filial", "Filiyal", "Fillial", "Filyal", "Filial"));
        quizList.add(new QuestionData(116, true, question, "Sáwbet", "Sawbet", "Sáubet", "Saúbet", "Sáwbet"));
        quizList.add(new QuestionData(117, true, question, "Emlewxana", "Emleuxana", "Yemlewxana", "Yemleuxana", "Emlewxana"));
        quizList.add(new QuestionData(118, true, question, "Dialog", "Dialok", "Diyalog", "Dıyalok", "Dialog"));
        quizList.add(new QuestionData(119, true, question, "Xabar", "Kabar", "Qabar", "xxx", "Xabar"));

        quizList.add(new QuestionData(120, true, "Durıs jazılǵan sózdi tabıń", "Támiyinlew", "Támiynlew", "Táminlew", "Tamynlew", "Támiyinlew"));
        quizList.add(new QuestionData(121, true, "Durıs jazılǵan sózdi tabıń", "Tigiwshi", "Tiginshi", "Tikinshi", "Tigúwshi", "Tigiwshi"));
        quizList.add(new QuestionData(122, true, "Durıs jazılǵan sózdi tabıń", "Heshkim", "Hesh kim", "Eshkim", "Eshgim", "Heshkim"));
        quizList.add(new QuestionData(123, true, "Durıs jazılǵan sózdi tabıń", "Densawlıq", "Den-sawlıq", "Den sawlıq", "Densawlık", "Densawlıq"));
        quizList.add(new QuestionData(124, true, "Durıs jazılǵan sózdi tabıń", "Ministrlik", "Ministirlik", "Ministrlıq", "Ministerlik", "Ministrlik"));
        quizList.add(new QuestionData(125, true, "Durıs jazılǵan sózdi tabıń", "Shańaraq", "Shanaraq", "Shańarak", "Semya", "Shańaraq"));
        quizList.add(new QuestionData(126, true, "Durıs jazılǵan sózdi tabıń", "Awıl", "Awil", "Auıl", "Awl", "Awıl"));
        quizList.add(new QuestionData(127, true, "Durıs jazılǵan sózdi tabıń", "Xalıqaralıq", "Xalqaralıq", "Xalqaralık", "Qalıqaralıq", "Xalıqaralıq"));
        quizList.add(new QuestionData(128, true, "Durıs jazılǵan sózdi tabıń", "Menińshe", "Menimshe", "Meninshe", "xxx", "Menińshe"));
        quizList.add(new QuestionData(129, true, "Durıs jazılǵan sózdi tabıń", "Haqqında", "Haqında", "Haqıda", "Hakkında", "Haqqında"));

        quizList.add(new QuestionData(130, true, "Durıs jazılǵan sózdi tabıń", "Biologiya", "Biyologiya", "Biologia", "Biologya", "Biologiya"));
        quizList.add(new QuestionData(131, true, "Durıs jazılǵan sózdi tabıń", "Turist", "Tuwrist", "Turis", "Tourist", "Turist"));
        quizList.add(new QuestionData(132, true, "Durıs jazılǵan sózdi tabıń", "Geografiya", "Giyografiya", "Geografya", "Geyografya", "Geografiya"));
        quizList.add(new QuestionData(133, true, "Durıs jazılǵan sózdi tabıń", "Awızeki", "Awız eki", "Awızyeki", "Awzeki", "Awızeki"));
        quizList.add(new QuestionData(134, true, "Durıs jazılǵan sózdi tabıń", "Prokuratura", "Prokratura", "Prakratuwra", "Prokuratuwra", "Prokuratura"));
        quizList.add(new QuestionData(135, true, "Durıs jazılǵan sózdi tabıń", "DÁRIXANA", "DÁRÍXANA", "DÁRXANA", "APTEKA", "DÁRIXANA"));
        quizList.add(new QuestionData(136, true, "Durıs jazılǵan sózdi tabıń", "Leytenant", "Letenant", "Litiynat", "Litenat", "Leytenant"));
        quizList.add(new QuestionData(137, true, "Durıs jazılǵan sózdi tabıń", "Qońıraw", "Qońraw", "Qonıraw", "Zvonok", "Qońıraw"));
        quizList.add(new QuestionData(138, true, "Durıs jazılǵan sózdi tabıń", "Dushpan", "Dushman", "Dushban", "xxx", "Dushpan"));
        quizList.add(new QuestionData(139, true, "Durıs jazılǵan sózdi tabıń", "Múnásibet", "Múnasibet", "Múnasiybet", "Múnásebet", "Múnásibet"));

        quizList.add(new QuestionData(140, true, "Durıs jazılǵan sózdi tabıń", "Dumalaq", "Domalak", "Domalaq", "Duwmalaq", "Dumalaq"));
        quizList.add(new QuestionData(141, true, "Durıs jazılǵan sózdi tabıń", "Nawrız", "Navruz", "Naurız", "Nawriz", "Nawrız"));
        quizList.add(new QuestionData(142, true, "Durıs jazılǵan sózdi tabıń", "Kirpitiken", "Qirpitken", "Qirptken", "Qirptiken", "Kirpitiken"));
        quizList.add(new QuestionData(143, true, "Durıs jazılǵan sózdi tabıń", "Velosiyped", "Velosipet", "Belsebet", "Velosiped", "Velosiped"));
        quizList.add(new QuestionData(144, true, "Durıs jazılǵan sózdi tabıń", "Dúyshembi", "Dúshembi", "Dushanba", "Понедельник", "Dúyshembi"));
        quizList.add(new QuestionData(145, true, "Durıs jazılǵan sózdi tabıń", "Sárshembi", "Sarshembı", "Sarshenbi", "Cреда", "Sárshembi"));
        quizList.add(new QuestionData(146, true, "Durıs jazılǵan sózdi tabıń", "Piyshembi", "Pishembi", "Payshanba", "Четверг", "Piyshembi"));
        quizList.add(new QuestionData(147, true, "Durıs jazılǵan sózdi tabıń", "Shembi", "Shenbi", "Shanba", "Суббота", "Shembi"));
        quizList.add(new QuestionData(148, true, "Durıs jazılǵan sózdi tabıń", "Ekshembi", "Yekshembi", "Yakshanba", "Воскресенье", "Ekshembi"));
        quizList.add(new QuestionData(149, true, "Durıs jazılǵan sózdi tabıń", "Dúkán", "Túkán", "Dukan", "Магазин", "Dúkán"));
    }

/*
    public void writeData() {
        Map<String, Object> mainMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < quizList.size(); i++) {
            map.put("id", quizList.get(i).getId());
            map.put("isValid", quizList.get(i).getIsValid());
            map.put("question", quizList.get(i).getQuestion());
            map.put("variant1", quizList.get(i).getVariant1());
            map.put("variant2", quizList.get(i).getVariant2());
            map.put("variant3", quizList.get(i).getVariant3());
            map.put("variant4", quizList.get(i).getVariant4());
            map.put("answer", quizList.get(i).getAnswer());

            db.collection("QuizListNew").document(String.valueOf(i)).set(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.v("AAAAA", "Success");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.v("AAAAA", e.getMessage());
                }
            });
        }
    }
*/

}