package com.lakabe.laoka.gs.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lakabe.laoka.gs.R;
import com.lakabe.laoka.gs.dialog.RatingFragment;
import com.lakabe.laoka.gs.interfaces.FoodListener;
import com.lakabe.laoka.gs.model.Food;

import com.google.android.material.imageview.ShapeableImageView;
import com.lakabe.laoka.gs.utils.Converters;
import com.lakabe.laoka.gs.utils.WordUtils;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private FragmentActivity activity;
    private List<Food> foods;
    private FoodListener listener;

    public FoodAdapter(FragmentActivity activity, List<Food> foods, FoodListener listener){
        this.activity = activity;
        this.listener = listener;
        this.foods = foods;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{
        ShapeableImageView photo_laoka;
        TextView name_laoka;
        ImageView heart_laoka;
        ImageView[] start_laoka = new ImageView[5];
        LinearLayout layout_start;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            photo_laoka = itemView.findViewById(R.id.photo_laoka);
            name_laoka = itemView.findViewById(R.id.name_laoka);
            heart_laoka = itemView.findViewById(R.id.heart_laoka);
            start_laoka[0] = itemView.findViewById(R.id.start_laoka5);
            start_laoka[1] = itemView.findViewById(R.id.start_laoka4);
            start_laoka[2] = itemView.findViewById(R.id.start_laoka3);
            start_laoka[3] = itemView.findViewById(R.id.start_laoka2);
            start_laoka[4] = itemView.findViewById(R.id.start_laoka1);
            layout_start = itemView.findViewById(R.id.layout_start);

            photo_laoka.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Food f = foods.get(getAdapterPosition());
                    listener.onSelected(f, getAdapterPosition(), view);
                }
            });
            heart_laoka.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Food f = foods.get(getAdapterPosition());
                    boolean heart = !f.getHeart();

                    Food food;
                    if(heart){
                        if(f.getRating() < 2)
                            food = new Food(f.getName(),f.getDesc(),f.getPhoto(),heart,2,f.getCategoryId());
                        else
                            food = new Food(f.getName(),f.getDesc(),f.getPhoto(),heart,f.getRating(),f.getCategoryId());
                    }
                    else{
                        if(f.getRating() > 2)
                            food = new Food(f.getName(),f.getDesc(),f.getPhoto(),heart,2,f.getCategoryId());
                        else
                            food = new Food(f.getName(),f.getDesc(),f.getPhoto(),heart,f.getRating(),f.getCategoryId());
                    }

                    food.setId(f.getId());
                    foods.set(getAdapterPosition(), food);
                    notifyItemChanged(getAdapterPosition());
                    listener.onHeartSelected(food, getAdapterPosition(), view);
                }
            });
            layout_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Food f = foods.get(getAdapterPosition());
                    RatingFragment rating = RatingFragment.getInstance(f, getAdapterPosition(), new onFoodListener());
                    rating.show(activity.getSupportFragmentManager(), "RATING");
                }
            });
        }
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        final Food food = foods.get(position);
        Bitmap photo = Converters.fromBase64ToBitmap(food.getPhoto());
        String name = food.getName();
        boolean heart = food.getHeart();
        int rating = food.getRating();

        holder.photo_laoka.setImageBitmap(photo);
        holder.name_laoka.setText(WordUtils.readMore(name));

        if(heart)
            holder.heart_laoka.setImageResource(R.drawable.ic_heart_red);
        else
            holder.heart_laoka.setImageResource(R.drawable.ic_heart_white);

        for(int i = 0; i < holder.start_laoka.length; i++){
            if(rating == 0){
                holder.start_laoka[i].setImageResource(R.drawable.ic_star_white);
            }
            else{
                if(rating >= (i+1)){
                    holder.start_laoka[(holder.start_laoka.length-1)-i].setImageResource(R.drawable.ic_star_yellow);
                }
                else{
                    holder.start_laoka[(holder.start_laoka.length-1)-i].setImageResource(R.drawable.ic_star_white);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    private class onFoodListener implements FoodListener{
        @Override
        public void onHeartSelected(Food food, int pos, View view) {

        }

        @Override
        public void onRatingSelected(Food f, int pos, View view) {
            Food food;
            if(f.getRating() < 2){
                food = new Food(f.getName(),f.getDesc(),f.getPhoto(),false,f.getRating(),f.getCategoryId());
            }
            else{
                if(f.getRating() == 5)
                    food = new Food(f.getName(),f.getDesc(),f.getPhoto(),true,f.getRating(),f.getCategoryId());
                else
                    food = new Food(f.getName(),f.getDesc(),f.getPhoto(),f.getHeart(),f.getRating(),f.getCategoryId());
            }

            food.setId(f.getId());
            foods.set(pos, food);
            notifyItemChanged(pos);
            listener.onRatingSelected(food, pos, view);
        }

        @Override
        public void onSelected(Food food, int pos, View view) {

        }
    }
}
