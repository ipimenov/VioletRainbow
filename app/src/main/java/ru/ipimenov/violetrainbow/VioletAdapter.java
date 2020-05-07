package ru.ipimenov.violetrainbow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.ipimenov.violetrainbow.data.Violet;

public class VioletAdapter extends RecyclerView.Adapter<VioletAdapter.VioletViewHolder> {

    private ArrayList<Violet> violets;

    public VioletAdapter() {
        violets = new ArrayList<>();
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
        Violet violet = violets.get(position);
        Picasso.get().load(violet.getVioletThumbnailPath()).into(holder.imageViewVioletThumbnail);
    }

    @Override
    public int getItemCount() {
        return violets.size();
    }

    class VioletViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewVioletThumbnail;

        public VioletViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewVioletThumbnail = itemView.findViewById(R.id.imageViewVioletThumbnail);
        }
    }

    public ArrayList<Violet> getViolets() {
        return violets;
    }

    public void setViolets(ArrayList<Violet> violets) {
        this.violets = violets;
        notifyDataSetChanged();
    }

    public void addViolets(ArrayList<Violet> violets) {
        this.violets.addAll(violets);
        notifyDataSetChanged();
    }
}
