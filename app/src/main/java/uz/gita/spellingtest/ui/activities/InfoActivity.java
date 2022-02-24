package uz.gita.spellingtest.ui.activities;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import uz.gita.spellingtest.R;

@Keep
public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        findViewById(R.id.btn_info_back).setOnClickListener(v -> finish());

        findViewById(R.id.instagram_tv).setOnClickListener(v->{
            Uri uri = Uri.parse("http://instagram.com/_u/ruslan_jumatov");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/ruslan_jumatov")));
            }
        });

        findViewById(R.id.telegram_tv).setOnClickListener(v->{
            Uri uri = Uri.parse("http://t.me/jumatovrm");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.telegram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://t.me/jumatovrm")));
            }
        });


    }
}