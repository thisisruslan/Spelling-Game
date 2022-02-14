package uz.gita.spellingtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.skydoves.progressview.ProgressView;

import java.util.ArrayList;

import uz.gita.spellingtest.R;
import uz.gita.spellingtest.ui.ClickListener;
import uz.gita.spellingtest.data.MenuData;


@Keep
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.VH> {
    private ArrayList<MenuData> list;
    private final Context context;
    private final ClickListener listener;


    public MenuAdapter(ArrayList<MenuData> list, Context context, ClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.list = list;
    }

    public class VH extends RecyclerView.ViewHolder {
        ImageView menuImage = itemView.findViewById(R.id.img_lock);
        TextView menuTitle = itemView.findViewById(R.id.tv_stage_menu);
        ProgressView progressView = itemView.findViewById(R.id.progressView);

        public VH(@NonNull View itemView) {
            super(itemView);
        }

        void bind() {
            MenuData data = list.get(getAdapterPosition());
            int score = data.getMenuResultIndicator();
            itemView.setOnClickListener(v -> {
                if (!data.isOpen() && listener != null) {
                    listener.showDialog();
                } else if (listener != null)
                    listener.menuItemClickListener(getAdapterPosition() + 1);
            });

            menuImage.setImageResource(data.getMenuImage());
            menuTitle.setText(data.getMenuTitle());
            progressView.setProgress(score * 10);
            progressView.setLabelText(score * 10 + "%");

            if (score > 8)
                progressView.getHighlightView().setColor(ContextCompat.getColor(context, R.color.green));
            else if (score >= 5)
                progressView.getHighlightView().setColor(ContextCompat.getColor(context, R.color.yellow));
            else if (score > 0)
                progressView.getHighlightView().setColor(ContextCompat.getColor(context, R.color.red));
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

