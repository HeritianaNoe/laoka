package com.lakabe.laoka.gs.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lakabe.laoka.gs.R;
import com.lakabe.laoka.gs.interfaces.CategoryListener;
import com.lakabe.laoka.gs.model.Category;

import com.lakabe.laoka.gs.utils.Converters;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private FragmentActivity activity;
    private List<Category> categories;
    private CategoryListener listener;

    public CategoryAdapter(FragmentActivity activity, List<Category> categories, CategoryListener listener){
        this.listener = listener;
        this.categories = categories;
        this.activity = activity;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView photo_laoka;
        TextView name_laoka;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            photo_laoka = itemView.findViewById(R.id.photo_laoka);
            name_laoka = itemView.findViewById(R.id.name_laoka);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onSelected(categories.get(getAdapterPosition()), getAdapterPosition(), view);
                }
            });
        }
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        final Category category = categories.get(position);
        Bitmap photo = Converters.fromBase64ToBitmap(category.getPhoto());
        String name = category.getName();

        holder.photo_laoka.setImageBitmap(photo);
        holder.name_laoka.setText(name);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
