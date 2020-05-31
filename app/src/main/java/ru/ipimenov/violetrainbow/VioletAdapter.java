package ru.ipimenov.violetrainbow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.ipimenov.violetrainbow.data.Violet;

public class VioletAdapter extends RecyclerView.Adapter<VioletAdapter.VioletViewHolder> {

    private List<Violet> violets;
    private OnVioletThumbnailClickListener onVioletThumbnailClickListener;
    private OnReachEndListener onReachEndListener;

    public VioletAdapter() {
        violets = new ArrayList<>();
    }

    interface OnVioletThumbnailClickListener {
        void onVioletThumbnailClick(int position);
    }

    interface OnReachEndListener {
        void onReachEnd();
    }

    public void setOnVioletThumbnailClickListener(OnVioletThumbnailClickListener onVioletThumbnailClickListener) {
        this.onVioletThumbnailClickListener = onVioletThumbnailClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    @NonNull
    @Override
    public VioletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.violet_item, parent,
                false);
        return new VioletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VioletViewHolder holder, int position) {
        if (violets.size() >= 10 && position > violets.size() - 3 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }
        Violet violet = violets.get(position);
        Picasso.get().load(violet.getVioletThumbnailPath()).into(holder.imageViewVioletThumbnail);
        holder.imageViewVioletThumbnailName.setText(violet.getVioletName());
    }

    @Override
    public int getItemCount() {
        return violets.size();
    }

    class VioletViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewVioletThumbnail;
        private TextView imageViewVioletThumbnailName;

        public VioletViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewVioletThumbnail = itemView.findViewById(R.id.imageViewVioletThumbnail);
            imageViewVioletThumbnailName = itemView.findViewById(R.id.imageViewVioletThumbnailName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onVioletThumbnailClickListener != null) {
                        onVioletThumbnailClickListener.onVioletThumbnailClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void clear() {
        this.violets.clear();
        notifyDataSetChanged();
    }

    public List<Violet> getViolets() {
        return violets;
    }

    public void setViolets(List<Violet> violets) {
        this.violets = violets;
        notifyDataSetChanged();
    }

    public void addViolets(List<Violet> violets) {
        this.violets.addAll(violets);
        notifyDataSetChanged();
    }
}
