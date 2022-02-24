package uz.gita.spellingtest.contract;

import androidx.annotation.Keep;

import uz.gita.spellingtest.data.QuestionData;

@Keep
public interface FlagContract {

    interface Model {
        void getDataFromFirebase(ReadDataFinishListener readDataFinishListener);
        void cancelGetDataRequest();

        void submitTest(QuestionData questionData, WriteDataFinishListener writeDataFinishListener);

        interface ReadDataFinishListener {
            void getDataFinishedListener();
        }

        interface WriteDataFinishListener {
            void submitTestFinishedListener();
        }

        //Main repository
        void splitLevelQuestions(int startIndex);
        int getQuizSize();
        int getStageResult(String key);
        void saveStageResult(String levelTitle, int correctCount);
        void clearResults();
        QuestionData getQuizByID(int id);
    }


    //MainActivity
    interface PresenterMain {
        void checkVariant(int id, int index);

        void loadQuestions();

        void saveStageResult(String levelTitle, int correctCount);

        boolean isNewLevelRecord(String key, int correctCount);

        int getCorrectAnswerIndex();
    }
    interface ViewMain {
        void setQuestionText(QuestionData data);

        void showResult(int correctVariantID, int userVariantID);

        int getStartIndex();

        void finishTest(int wrongCount, int correctCount);
    }


    //SplashActivity
    interface PresenterSplash {
        void getDataFromFirebase();
        void onDestroy();
    }

    interface ViewSplash {
        void launchNextScreen();
    }


    //CreateTestActivity
    interface PresenterCreateTest {
        void submitTest(QuestionData questionData);
        void onDestroy();
    }

    interface ViewCreateTest {
        void submitTestFinish();
    }

    //MenuActivity
    interface PresenterMenu {
        int getQuizSize();

        boolean getStageResultBool(String key);

        int getStageResult(String key);

        void clearResults();

    }


}
