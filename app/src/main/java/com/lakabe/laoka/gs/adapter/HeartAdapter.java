package com.lakabe.laoka.gs.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.lakabe.laoka.gs.R;
import com.lakabe.laoka.gs.interfaces.FoodListener;
import com.lakabe.laoka.gs.model.Food;
import com.lakabe.laoka.gs.utils.Converters;
import com.lakabe.laoka.gs.utils.WordUtils;

import java.util.List;

public class HeartAdapter extends RecyclerView.Adapter<HeartAdapter.HeartViewHolder>{
    private FragmentActivity activity;
    private List<Food> foods;
    private FoodListener listener;

    public HeartAdapter(FragmentActivity activity, List<Food> foods, FoodListener listener){
        this.activity = activity;
        this.listener = listener;
        this.foods = foods;
    }

    public class HeartViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView photo_laoka;
        TextView name_laoka;
        TextView desc_laoka;

        public HeartViewHolder(@NonNull View itemView) {
            super(itemView);
            photo_laoka = itemView.findViewById(R.id.photo_laoka);
            name_laoka = itemView.findViewById(R.id.name_laoka);
            desc_laoka = itemView.findViewById(R.id.desc_laoka);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Food f = foods.get(getAdapterPosition());
                    listener.onSelected(f, getAdapterPosition(), view);
                }
            });
        }
    }

    @NonNull
    @Override
    public HeartAdapter.HeartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HeartAdapter.HeartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_heart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HeartAdapter.HeartViewHolder holder, int position) {
        Bitmap photo = Converters.fromBase64ToBitmap(foods.get(position).getPhoto());
        String name = foods.get(position).getName();
        String desc = foods.get(position).getDesc();

        holder.photo_laoka.setImageBitmap(photo);
        holder.name_laoka.setText(WordUtils.readMore(name));
        holder.desc_laoka.setText(WordUtils.readMore(desc));
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }
}
