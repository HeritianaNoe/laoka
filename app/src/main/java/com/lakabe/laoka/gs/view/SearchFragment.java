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
import android.widget.TextView;

import com.lakabe.laoka.gs.MainActivity;
import com.lakabe.laoka.gs.R;
import com.lakabe.laoka.gs.adapter.HeartAdapter;
import com.lakabe.laoka.gs.databinding.FragmentSearchBinding;
import com.lakabe.laoka.gs.interfaces.FoodListener;
import com.lakabe.laoka.gs.model.Food;
import com.lakabe.laoka.gs.utils.WordUtils;

import java.util.List;

public class SearchFragment extends Fragment {
    private FragmentActivity activity;
    private SearchViewModel model;
    private RecyclerView list_food;
    private SearchView search_food;
    private TextView laokaName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = requireActivity();
        model = new ViewModelProvider(this).get(SearchViewModel.class);

        FragmentSearchBinding binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        laokaName = binding.laokaName;

        search_food = binding.searchFood;
        search_food.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null && !query.isEmpty()){
                    model.setTextSearch(query);
                    InputMethodManager imm = (InputMethodManager)requireActivity().getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search_food.getWindowToken(), 0);
                    Log.e(this.getClass().toString(), "Search: " + query);
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

        if(getArguments() != null){
            String searchText = getArguments().getString("ARG_SEARCH");
            model.setTextSearch(searchText);
            setArguments(null);
        }

        model.getTextSearch().observe(getViewLifecycleOwner(), new Observer<String>(){
            @Override
            public void onChanged(String s) {
                laokaName.setText(WordUtils.Capitalize(s));

                model.getAllFoodWithSearch(s).observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
                    @Override
                    public void onChanged(List<Food> foods) {
                        HeartAdapter adapter = new HeartAdapter(activity, foods, new onFoodListener());
                        list_food.setAdapter(adapter);
                        Log.e(this.getClass().toString(), "Search: " + foods.size());
                    }
                });
            }
        });

        return root;
    }

    public class onFoodListener implements FoodListener {
        @Override
        public void onHeartSelected(Food food, int pos, View view) {

        }

        @Override
        public void onRatingSelected(Food food, int pos, View view) {

        }

        @Override
        public void onSelected(Food food, int pos, View view) {
            Bundle args = new Bundle();
            args.putInt("ARG_ID", food.getId());
            Navigation.findNavController(view).navigate(R.id.navigation_detail, args);
        }
    }
}