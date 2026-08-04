package com.lakabe.laoka.gs.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lakabe.laoka.gs.adapter.FadyAdapter;
import com.lakabe.laoka.gs.databinding.FragmentFadyBinding;
import com.lakabe.laoka.gs.dialog.AddFadyFragment;
import com.lakabe.laoka.gs.interfaces.FadyListener;
import com.lakabe.laoka.gs.model.Fady;

import java.util.List;

public class FadyFragment extends Fragment {
    private FragmentActivity activity;
    private FadyViewModel model;
    private RecyclerView list_fady;
    private ImageView add_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = requireActivity();
        model = new ViewModelProvider(this).get(FadyViewModel.class);

        FragmentFadyBinding binding = FragmentFadyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        list_fady = binding.listFady;
        list_fady.setHasFixedSize(true);
        LinearLayoutManager layout_food = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        list_fady.setLayoutManager(layout_food);

        model.getAllFady().observe(getViewLifecycleOwner(), new Observer<List<Fady>>() {
            @Override
            public void onChanged(List<Fady> fadies) {
                FadyAdapter adapter = new FadyAdapter(activity, fadies, new onFadyListener());
                list_fady.setAdapter(adapter);
            }
        });

        add_button = binding.addButton;
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFadyFragment dialog = AddFadyFragment.getInstance(new onFadyListener());
                dialog.show(requireActivity().getSupportFragmentManager(), "ADD");
            }
        });


        return root;
    }

    private class onFadyListener implements FadyListener {
        @Override
        public void onInsert(Fady fady, int pos, View view) {
            model.insert(fady);
            Toast.makeText(requireContext(), "Nampiana: " + fady.getName(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpdate(Fady fady, int pos, View view) {
            model.update(fady);
            Toast.makeText(requireContext(), "Novaina: " + fady.getName(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDelete(Fady fady, int pos, View view) {
            model.delete(fady);
            Toast.makeText(requireContext(), "Novonoina: " + fady.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}