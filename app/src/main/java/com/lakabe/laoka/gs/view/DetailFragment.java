package com.lakabe.laoka.gs.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lakabe.laoka.gs.databinding.FragmentDetailBinding;
import com.lakabe.laoka.gs.dialog.UpdateFoodFragment;
import com.lakabe.laoka.gs.interfaces.UpdateFoodListener;
import com.lakabe.laoka.gs.model.Food;
import com.lakabe.laoka.gs.utils.Converters;

public class DetailFragment extends Fragment {
    private DetailViewModel model;
    private ImageView detail_photo;
    private TextView detail_name;
    private TextView detail_desc;
    private Button btnUpdate;
    private Food f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(this).get(DetailViewModel.class);

        FragmentDetailBinding binding = FragmentDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        detail_photo = binding.detailPhoto;
        detail_name = binding.detailName;
        detail_desc = binding.detailDesc;
        btnUpdate = binding.btnUpdate;

        if(getArguments() != null){
            int id = getArguments().getInt("ARG_ID");

            model.getFood(id).observe(getViewLifecycleOwner(), new Observer<Food>() {
                @Override
                public void onChanged(Food food) {
                    detail_photo.setImageBitmap(Converters.fromBase64ToBitmap(food.getPhoto()));
                    detail_name.setText(food.getName());
                    detail_desc.setText(food.getDesc());
                    f = food;
                }
            });
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateFoodFragment update = UpdateFoodFragment.getInstance(detail_desc.getText().toString(), new onUpdateFoodListener());
                update.show(requireActivity().getSupportFragmentManager(), "FOOD UPDATE");
            }
        });


        return root;
    }

    private class onUpdateFoodListener implements UpdateFoodListener{
        @Override
        public void onDescription(String desc) {
            Food fd = new Food(f.getName(),desc,f.getPhoto(),f.getHeart(),f.getRating(),f.getCategoryId());
            fd.setId(f.getId());
            model.update(fd);
            detail_desc.setText(desc);
            Toast.makeText(requireContext(), "Nisy fanovana: " + fd.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}