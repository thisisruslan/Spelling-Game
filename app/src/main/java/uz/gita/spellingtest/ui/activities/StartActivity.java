package uz.gita.spellingtest.ui.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

import uz.gita.spellingtest.R;
import uz.gita.spellingtest.app.App;

@Keep
public class StartActivity extends AppCompatActivity {
    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(result -> {
            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
                try {
                    mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, this, RC_APP_UPDATE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        findViewById(R.id.start).setOnClickListener(v -> {
            Intent intent = new Intent(App.instance, MenuActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.alphabet).setOnClickListener(v -> {
            Intent intent = new Intent(App.instance, AlphabetActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.img_info).setOnClickListener(v -> {
            Intent intent = new Intent(App.instance, InfoActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.img_rate).setOnClickListener(v -> {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(myAppLinkToMarket);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Play markette bul qosımsha tabılmadı!", Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.img_create_test).setOnClickListener(v -> {
            Intent intent = new Intent(App.instance, CreateTestActivity.class);
            startActivity(intent);
        });

    }

    /* share function
     findViewById(R.id.img_create_test).setOnClickListener(v -> {
            Intent intent = new Intent((Intent.ACTION_SEND));
            intent.setType("text/plain");
            String text = "Qaraqalpaq tilinde qátesiz jazıwdı úyrenemiz.\n\n-> "
                    + "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
            intent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(intent, "Bólisiw:"));
        });
     */

    private void showCompletedUpdate() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Mobil qosımsha jańalanıwǵa tayyar!", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Jańalaw", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(
                getResources().getColor(R.color.snackbar_action_text_color));
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_OK && requestCode == RC_APP_UPDATE) {
            Toast.makeText(this, "Jańalanıw biykarlandı!", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(result -> {
            if (result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS)
                try {
                    mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, this, RC_APP_UPDATE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
        });

    }
}