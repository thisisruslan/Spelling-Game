package uz.gita.spellingtest.presenter;


import androidx.annotation.Keep;

import uz.gita.spellingtest.contract.FlagContract;

@Keep
public class SplashPresenter implements FlagContract.PresenterSplash, FlagContract.Model.FinishListener {
    FlagContract.ViewSplash viewSplash;
    FlagContract.Model model;

    //constructor
    public SplashPresenter(FlagContract.ViewSplash viewSplash, FlagContract.Model model) {
        this.viewSplash = viewSplash;
        this.model = model;
    }

    @Override
    public void getDataFinishedListener() {
        viewSplash.launchNextScreen();
    }

    @Override
    public void getDataFromFirebase() {
        model.getDataFromFirebase(this);
    }

    @Override
    public void onDestroy() {
        viewSplash = null;
    }
}
