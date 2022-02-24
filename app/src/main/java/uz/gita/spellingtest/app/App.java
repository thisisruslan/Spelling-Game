package uz.gita.spellingtest.app;

import android.app.Application;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatDelegate;

@Keep
public class App extends Application {
    public static App instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        instance = this;
    }

}
