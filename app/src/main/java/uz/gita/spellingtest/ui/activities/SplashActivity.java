package uz.gita.spellingtest.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

import uz.gita.spellingtest.R;
import uz.gita.spellingtest.contract.FlagContract;
import uz.gita.spellingtest.presenter.SplashPresenter;
import uz.gita.spellingtest.repository.MainRepository;

public class SplashActivity extends AppCompatActivity implements FlagContract.ViewSplash {
    private FlagContract.PresenterSplash presenter;
    private boolean isActivityExists;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        presenter = new SplashPresenter(this, new MainRepository(this));
        progressBar = findViewById(R.id.spin_kit);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);

    }

    @Override
    public void launchNextScreen() {
        if (isActivityExists) {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityExists = true;
        presenter.getDataFromFirebase();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityExists = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActivityExists = false;
        presenter.onDestroy();
    }

}