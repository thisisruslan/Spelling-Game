package uz.gita.spellingtest.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import uz.gita.spellingtest.R;

public class FinishDialog extends AlertDialog {

    Context context;
    TextView dialogTextView;

    @SuppressLint("InflateParams")
    public FinishDialog(Context context) {
        super(context);
        setView(LayoutInflater.from(context).inflate(R.layout.finish_dialog, null));
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn_understand_finish).setOnClickListener(v-> dismiss());

    }
}
