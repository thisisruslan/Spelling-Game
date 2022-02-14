package uz.gita.spellingtest.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.yangp.ypwaveview.YPWaveView;

import uz.gita.spellingtest.R;

@Keep
public class ResultsActivity extends AppCompatActivity {
    private int corrects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        TextView correctsText = findViewById(R.id.correctsText);
        TextView mistakesText = findViewById(R.id.mistakesText);
        TextView notSelectedText = findViewById(R.id.notSelectedText);
        TextView actionBarText = findViewById(R.id.title_action_bar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            corrects = bundle.getInt("corrects", 0);
            int mistakes = bundle.getInt("mistakes", 0);
            int notSelected = bundle.getInt("notSelected", 0);
            String gameTitle = bundle.getString("gameTitle", "");

            correctsText.setText("Durıs juwaplar: " + corrects);
            mistakesText.setText("Nadurıs juwaplar: " + mistakes);
            notSelectedText.setText("Juwap berilmegenler: " + notSelected);
            actionBarText.setText(gameTitle +"-basqısh nátiyjesi");
        }

        findViewById(R.id.backMenuBtn).setOnClickListener(v -> finish());

        YPWaveView progressWave = findViewById(R.id.progressWave);
        progressWave.setProgress(10 * corrects);
    }

}