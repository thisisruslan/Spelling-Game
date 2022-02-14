package uz.gita.spellingtest.presenter;

import androidx.annotation.Keep;

import uz.gita.spellingtest.contract.FlagContract;

@Keep
public class MenuPresenter implements FlagContract.PresenterMenu {
    FlagContract.Model model;

    //constructor
    public MenuPresenter(FlagContract.Model model) {
        this.model = model;
    }


    @Override
    public int getQuizSize() {
        return model.getQuizSize();
    }

    @Override
    public boolean getStageResultBool(String key) {
        return model.getStageResult(key) > 8;
    }

    @Override
    public int getStageResult(String key) {
        return model.getStageResult(key);
    }

    @Override
    public void clearResults() {
        model.clearResults();
    }



}
