package uz.gita.spellingtest.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import uz.gita.spellingtest.R;
import uz.gita.spellingtest.contract.FlagContract;
import uz.gita.spellingtest.data.QuestionData;
import uz.gita.spellingtest.presenter.MainPresenter;
import uz.gita.spellingtest.repository.MainRepository;
@Keep
public class MainActivity extends AppCompatActivity implements FlagContract.ViewMain {
    private FlagContract.PresenterMain presenter;
    private final int[] variantIds = {R.id.variant1, R.id.variant2, R.id.variant3, R.id.variant4};
    private final TextView[] variantsText = new TextView[variantIds.length];
    private TextView questionText;
    private TextView variant1;
    private TextView variant2;
    private TextView variant3;
    private TextView variant4;
    private TextView tvHelpCount;
    private TextView textStage;
    private TextView tvQuestionCount;
    private int questionCount = 0;
    private int helpCount = 3;
    private String levelTitle;
    private boolean singleResult = true;
    private int skipCount = 0;
    private boolean isSelected = false;
    private boolean isGotHelp = false;
    private LinearLayoutCompat btnSkipNext;
    private ImageView imgPeopleHelp;
    private ImageView imgHelp50;
    private ImageView bgGrayHelp50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        loadViews();
        Bundle bundle = getIntent().getExtras();
        levelTitle = String.valueOf(bundle.getInt("level_title", 0));
        presenter = new MainPresenter(this, new MainRepository());
        connectActions();


