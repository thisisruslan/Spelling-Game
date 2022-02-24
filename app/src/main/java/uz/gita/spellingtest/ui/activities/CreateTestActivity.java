package uz.gita.spellingtest.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

import uz.gita.spellingtest.R;
import uz.gita.spellingtest.contract.FlagContract;
import uz.gita.spellingtest.data.QuestionData;
import uz.gita.spellingtest.presenter.CreateTestPresenter;
import uz.gita.spellingtest.repository.MainRepository;
import uz.gita.spellingtest.ui.dialog.InfoDialog;

public class CreateTestActivity extends AppCompatActivity implements FlagContract.ViewCreateTest {
    private FlagContract.PresenterCreateTest presenter;
    private TextInputEditText question;
    private TextInputEditText variant1;
    private TextInputEditText variant2;
    private TextInputEditText variant3;
    private TextInputEditText variant4;
    private LinearLayoutCompat btnCreate;
    private MaterialCheckBox checkBox1;
    private MaterialCheckBox checkBox2;
    private MaterialCheckBox checkBox3;
    private MaterialCheckBox checkBox4;
    private ContentLoadingProgressBar progressBar;
    private ConstraintLayout parent;
    private TextInputEditText answerVariant;
    private InfoDialog infoDialog;
    private ImageView imgPopUp;
    private ImageView btnBack;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }
        loadViews();

        presenter = new CreateTestPresenter(this, new MainRepository());
        btnBack.setOnClickListener(v -> {
            finish();
        });

        setCheckBoxListeners();

        btnCreate.setOnClickListener(view -> {
            clearFieldsFocus();
            if (areFieldsNotEmpty()) {
                if (isNetworkAvailable()) {
                    setClickable(false);
                    progressBar.show();
                    presenter.submitTest(
                            new QuestionData(
                                    1000,
                                    false,
                                    question.getText().toString().trim(),
                                    variant1.getText().toString().trim(),
                                    variant2.getText().toString().trim(),
                                    variant3.getText().toString().trim(),
                                    variant4.getText().toString().trim(),
                                    answerVariant.getText().toString().trim()
                            ));
                } else Toast.makeText(this, "Internetke jalǵanbaǵan!", Toast.LENGTH_SHORT).show();
            }
        });

        imgPopUp.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(this, imgPopUp);
            popup.getMenuInflater().inflate(R.menu.pop_up_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.qq_keyboard: {
                        final String appPackageName = "com.shagalalab.qqkeyboard";
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        break;
                    }
                    case R.id.rule_create_test: {
                        showInfoDialog(R.string.create_test_rules);
                        infoDialog.setInfoTextGravity(2);
                        break;
                    }
                }
                return true;
            });
            popup.show();
        });

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void clearFieldsFocus() {
        question.clearFocus();
        variant1.clearFocus();
        variant2.clearFocus();
        variant3.clearFocus();
        variant4.clearFocus();
    }

    private boolean areFieldsNotEmpty() {
        if (question.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Sorawdı kiritiń!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (variant1.getText().toString().trim().isEmpty()
                || variant2.getText().toString().trim().isEmpty()
                || variant3.getText().toString().trim().isEmpty()
                || variant4.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Variantlardı tolıq kiritiń!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (answerVariant.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Durıs varianttı tanlań!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setCheckBoxListeners() {
        checkBox1.setOnClickListener(view -> selectAnswer(variant1, (CheckBox) view));
        checkBox2.setOnClickListener(view -> selectAnswer(variant2, (CheckBox) view));
        checkBox3.setOnClickListener(view -> selectAnswer(variant3, (CheckBox) view));
        checkBox4.setOnClickListener(view -> selectAnswer(variant4, (CheckBox) view));
    }

    private void selectAnswer(TextInputEditText variant, CheckBox checkBox) {
        answerVariant = variant;
        setCheckBoxState(false);
        checkBox.setChecked(true);
    }

    private void setCheckBoxState(boolean state) {
        checkBox1.setChecked(state);
        checkBox2.setChecked(state);
        checkBox3.setChecked(state);
        checkBox4.setChecked(state);
    }

    private void loadViews() {
        question = findViewById(R.id.et_question);
        variant1 = findViewById(R.id.et_variant1);
        variant2 = findViewById(R.id.et_variant2);
        variant3 = findViewById(R.id.et_variant3);
        variant4 = findViewById(R.id.et_variant4);
        checkBox1 = findViewById(R.id.checkbox1);
        checkBox2 = findViewById(R.id.checkbox2);
        checkBox3 = findViewById(R.id.checkbox3);
        checkBox4 = findViewById(R.id.checkbox4);
        answerVariant = new TextInputEditText(this);
        btnCreate = findViewById(R.id.btn_create);
        progressBar = findViewById(R.id.progress_bar);
        parent = findViewById(R.id.parent_create_test);
        infoDialog = new InfoDialog(this);
        imgPopUp = findViewById(R.id.pop_up_menu);
        btnBack = findViewById(R.id.btn_test_create_back);
    }

    @Override
    public void submitTestFinish() {
        setClickable(true);
        progressBar.hide();
        clearFields();
        if (this != null) {
            showInfoDialog(R.string.info_create_test);
        }
    }

    private void showInfoDialog(int infoText) {
        infoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        infoDialog.show();
        infoDialog.setInfoText(infoText);
    }

    private void clearFields() {
        setCheckBoxState(false);
        question.setText(R.string.question);
        variant1.setText("");
        variant2.setText("");
        variant3.setText("");
        variant4.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void setClickable(Boolean state) {
        parent.setEnabled(state);
        question.setEnabled(state);
        variant1.setEnabled(state);
        variant2.setEnabled(state);
        variant3.setEnabled(state);
        variant4.setEnabled(state);
        btnCreate.setEnabled(state);
        checkBox1.setEnabled(state);
        checkBox2.setEnabled(state);
        checkBox3.setEnabled(state);
        checkBox4.setEnabled(state);
    }
}