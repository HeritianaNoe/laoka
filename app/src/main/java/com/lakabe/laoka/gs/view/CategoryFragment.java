package com.lakabe.laoka.gs.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lakabe.laoka.gs.R;
import com.lakabe.laoka.gs.adapter.CategoryAdapter;
import com.lakabe.laoka.gs.databinding.FragmentCategoryBinding;
import com.lakabe.laoka.gs.interfaces.CategoryListener;
import com.lakabe.laoka.gs.model.Category;

import java.util.List;

public class CategoryFragment extends Fragment {
    private FragmentActivity activity;
    private CategoryViewModel model;
    private RecyclerView list_category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = requireActivity();
        model = new ViewModelProvider(this).get(CategoryViewModel.class);

        FragmentCategoryBinding binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        list_category = binding.listCategory;
        list_category.setHasFixedSize(true);
        GridLayoutManager layout_category = new GridLayoutManager(requireActivity(), 2);
        list_category.setLayoutManager(layout_category);

        model.getAllCategory().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                CategoryAdapter adapter = new CategoryAdapter(activity, categories, new onCategoryListener());
                list_category.setAdapter(adapter);
            }
        });


        return root;
    }

    public class onCategoryListener implements CategoryListener {
        @Override
        public void onSelected(Category category, int pos, View view) {
            Bundle args = new Bundle();
            args.putInt("ARG_ID", category.getId());
            args.putString("ARG_NAME", category.getName());
            Navigation.findNavController(view).navigate(R.id.navigation_food, args);
        }
    }
}