package com.lakabe.laoka.gs.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lakabe.laoka.gs.R;
import com.lakabe.laoka.gs.adapter.HeartAdapter;
import com.lakabe.laoka.gs.databinding.FragmentHeartBinding;
import com.lakabe.laoka.gs.interfaces.FoodListener;
import com.lakabe.laoka.gs.model.Food;

import java.util.List;

public class HeartFragment extends Fragment {
    private FragmentActivity activity;
    private HeartViewModel model;
    private RecyclerView list_heart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = requireActivity();
        model = new ViewModelProvider(this).get(HeartViewModel.class);

        FragmentHeartBinding binding = FragmentHeartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        list_heart = binding.listHeart;
        list_heart.setHasFixedSize(true);
        LinearLayoutManager layout_food = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        list_heart.setLayoutManager(layout_food);

        model.getAllFoodWithHeart().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                HeartAdapter adapter = new HeartAdapter(activity, foods, new onFoodListener());
                list_heart.setAdapter(adapter);
            }
        });

        return root;
    }

    private class onFoodListener implements FoodListener {
        @Override
        public void onHeartSelected(Food food, int pos, View view) {
            //model.update(food);
        }

        @Override
        public void onRatingSelected(Food food, int pos, View view) {
            //model.update(food);
        }

        @Override
        public void onSelected(Food food, int pos, View view) {
            Bundle args = new Bundle();
            args.putInt("ARG_ID", food.getId());
            Navigation.findNavController(view).navigate(R.id.navigation_detail, args);
        }
    }
}