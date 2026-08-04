package com.lakabe.laoka.gs.view;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.lakabe.laoka.gs.MainActivity;
import com.lakabe.laoka.gs.R;
import com.lakabe.laoka.gs.adapter.FoodAdapter;
import com.lakabe.laoka.gs.adapter.SlideAdapter;
import com.lakabe.laoka.gs.databinding.FragmentHomeBinding;
import com.lakabe.laoka.gs.interfaces.CategoryListener;
import com.lakabe.laoka.gs.interfaces.FoodListener;
import com.lakabe.laoka.gs.model.Category;
import com.lakabe.laoka.gs.model.Fady;
import com.lakabe.laoka.gs.model.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment{
    private FragmentActivity activity;
    private HomeViewModel model;
    private RecyclerView list_food;
    private RecyclerView list_category;
    private ImageView menu_button;
    private SearchView search_food;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = requireActivity();
        model = new ViewModelProvider(this).get(HomeViewModel.class);

        FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        search_food = binding.searchFood;
        search_food.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null && !query.isEmpty()){
                    Bundle args = new Bundle();
                    args.putString("ARG_SEARCH", query);
                    Navigation.findNavController(search_food).navigate(R.id.navigation_search, args);

                    InputMethodManager imm = (InputMethodManager)requireActivity().getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search_food.getWindowToken(), 0);

                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        list_food = binding.listFood;
        list_food.setHasFixedSize(true);
        LinearLayoutManager layout_food = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        list_food.setLayoutManager(layout_food);

        model.getAllFady().observe(getViewLifecycleOwner(), new Observer<List<Fady>>() {
            @Override
            public void onChanged(List<Fady> fadies) {
                model.getAllFood().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
                    @Override
                    public void onChanged(List<Food> foods) {
                        List<Food> fs1 = new ArrayList<>();
                        List<Food> fs2 = new ArrayList<>();
                        List<Food> fs3 = new ArrayList<>();
                        List<Food> fs = new ArrayList<>();

                        for(Fady fady: fadies){
                            for(Food f: foods){
                                if(!f.getName().contains(fady.getName())){
                                    if(f.getRating() > 3)
                                        fs1.add(f);
                                    else if(f.getRating() > 1 && f.getRating() < 4)
                                        fs2.add(f);
                                    else
                                        fs3.add(f);
                                }
                            }

                            Random r = new Random();
                            int idx1, idx2;
                            if(fs1.size() > 1){
                                idx1 = r.nextInt(fs1.size());
                                fs.add(fs1.get(idx1));
                                idx2 = r.nextInt(fs1.size());
                                if(idx1 != idx2)
                                    fs.add(fs1.get(idx2));
                            }

                            if(fs2.size() > 1){
                                idx1 = r.nextInt(fs2.size());
                                fs.add(fs2.get(idx1));
                                idx2 = r.nextInt(fs2.size());
                                if(idx1 != idx2)
                                    fs.add(fs2.get(idx2));
                            }

                            if(fs3.size() > 1){
                                idx1 = r.nextInt(fs3.size());
                                fs.add(fs3.get(idx1));
                                idx2 = r.nextInt(fs3.size());
                                if(idx1 != idx2)
                                    fs.add(fs3.get(idx2));
                            }

                            FoodAdapter adapterFood = new FoodAdapter(activity, fs, new onFoodListener());
                            list_food.setAdapter(adapterFood);

                        }

                    }
                });
            }
        });



        list_category = binding.listCategory;
        list_category.setHasFixedSize(true);
        LinearLayoutManager layout_category = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        list_category.setLayoutManager(layout_category);

        model.getAllCategory().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                SlideAdapter adapterCategory = new SlideAdapter(activity, categories, new onCategoryListener());
                list_category.setAdapter(adapterCategory);
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

    private class onCategoryListener implements CategoryListener {
        @Override
        public void onSelected(Category category, int pos, View view) {
            Navigation.findNavController(view).navigate(R.id.navigation_category);
        }
    }
}