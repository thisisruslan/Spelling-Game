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
        String question = "Dur??s jaz??l??an s??zdi tab????";
        quizList.add(new QuestionData(0, true, question, "Konstitutciya", "Konstituciya", "Konstitutsiya", "Konstutsiya", "Konstituciya"));
        quizList.add(new QuestionData(1, true, question, "Kodjeli", "X??jeli", "Xojeli", "Kudjeli", "Xojeli"));
        quizList.add(new QuestionData(2, true, question, "Nukus", "N??kis", "Nokis", "Nuks", "N??kis"));
        quizList.add(new QuestionData(3, true, question, "Koncert", "Konsert", "Kansert", "Kontsert", "Koncert"));
        quizList.add(new QuestionData(4, true, question, "Qaraqalpaqstan", "Karakalpakstan", "Qaraqalpaq??stan", "Qaraqalpa????stan", "Qaraqalpaqstan"));
        quizList.add(new QuestionData(5, true, question, "Saat", "Sa??ad", "Sagat", "Sa??at", "Saat"));
        quizList.add(new QuestionData(6, true, question, "Institut", "Insitut", "Instut", "??nstitut", "Institut"));
        quizList.add(new QuestionData(7, true, question, "Quanish", "Quwan??sh", "Quan??sh", "Quansh", "Quwan??sh"));
        quizList.add(new QuestionData(8, true, question, "??nformatsiya", "Informaciya", "??nformaciya", "Informatsya", "Informaciya"));
        quizList.add(new QuestionData(9, true, question, "Qo????rat", "Kungrad", "Ko????rat", "Qo??rat", "Qo????rat"));

        quizList.add(new QuestionData(10, true, question, "Ozbekstan", "??zbekstan", "Uzbekistan", "??zbekiston", "??zbekstan"));
        quizList.add(new QuestionData(11, true, question, "Aqilli", "Aq??ll??", "Aq??li", "Aq??lli", "Aq??ll??"));
        quizList.add(new QuestionData(12, true, question, "Assalawma ??leykum", "Assalawma ??leyk??m", "Assalawmu aleykum", "Asalawma aleykum", "Assalawma ??leykum"));
        quizList.add(new QuestionData(13, true, question, "Oktyabr", "Oktyabir", "Oktabr", "Aktyabr", "Oktyabr"));
        quizList.add(new QuestionData(14, true, question, "Taqtakopir", "Taxtak??pir", "Taxtakopir", "Taktakopir", "Taxtak??pir"));
        quizList.add(new QuestionData(15, true, question, "Kolledj", "Kallej", "Kollej", "Koledj", "Kolledj"));
        quizList.add(new QuestionData(16, true, question, "Bagban", "Ba??man", "Ba??pan", "Bagpan", "Ba??man"));
        quizList.add(new QuestionData(17, true, question, "Sauapl??", "Sawabl??", "Sawapl??", "Sauapli", "Sawapl??"));
        quizList.add(new QuestionData(18, true, question, "A??ayinshilik", "A??aynshiliq", "Agayinshilik", "Agaynshilik", "A??ayinshilik"));
        quizList.add(new QuestionData(19, true, question, "Operaciya", "Operac??ya", "Aperatsiya", "Operatsiya", "Operaciya"));

        quizList.add(new QuestionData(20, true, question, "Madeniyat", "M??denyat", "Madenyad", "M??deniyat", "M??deniyat"));
        quizList.add(new QuestionData(21, true, question, "Oq??wsh??lar", "Ok??ush??lar", "Oqush??lar", "Oquwsh??lar", "Oq??wsh??lar"));
        quizList.add(new QuestionData(22, true, question, "Rawajlan??w", "Rauajlanuw", "Rauajlan??w", "Rauajlanu", "Rawajlan??w"));
        quizList.add(new QuestionData(23, true, question, "Qorq??t??w", "Qorq??tw", "Kork??tuw", "Qork??tuw", "Qorq??t??w"));
        quizList.add(new QuestionData(24, true, question, "Pitkeriwshi", "Pitker??wshi", "P??tkeriwshi", "Pitkerushi", "Pitkeriwshi"));
        quizList.add(new QuestionData(25, true, question, "Shomanay", "Shumanay", "Shuwmanay", "Chumanay", "Shomanay"));
        quizList.add(new QuestionData(26, true, question, "Lampochka", "Lampuchka", "Lampshk??", "Lamposhka", "Lampochka"));
        quizList.add(new QuestionData(27, true, question, "Pochtaxana", "Poshtaxana", "Pochtaqana", "Poshtakana", "Pochtaxana"));
        quizList.add(new QuestionData(28, true, question, "Ellikqala", "Ellikala", "Ell??kqala", "Elliqala", "Ellikqala"));
        quizList.add(new QuestionData(29, true, question, "Qanl??k??l", "Kanl??k??l", "Qanl??kul", "Qanlikul", "Qanl??k??l"));

        quizList.add(new QuestionData(30, true, question, "Qara??zek", "Qara??zek", "Kara??zek", "Qara??zyek", "Qara??zek"));
        quizList.add(new QuestionData(31, true, question, "T??jribeli", "Tajribeli", "T??jiriybeli", "Tajrybeli", "T??jiriybeli"));
        quizList.add(new QuestionData(32, true, question, "M??sl??h??t", "Maslahat", "M??slaxat", "M??sl??q??t", "M??sl??h??t"));
        quizList.add(new QuestionData(33, true, question, "Ximiya", "Kimya", "X??mya", "X??m??ya", "Ximiya"));
        quizList.add(new QuestionData(34, true, question, "Q??nige", "Q??niyge", "Qanige", "Qanyge", "Q??nige"));
        quizList.add(new QuestionData(35, true, question, "Adebiyat", "??debiyat", "??deb??yat", "Adebyat", "??debiyat"));
        quizList.add(new QuestionData(36, true, question, "Aruwxan", "Ar??wxan", "Aruxan", "Aruwkan", "Ar??wxan"));
        quizList.add(new QuestionData(37, true, question, "Xalqabad", "Qalqabat", "Xal??qabad", "Xalkabat", "Xalqabad"));
        quizList.add(new QuestionData(38, true, question, "??aresiz", "??ares??z", "??arezsiz", "????rezsiz", "????rezsiz"));
        quizList.add(new QuestionData(39, true, question, "Muhabbat", "Muxabbat", "Muhabat", "Muxabbet", "Muhabbat"));

        quizList.add(new QuestionData(40, true, question, "W??layat", "Viloyat", "W??l??yat", "V??layat", "W??layat"));
        quizList.add(new QuestionData(41, true, question, "Takiyatas", "Taq??yatas", "Taxyatas", "Taqyatas", "Taq??yatas"));
        quizList.add(new QuestionData(42, true, question, "Heshqashan", "Esh qashan", "Hesh qashan", "Eshqashan", "Heshqashan"));
        quizList.add(new QuestionData(43, true, question, "??miwd??rya", "??mud??rya", "Amuwdarya", "Amiwdarya", "??miwd??rya"));
        quizList.add(new QuestionData(44, true, question, "Baliq", "Bal??q", "Bal??k", "Balik", "Bal??q"));
        quizList.add(new QuestionData(45, true, question, "Balalik", "Balaliq", "Balal??q", "Balalq", "Balal??q"));
        quizList.add(new QuestionData(46, true, question, "Diyxan", "D??yxan", "Dyqan", "Diyqan", "Diyqan"));
        quizList.add(new QuestionData(47, true, question, "Xabarland??r??w", "Qabarland??r??w", "Qabarland??ru", "Xabarland??ruw", "Xabarland??r??w"));
        quizList.add(new QuestionData(48, true, question, "Bilimlendiriw", "Bilimlend??ruw", "Bilimlendiruw", "Bilmlendriw", "Bilimlendiriw"));
        quizList.add(new QuestionData(49, true, question, "Je??impaz", "Je??ilpaz", "Je??imbaz", "Jenimpaz", "Je??impaz"));

        quizList.add(new QuestionData(50, true, question, "S??wbetlesiw", "Sawbetlesiu", "Sawbetlesuw", "Sawbetlesw", "S??wbetlesiw"));
        quizList.add(new QuestionData(51, true, question, "Iymans??z", "??ymansz", "Imans??z", "??ymans??z", "Iymans??z"));
        quizList.add(new QuestionData(52, true, question, "Moynaq", "Moynak", "Muynak", "Muynaq", "Moynaq"));
        quizList.add(new QuestionData(53, true, question, "Tarix", "Tariyh", "Taryx", "Tariyx", "Tariyx"));
        quizList.add(new QuestionData(54, true, question, "Q??y??nsh??l??q", "Q??ynsh??l??q", "Q??y??nsh??l??k", "Q??y??nshilik", "Q??y??nsh??l??q"));
        quizList.add(new QuestionData(55, true, question, "Yernazar Alak??z", "Ernazar-Alak??z", "Yernazar Alakoz", "Ernazar Alak??z", "Ernazar Alak??z"));
        quizList.add(new QuestionData(56, true, question, "Quwat", "Kuwat", "Quwwat", "Kuat", "Quwat"));
        quizList.add(new QuestionData(57, true, question, "Materiall??q", "Matereall??k", "Materiall??k", "Materiyall??q", "Materiall??q"));
        quizList.add(new QuestionData(58, true, question, "Mehriban", "Mexriban", "Mehr??ban", "Mexriyban", "Mehriban"));
        quizList.add(new QuestionData(59, true, question, "Miymanxana", "Mehmanxana", "Mymanxana", "Mymankana", "Miymanxana"));

        quizList.add(new QuestionData(60, true, question, "Biratala", "Birotala", "Birotola", "Bir??tala", "Birotala"));
        quizList.add(new QuestionData(61, true, question, "Amnistiya", "Amniciya", "Ammisiya", "Amnissiya", "Amnistiya"));
        quizList.add(new QuestionData(62, true, question, "Begimot", "Begemot", "Bigemot", "Begiymot", "Begemot"));
        quizList.add(new QuestionData(63, true, question, "Biyopa", "Beywopa", "Biywopa", "Biywapa", "Biyopa"));
        quizList.add(new QuestionData(64, true, question, "Byudjet", "Buydjet", "Buyjet", "Byudjed", "Byudjet"));
        quizList.add(new QuestionData(65, true, question, "D??rhal", "Darhal", "D??rxal", "Darx??l", "D??rhal"));
        quizList.add(new QuestionData(66, true, question, "Decimetr", "Dicimetr", "Dicemetr", "Deciymetr", "Decimetr"));
        quizList.add(new QuestionData(67, true, question, "Aeroport", "Aeropord", "Airoport", "Ayraport", "Aeroport"));
        quizList.add(new QuestionData(68, true, question, "Klaslas", "K??laslas", "Klaslass", "Klasslas", "Klaslas"));
        quizList.add(new QuestionData(69, true, question, "Sociall??q", "Sotsialliq", "Sociyalliq", "Sotsiyallik", "Sociall??q"));

        quizList.add(new QuestionData(70, true, question, "Qum??rsqa", "Qumirsqa", "Qumrsqa", "Qum??r??sqa", "Qum??rsqa"));
        quizList.add(new QuestionData(71, true, question, "S??lem", "S??lam", "Salam", "Salem", "S??lem"));
        quizList.add(new QuestionData(72, true, question, "??japa", "Ajap??", "Ajyapa", "Ajapa", "??japa"));
        quizList.add(new QuestionData(73, true, question, "??yretetu????n", "Uyretetn", "??yretetug??n", "??yretetin", "??yretetu????n"));
        quizList.add(new QuestionData(74, true, question, "H??kimet", "??kimet", "H??kmet", "H??k??met", "H??kimet"));
        quizList.add(new QuestionData(75, true, question, "Raxmet", "R??xmet", "Rahmet", "Rahmed", "Raxmet"));
        quizList.add(new QuestionData(76, true, question, "Klaviatura", "Klavyatura", "Klaviyatura", "K??laviyatura", "Klaviatura"));
        quizList.add(new QuestionData(77, true, question, "Bassh??", "Bashsh??", "Basshi", "Bash??", "Bassh??"));
        quizList.add(new QuestionData(78, true, question, "Top??raq", "Topiraq", "Top??rak", "Topraq", "Top??raq"));
        quizList.add(new QuestionData(79, true, question, "Avtovokzal", "Avtovakzal", "Avtovagzal", "Aftovokzal", "Avtovokzal"));

        quizList.add(new QuestionData(80, true, question, "Samolyot", "Samalyot", "Somolyot", "Samalyut", "Samolyot"));
        quizList.add(new QuestionData(81, true, question, "Vertolyot", "Virtolyot", "Vertalyot", "Vertalyut", "Vertolyot"));
        quizList.add(new QuestionData(82, true, question, "Okean", "Akean", "Okeyan", "Akeyan", "Okean"));
        quizList.add(new QuestionData(83, true, question, "Sul??wxan", "Suluwxan", "Sul??wqan", "Sulwxan", "Sul??wxan"));
        quizList.add(new QuestionData(84, true, question, "Milliard", "Milyard", "Millyart", "Milliart", "Milliard"));
        quizList.add(new QuestionData(85, true, question, "Teatr", "Tiatr", "Tyatr", "Teat??r", "Teatr"));
        quizList.add(new QuestionData(86, true, question, "Jas ??wlad", "Jas awlad", "Jas awlat", "Jas ??ulad", "Jas ??wlad"));
        quizList.add(new QuestionData(87, true, question, "Kilogramm", "Kilogram", "Kilagramm", "Kiylagram", "Kilogramm"));
        quizList.add(new QuestionData(88, true, question, "Televizor", "Televizr", "Telezivor", "Televiyzr", "Televizor"));
        quizList.add(new QuestionData(89, true, question, "Tuw??l??an k??n", "Tu??l??an kun", "Tuw??an k??n", "Tuw??l??an kun", "Tuw??l??an k??n"));

        quizList.add(new QuestionData(90, true, question, "Chempionat", "Chempiyonat", "Shempionat", "Chempyonat", "Chempionat"));
        quizList.add(new QuestionData(91, true, question, "Kegeyli", "Kegeyl??", "Kigeyli", "Kegiyli", "Kegeyli"));
        quizList.add(new QuestionData(92, true, question, "Teorema", "Tiyorema", "Tearema", "Tiyarema", "Teorema"));
        quizList.add(new QuestionData(93, true, question, "Qaharman", "Qahraman", "Q??harman", "Qaqarman", "Qaharman"));
        quizList.add(new QuestionData(94, true, question, "H??kimiyat", "H??kimyat", "Hakimyat", "H??kimiat", "H??kimiyat"));
        quizList.add(new QuestionData(95, true, question, "Imkaniyat", "??mkaniyat", "??mkanyat", "Imkanyat", "Imkaniyat"));
        quizList.add(new QuestionData(96, true, question, "J??rdemlesiw", "Jardemlesiw", "J??rdemles??w", "Jardemlesuw", "J??rdemlesiw"));
        quizList.add(new QuestionData(97, true, question, "To????z??nsh??", "Toqq??z??nsh??", "To????z??nch??", "Tog??z??nsh??", "To????z??nsh??"));
        quizList.add(new QuestionData(98, true, question, "Medicina", "Medecina", "Meditsina", "Medetsina", "Medicina"));
        quizList.add(new QuestionData(99, true, question, "Mashina", "Mashiyna", "Mash??n", "Machina", "Mashina"));

        quizList.add(new QuestionData(100, true, question, "Xalqabad", "Qalqabat", "Xal??qabad", "Xalkabat", "Xalqabad"));
        quizList.add(new QuestionData(101, true, question, "Miymanxana", "Mehmanxana", "Mymanxana", "Mymankana", "Miymanxana"));
        quizList.add(new QuestionData(102, true, question, "Biyopa", "Beywopa", "Biywopa", "Biywapa", "Biyopa"));
        quizList.add(new QuestionData(103, true, question, "Quanish", "Quwan??sh", "Quan??sh", "Quansh", "Quwan??sh"));
        quizList.add(new QuestionData(104, true, question, "M??sl??h??t", "Maslahat", "M??slaxat", "M??sl??q??t", "M??sl??h??t"));
        quizList.add(new QuestionData(105, true, question, "Awa", "Aua", "xxx", "xxx", "Awa"));
        quizList.add(new QuestionData(106, true, question, "W??layat", "Viloyat", "W??l??yat", "V??layat", "W??layat"));
        quizList.add(new QuestionData(107, true, question, "Kilogramm", "Kilogram", "Kilagramm", "Kiylagram", "Kilogramm"));
        quizList.add(new QuestionData(108, true, question, "Telefon", "Telifon", "Telepon", "Tilifon", "Telefon"));
        quizList.add(new QuestionData(109, true, question, "Sh??msh??q", "Sh??mch??q", "Sh??msh??k", "Ch??msh??q", "Sh??msh??q"));

        quizList.add(new QuestionData(110, true, question, "Multfilm", "Multfilim", "Murtpilm", "Multfiylim", "Multfilm"));
        quizList.add(new QuestionData(111, true, question, "Maqset", "Maxset", "Makset", "Maksed", "Maqset"));
        quizList.add(new QuestionData(112, true, question, "Respublikal??q", "Resbuplikal??q", "Respublikalik", "Respubliqal??q", "Respublikal??q"));
        quizList.add(new QuestionData(113, true, question, "Ta??law", "Tanlaw", "Ta??lauw", "Tanlau", "Ta??law"));
        quizList.add(new QuestionData(114, true, question, "Prezident", "Preziydent", "Preziydend", "Prizident", "Prezident"));
        quizList.add(new QuestionData(115, true, question, "Filial", "Filiyal", "Fillial", "Filyal", "Filial"));
        quizList.add(new QuestionData(116, true, question, "S??wbet", "Sawbet", "S??ubet", "Sa??bet", "S??wbet"));
        quizList.add(new QuestionData(117, true, question, "Emlewxana", "Emleuxana", "Yemlewxana", "Yemleuxana", "Emlewxana"));
        quizList.add(new QuestionData(118, true, question, "Dialog", "Dialok", "Diyalog", "D??yalok", "Dialog"));
        quizList.add(new QuestionData(119, true, question, "Xabar", "Kabar", "Qabar", "xxx", "Xabar"));

        quizList.add(new QuestionData(120, true, "Dur??s jaz??l??an s??zdi tab????", "T??miyinlew", "T??miynlew", "T??minlew", "Tamynlew", "T??miyinlew"));
        quizList.add(new QuestionData(121, true, "Dur??s jaz??l??an s??zdi tab????", "Tigiwshi", "Tiginshi", "Tikinshi", "Tig??wshi", "Tigiwshi"));
        quizList.add(new QuestionData(122, true, "Dur??s jaz??l??an s??zdi tab????", "Heshkim", "Hesh kim", "Eshkim", "Eshgim", "Heshkim"));
        quizList.add(new QuestionData(123, true, "Dur??s jaz??l??an s??zdi tab????", "Densawl??q", "Den-sawl??q", "Den sawl??q", "Densawl??k", "Densawl??q"));
        quizList.add(new QuestionData(124, true, "Dur??s jaz??l??an s??zdi tab????", "Ministrlik", "Ministirlik", "Ministrl??q", "Ministerlik", "Ministrlik"));
        quizList.add(new QuestionData(125, true, "Dur??s jaz??l??an s??zdi tab????", "Sha??araq", "Shanaraq", "Sha??arak", "Semya", "Sha??araq"));
        quizList.add(new QuestionData(126, true, "Dur??s jaz??l??an s??zdi tab????", "Aw??l", "Awil", "Au??l", "Awl", "Aw??l"));
        quizList.add(new QuestionData(127, true, "Dur??s jaz??l??an s??zdi tab????", "Xal??qaral??q", "Xalqaral??q", "Xalqaral??k", "Qal??qaral??q", "Xal??qaral??q"));
        quizList.add(new QuestionData(128, true, "Dur??s jaz??l??an s??zdi tab????", "Meni??she", "Menimshe", "Meninshe", "xxx", "Meni??she"));
        quizList.add(new QuestionData(129, true, "Dur??s jaz??l??an s??zdi tab????", "Haqq??nda", "Haq??nda", "Haq??da", "Hakk??nda", "Haqq??nda"));

        quizList.add(new QuestionData(130, true, "Dur??s jaz??l??an s??zdi tab????", "Biologiya", "Biyologiya", "Biologia", "Biologya", "Biologiya"));
        quizList.add(new QuestionData(131, true, "Dur??s jaz??l??an s??zdi tab????", "Turist", "Tuwrist", "Turis", "Tourist", "Turist"));
        quizList.add(new QuestionData(132, true, "Dur??s jaz??l??an s??zdi tab????", "Geografiya", "Giyografiya", "Geografya", "Geyografya", "Geografiya"));
        quizList.add(new QuestionData(133, true, "Dur??s jaz??l??an s??zdi tab????", "Aw??zeki", "Aw??z eki", "Aw??zyeki", "Awzeki", "Aw??zeki"));
        quizList.add(new QuestionData(134, true, "Dur??s jaz??l??an s??zdi tab????", "Prokuratura", "Prokratura", "Prakratuwra", "Prokuratuwra", "Prokuratura"));
        quizList.add(new QuestionData(135, true, "Dur??s jaz??l??an s??zdi tab????", "D??RIXANA", "D??R??XANA", "D??RXANA", "APTEKA", "D??RIXANA"));
        quizList.add(new QuestionData(136, true, "Dur??s jaz??l??an s??zdi tab????", "Leytenant", "Letenant", "Litiynat", "Litenat", "Leytenant"));
        quizList.add(new QuestionData(137, true, "Dur??s jaz??l??an s??zdi tab????", "Qo????raw", "Qo??raw", "Qon??raw", "Zvonok", "Qo????raw"));
        quizList.add(new QuestionData(138, true, "Dur??s jaz??l??an s??zdi tab????", "Dushpan", "Dushman", "Dushban", "xxx", "Dushpan"));
        quizList.add(new QuestionData(139, true, "Dur??s jaz??l??an s??zdi tab????", "M??n??sibet", "M??nasibet", "M??nasiybet", "M??n??sebet", "M??n??sibet"));

        quizList.add(new QuestionData(140, true, "Dur??s jaz??l??an s??zdi tab????", "Dumalaq", "Domalak", "Domalaq", "Duwmalaq", "Dumalaq"));
        quizList.add(new QuestionData(141, true, "Dur??s jaz??l??an s??zdi tab????", "Nawr??z", "Navruz", "Naur??z", "Nawriz", "Nawr??z"));
        quizList.add(new QuestionData(142, true, "Dur??s jaz??l??an s??zdi tab????", "Kirpitiken", "Qirpitken", "Qirptken", "Qirptiken", "Kirpitiken"));
        quizList.add(new QuestionData(143, true, "Dur??s jaz??l??an s??zdi tab????", "Velosiyped", "Velosipet", "Belsebet", "Velosiped", "Velosiped"));
        quizList.add(new QuestionData(144, true, "Dur??s jaz??l??an s??zdi tab????", "D??yshembi", "D??shembi", "Dushanba", "??????????????????????", "D??yshembi"));
        quizList.add(new QuestionData(145, true, "Dur??s jaz??l??an s??zdi tab????", "S??rshembi", "Sarshemb??", "Sarshenbi", "C????????", "S??rshembi"));
        quizList.add(new QuestionData(146, true, "Dur??s jaz??l??an s??zdi tab????", "Piyshembi", "Pishembi", "Payshanba", "??????????????", "Piyshembi"));
        quizList.add(new QuestionData(147, true, "Dur??s jaz??l??an s??zdi tab????", "Shembi", "Shenbi", "Shanba", "??????????????", "Shembi"));
        quizList.add(new QuestionData(148, true, "Dur??s jaz??l??an s??zdi tab????", "Ekshembi", "Yekshembi", "Yakshanba", "??????????????????????", "Ekshembi"));
        quizList.add(new QuestionData(149, true, "Dur??s jaz??l??an s??zdi tab????", "D??k??n", "T??k??n", "Dukan", "??????????????", "D??k??n"));
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