        imgPeopleHelp.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String text = "Durıs jazılǵan sózdi tabıń:"
                    + "\n1) " + variant1.getText()
                    + "\n2) " + variant2.getText()
                    + "\n3) " + variant3.getText()
                    + "\n4) " + variant4.getText()
                    + "\n\n-> https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
            intent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(intent, "Bólisiw:"));
        });

        imgHelp50.setOnClickListener(v -> {
            setStateImageHelp50(false, View.VISIBLE);
            if (helpCount != 0 && !isGotHelp) {
                isGotHelp = true;
                helpCount--;
                tvHelpCount.setText(String.valueOf(helpCount));

                if (0 != presenter.getCorrectAnswerIndex()) variant1.setVisibility(View.INVISIBLE);
                else variant2.setVisibility(View.INVISIBLE);
                if (2 != presenter.getCorrectAnswerIndex()) variant3.setVisibility(View.INVISIBLE);
                else variant4.setVisibility(View.INVISIBLE);
            } else
                Toast.makeText(this, "Járdem alıw imkániyatı qalmadı!", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadViews() {
        questionText = findViewById(R.id.questionText);
        variant1 = findViewById(R.id.variant1);
        variant2 = findViewById(R.id.variant2);
        variant3 = findViewById(R.id.variant3);
        variant4 = findViewById(R.id.variant4);
        textStage = findViewById(R.id.tv_stage);
        tvQuestionCount = findViewById(R.id.tv_question_count);
        btnSkipNext = findViewById(R.id.btn_skip);
        tvHelpCount = findViewById(R.id.help_count);
        imgPeopleHelp = findViewById(R.id.img_people_help);
        imgHelp50 = findViewById(R.id.img_help_50);
        bgGrayHelp50 = findViewById(R.id.bg_gray_help50);
        for (int i = 0; i < variantIds.length; i++) {
            variantsText[i] = findViewById(variantIds[i]);
        }
    }

    private void connectActions() {
        variant1.setOnClickListener(v -> {
            presenter.checkVariant(variant1.getId(), 0);
            setClickableMethod(false);
            setStateImageHelp50(false, View.VISIBLE);
        });
        variant2.setOnClickListener(v -> {
            presenter.checkVariant(variant2.getId(), 1);
            setClickableMethod(false);
            setStateImageHelp50(false, View.VISIBLE);
        });
        variant3.setOnClickListener(v -> {
            presenter.checkVariant(variant3.getId(), 2);
            setClickableMethod(false);
            setStateImageHelp50(false, View.VISIBLE);
        });
        variant4.setOnClickListener(v -> {
            presenter.checkVariant(variant4.getId(), 3);
            setClickableMethod(false);
            setStateImageHelp50(false, View.VISIBLE);
        });

        btnSkipNext.setOnClickListener(v -> {
            if (!isSelected) skipCount++;
            presenter.loadQuestions();
            setViewVisibility();
            setClickableMethod(true);
            if (helpCount != 0) {
                setStateImageHelp50(true, View.GONE);
            }

            isSelected = false;
            isGotHelp = false;
        });
    }

    private void setViewVisibility() {
        variant1.setBackgroundResource(R.drawable.question_flag_variants_clickable_bg);
        variant2.setBackgroundResource(R.drawable.question_flag_variants_clickable_bg);
        variant3.setBackgroundResource(R.drawable.question_flag_variants_clickable_bg);
        variant4.setBackgroundResource(R.drawable.question_flag_variants_clickable_bg);
        variant1.setVisibility(View.VISIBLE);
        variant2.setVisibility(View.VISIBLE);
        variant3.setVisibility(View.VISIBLE);
        variant4.setVisibility(View.VISIBLE);
    }

    private void setStateImageHelp50(boolean isClickable, int bgVisibility) {
        imgHelp50.setClickable(isClickable);
        bgGrayHelp50.setVisibility(bgVisibility);
    }

    private void setCorrectResultBackground(int id) {
        switch (id) {
            case 0:
                variant1.setBackgroundResource(R.drawable.bg_correct_answer);
                break;
            case 1:
                variant2.setBackgroundResource(R.drawable.bg_correct_answer);
                break;
            case 2:
                variant3.setBackgroundResource(R.drawable.bg_correct_answer);
                break;
            default:
                variant4.setBackgroundResource(R.drawable.bg_correct_answer);
        }
    }

    private void setIncorrectResultBackground(int id) {
        switch (id) {
            case 0:
                variant1.setBackgroundResource(R.drawable.bg_incorrect_answer);
                break;
            case 1:
                variant2.setBackgroundResource(R.drawable.bg_incorrect_answer);
                break;
            case 2:
                variant3.setBackgroundResource(R.drawable.bg_incorrect_answer);
                break;
            default:
                variant4.setBackgroundResource(R.drawable.bg_incorrect_answer);
        }
    }

    private void setClickableMethod(boolean value) {
        variant1.setEnabled(value);
        variant2.setEnabled(value);
        variant3.setEnabled(value);
        variant4.setEnabled(value);
        variant1.setClickable(value);
        variant2.setClickable(value);
        variant3.setClickable(value);
        variant4.setClickable(value);
    }

    @Override
    public void showResult(int correctVariantID, int userVariantID) {
        isGotHelp = true;
        isSelected = true;
        if (correctVariantID == userVariantID) {
            setCorrectResultBackground(userVariantID);
        } else {
            setCorrectResultBackground(correctVariantID);
            setIncorrectResultBackground(userVariantID);
        }
    }

    @Override
    public int getStartIndex() {
        return (Integer.valueOf(levelTitle) - 1) * 10;
    }

    @Override
    public void setQuestionText(@NonNull QuestionData data) {
        questionCount++;
        textStage.setText(levelTitle + "-Basqısh");
        tvQuestionCount.setText(questionCount + "/10");
        questionText.setText(data.getQuestion());
        for (int i = 0; i < variantsText.length; i++) {
            variantsText[i].setText(data.getVariantById(i));
        }
    }

    @Override
    public void finishTest(int wrongCount, int correctCount) {
        if (singleResult) {
            if (presenter.isNewLevelRecord(levelTitle, correctCount))
                presenter.saveStageResult(levelTitle, correctCount);
            Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            intent.putExtra("corrects", correctCount);
            intent.putExtra("mistakes", wrongCount);
            intent.putExtra("notSelected", skipCount);
            intent.putExtra("gameTitle", levelTitle);
            startActivity(intent);
            finish();
            singleResult = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        singleResult = true;
    }

}
