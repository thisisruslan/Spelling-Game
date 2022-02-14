package uz.gita.spellingtest.app;

import android.app.Application;

import androidx.annotation.Keep;

@Keep
public class App extends Application {
    public static App instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
