package uz.gita.spellingtest.presenter;

import androidx.annotation.Keep;

import uz.gita.spellingtest.contract.FlagContract;
import uz.gita.spellingtest.data.QuestionData;

@Keep
public class CreateTestPresenter implements FlagContract.PresenterCreateTest, FlagContract.Model.WriteDataFinishListener {
    FlagContract.Model model;
    FlagContract.ViewCreateTest view;

    //constructor
    public CreateTestPresenter(FlagContract.ViewCreateTest view ,FlagContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void submitTest(QuestionData questionData) {
         model.submitTest(questionData, this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void submitTestFinishedListener() {
        if (view != null) view.submitTestFinish();
    }
}
