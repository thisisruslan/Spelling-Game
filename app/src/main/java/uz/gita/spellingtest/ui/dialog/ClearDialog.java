package uz.gita.spellingtest.ui.dialog;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import uz.gita.spellingtest.R;
import uz.gita.spellingtest.ui.UserActionDialogInterface;

public class ClearDialog extends AlertDialog {

    Context context;
    private UserActionDialogInterface listener;

    @SuppressLint("InflateParams")
    public ClearDialog(Context context) {
        super(context);
        setView(LayoutInflater.from(context).inflate(R.layout.clear_dialog, null));
        this.context = context;
    }

    public void setListener(UserActionDialogInterface listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn_positive).setOnClickListener(v -> {
            listener.positiveButton();
            cancel();
        });

        findViewById(R.id.btn_negative).setOnClickListener(v -> {
            listener.negativeButton();
            cancel();
        });

    }
}
