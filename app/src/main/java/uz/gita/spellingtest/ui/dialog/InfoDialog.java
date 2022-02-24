package uz.gita.spellingtest.ui.dialog;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import uz.gita.spellingtest.R;

public class InfoDialog extends AlertDialog {
    TextView infoTV;
    Context context;

    @SuppressLint("InflateParams")
    public InfoDialog(Context context) {
        super(context);
        setView(LayoutInflater.from(context).inflate(R.layout.locked_dialog, null));
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoTV = findViewById(R.id.tv_dialog_info);
        findViewById(R.id.btn_understand).setOnClickListener(v -> dismiss());

    }

    public void setInfoTextGravity(int gravity) {
        infoTV.setGravity(gravity);
    }

    public void setInfoText(int infoText) {
        infoTV.setText(infoText);
    }
}
