package uz.gita.spellingtest.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
    ProgressBar progressBar;
    Sprite wave = new Wave();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        progressBar = findViewById(R.id.spin_kit);
        progressBar.setIndeterminateDrawable(wave);
        presenter = new SplashPresenter(this, new MainRepository(this));
    }

    @Override
    public void launchNextScreen() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getDataFromFirebase();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

}