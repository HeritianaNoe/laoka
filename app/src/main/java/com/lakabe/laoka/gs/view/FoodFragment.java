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
import android.widget.TextView;
import android.widget.Toast;

import com.lakabe.laoka.gs.R;
import com.lakabe.laoka.gs.adapter.FoodAdapter;
import com.lakabe.laoka.gs.databinding.FragmentFoodBinding;
import com.lakabe.laoka.gs.interfaces.FoodListener;
import com.lakabe.laoka.gs.model.Food;

import java.util.List;

public class FoodFragment extends Fragment {
    private FragmentActivity activity;
    private FoodViewModel model;
    private RecyclerView list_food;
    private TextView laokaName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = requireActivity();
        model = new ViewModelProvider(this).get(FoodViewModel.class);

        FragmentFoodBinding binding = FragmentFoodBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        laokaName = binding.laokaName;
        list_food = binding.listFood;
        list_food.setHasFixedSize(true);
        LinearLayoutManager layout_food = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        list_food.setLayoutManager(layout_food);

        if(getArguments() != null){
            int id = getArguments().getInt("ARG_ID");
            String name = getArguments().getString("ARG_NAME");

            laokaName.setText(name);

            model.getAllFoodWithCategory(id).observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
                @Override
                public void onChanged(List<Food> foods) {
                    FoodAdapter adapter = new FoodAdapter(activity, foods, new onFoodListener());
                    list_food.setAdapter(adapter);
                }
            });
        }

        return root;
    }

    public class onFoodListener implements FoodListener {
        @Override
        public void onHeartSelected(Food food, int pos, View view) {
            model.update(food);
            Toast.makeText(requireContext(), "Nisy fanovana: " + food.getName(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRatingSelected(Food food, int pos, View view) {
            model.update(food);
            Toast.makeText(requireContext(), "Nisy fanovana: " + food.getName(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSelected(Food food, int pos, View view) {
            Bundle args = new Bundle();
            args.putInt("ARG_ID", food.getId());
            Navigation.findNavController(view).navigate(R.id.navigation_detail, args);
        }
    }
}