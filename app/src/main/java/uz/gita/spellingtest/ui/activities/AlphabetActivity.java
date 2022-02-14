package uz.gita.spellingtest.ui.activities;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;

import com.pdfview.PDFView;

import uz.gita.spellingtest.R;

@Keep
public class AlphabetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }
        findViewById(R.id.btn_alphabet_back).setOnClickListener(v -> finish());
        PDFView pdfView = findViewById(R.id.pdfviewer);
        pdfView.fromAsset("qq_imla_rule.pdf").show();

    }

}