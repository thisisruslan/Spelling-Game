package uz.gita.spellingtest.app;

import android.content.Context;
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

    private LocalStorage(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static LocalStorage getInstance(Context context) {
        if (instance == null) return new LocalStorage(context);
        return instance;
    }

    public void saveStageResult(String key, Integer score) {
        preferences.edit().putInt(key, score).apply();
    }

    public Integer getStageResult(String key) {
        return preferences.getInt(key, 0);
    }


}
