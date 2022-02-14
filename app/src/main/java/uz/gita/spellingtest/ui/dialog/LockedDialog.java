package uz.gita.spellingtest.ui.dialog;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import uz.gita.spellingtest.R;

public class LockedDialog extends AlertDialog {

    Context context;

    @SuppressLint("InflateParams")
    public LockedDialog(Context context) {
        super(context);
        setView(LayoutInflater.from(context).inflate(R.layout.locked_dialog, null));
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn_understand).setOnClickListener(v-> dismiss());

    }
}
