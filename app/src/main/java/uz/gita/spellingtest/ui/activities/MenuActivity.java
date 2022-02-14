package uz.gita.spellingtest.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uz.gita.spellingtest.R;
import uz.gita.spellingtest.adapter.MenuAdapter;
import uz.gita.spellingtest.app.App;
import uz.gita.spellingtest.contract.FlagContract;
import uz.gita.spellingtest.data.MenuData;
import uz.gita.spellingtest.presenter.MenuPresenter;
import uz.gita.spellingtest.repository.MainRepository;
import uz.gita.spellingtest.ui.ClickListener;
import uz.gita.spellingtest.ui.UserActionDialogInterface;
import uz.gita.spellingtest.ui.dialog.ClearDialog;
import uz.gita.spellingtest.ui.dialog.FinishDialog;
import uz.gita.spellingtest.ui.dialog.LockedDialog;

@Keep
public class MenuActivity extends AppCompatActivity implements ClickListener {
    private FlagContract.PresenterMenu presenter;
    private ArrayList<MenuData> data = new ArrayList<>();
    private LockedDialog lockedDialog;
    private MenuAdapter adapter;
    private int lastClickedItemIndex;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        presenter = new MenuPresenter(new MainRepository(this));
        lockedDialog = new LockedDialog(this);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        adapter = new MenuAdapter(data, this, this);
        RecyclerView rv = findViewById(R.id.menu_rv_container);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btn_menu_back).setOnClickListener(v -> finish());

        if (presenter.getStageResultBool(String.valueOf(presenter.getQuizSize()))) {
            FinishDialog finishDialog = new FinishDialog(this);
            finishDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            finishDialog.show();
        }

        findViewById(R.id.clear_results).setOnClickListener(v -> {
            ClearDialog clearDialog = new ClearDialog(this);
            clearDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            clearDialog.setListener(new UserActionDialogInterface() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void positiveButton() {
                    presenter.clearResults();
                    data.clear();
                    loadData();
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void negativeButton() {
                    clearDialog.dismiss();
                }
            });
            clearDialog.show();
        });
    }

    private void loadData() {
        int quizSize = presenter.getQuizSize();
        for (int i = 0; i < quizSize; i++) {
            String level = String.valueOf(i + 1);
            if (presenter.getStageResultBool(String.valueOf(i)) || i == 0)
                data.add(new MenuData(R.drawable.ic_baseline_lock_open_24, level + "-Basqısh", presenter.getStageResult(level), true));
            else
                data.add(new MenuData(R.drawable.ic_baseline_lock_24, level + "-Basqısh", presenter.getStageResult(level), false));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        data.clear();
        loadData();
        adapter.notifyItemChanged(lastClickedItemIndex);
        adapter.notifyItemChanged(lastClickedItemIndex + 1);
    }

    @Override
    public void menuItemClickListener(int level) {
        lastClickedItemIndex = level - 1;
        Intent intent = new Intent(App.instance, MainActivity.class);
        intent.putExtra("level_title", level);
        startActivity(intent);
    }

    @Override
    public void showDialog() {
        lockedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lockedDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}