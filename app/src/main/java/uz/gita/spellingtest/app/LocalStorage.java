package uz.gita.spellingtest.app;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.Keep;


@Keep
public class LocalStorage {
    static LocalStorage instance;
    private final SharedPreferences preferences;

    public void clearResults() {
        preferences.edit().clear().apply();
    }

    private LocalStorage() {
        preferences = PreferenceManager.getDefaultSharedPreferences(App.instance);
    }

    public static LocalStorage getInstance() {
        if (instance == null) return new LocalStorage();
        return instance;
    }

    public void saveStageResult(String key, Integer score) {
        preferences.edit().putInt(key, score).apply();
    }

    public Integer getStageResult(String key) {
        return preferences.getInt(key, 0);
    }


}
