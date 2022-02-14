package uz.gita.spellingtest.presenter;

import androidx.annotation.Keep;

import uz.gita.spellingtest.contract.FlagContract;
import uz.gita.spellingtest.data.QuestionData;

@Keep
public class MainPresenter implements FlagContract.PresenterMain {
    FlagContract.ViewMain viewMain;
    FlagContract.Model model;
    private int wrongCount;
    private int correctCount;
    private int currentQuestion = 0;
    private int correctAnswerIndex;
    private QuestionData quiz;
    private String originalAnswer;

    public MainPresenter(FlagContract.ViewMain viewMain, FlagContract.Model model) {
        this.viewMain = viewMain;
        this.model = model;
        this.model.splitLevelQuestions(viewMain.getStartIndex());
        loadNextQuestion();
    }

    private void loadNextQuestion() {
        quiz = model.getQuizByID(currentQuestion);
        originalAnswer = quiz.getAnswer();

        if (quiz.getVariantById(0).equals(originalAnswer)) correctAnswerIndex = 0;
        if (quiz.getVariantById(1).equals(originalAnswer)) correctAnswerIndex = 1;
        if (quiz.getVariantById(2).equals(originalAnswer)) correctAnswerIndex = 2;
        if (quiz.getVariantById(3).equals(originalAnswer)) correctAnswerIndex = 3;
        viewMain.setQuestionText(quiz);
    }

    @Override
    public void checkVariant(int id, int index) {
        String userAnswer = quiz.getVariantById(id);
        viewMain.showResult(correctAnswerIndex, index);
        if (userAnswer.equalsIgnoreCase(originalAnswer)) {
            correctCount++;
        } else {
            wrongCount++;
        }
    }


    @Override
    public void loadQuestions() {

        if (isFinish()) {
            finishQuiz();
            return;
        }
        currentQuestion++;
        loadNextQuestion();
    }

    @Override
    public void saveStageResult(String levelTitle, int correctCount) {
        model.saveStageResult(levelTitle, correctCount);
    }

    @Override
    public boolean isNewLevelRecord(String key, int correctCount) {
        return model.getStageResult(key) < correctCount;
    }

    @Override
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    private boolean isFinish() {
        return currentQuestion + 1 == 10;
    }

    private void finishQuiz() {
        viewMain.finishTest(wrongCount, correctCount);
    }
}
