package com.lakabe.laoka.gs.view;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.lakabe.laoka.gs.MainActivity;
import com.lakabe.laoka.gs.R;
import com.lakabe.laoka.gs.databinding.FragmentInsertBinding;
import com.lakabe.laoka.gs.model.Food;
import com.lakabe.laoka.gs.utils.Converters;
import com.lakabe.laoka.gs.utils.WordUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InsertFragment extends Fragment {
    private InsertViewModel model;
    private EditText txt_name;
    private EditText txt_desc;
    private Spinner txt_category;
    private ImageView img_photo;
    private ImageView addButton;
    private Button loadButton;

    private String name;
    private String desc;
    private int category;
    private boolean isPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(this).get(InsertViewModel.class);

        FragmentInsertBinding binding = FragmentInsertBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        isPhoto = false;
        img_photo = binding.imgPhoto;

        txt_name = binding.txtName;
        txt_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager)requireActivity().getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(txt_name.getWindowToken(), 0);
                    handled = true;
                }

                return handled;
            }
        });
        txt_desc = binding.txtDesc;
        txt_desc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager)requireActivity().getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(txt_desc.getWindowToken(), 0);
                    handled = true;
                }

                return handled;
            }
        });

        txt_category = binding.txtCategory;
        txt_category.setOnItemSelectedListener(new SpinnerListener());

        model.getAllCategoryName().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> names) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, names);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txt_category.setAdapter(adapter);
            }
        });

        loadButton = binding.loadButton;
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
        addButton = binding.addButton;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = WordUtils.Capitalize(txt_name.getText().toString().trim());
                desc = txt_desc.getText().toString().trim();

                if(name.equals("")||desc.equals("")){
                    Snackbar.make(view, "Fenoy tsara daholo azafady!", Snackbar.LENGTH_LONG).show();
                }
                else{
                    String photo = saveImage(requireContext(), getCurrentDateTime());
                    if(photo != null && !photo.isEmpty() && isPhoto){
                        Food f = new Food(name, desc, photo, false, 0, category);
                        model.insert(f);

                        txt_name.setText("");
                        txt_desc.setText("");
                        img_photo.setImageResource(R.drawable.not_image);
                        isPhoto = false;
                        Snackbar.make(view, "Voatahiry ny laoka nampidirinao!", Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        Snackbar.make(view, "Asio sary azafady!", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        return root;
    }

    private class SpinnerListener  implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            category = pos + 1;
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback.
        }
    }

    private String getCurrentDateTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String dt = sdf.format(new Date());
        return dt;
    }

    private void imageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(intent);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                        }
                        catch (IOException e) {
                            Log.e(this.getClass().toString(), e.getMessage());
                        }

                        if(selectedImageBitmap != null){
                            isPhoto = true;
                            img_photo.setImageBitmap(selectedImageBitmap);
                        }
                    }
                }
            });

    private String saveImage(Context context, String filename){
        if(!verifyPermissions()) return null;

        File file = new File(context.getExternalFilesDir("laoka"), filename+".png");

        img_photo.buildDrawingCache();
        Bitmap bmp = img_photo.getDrawingCache();
        try{
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 96, fos);
            fos.close();

            scanFile(context, Uri.fromFile(file));

            return Converters.fromBitmapToBase64(bmp);
        } catch (FileNotFoundException e1) {
            Log.e(this.getClass().toString(), e1.getMessage()); return null;
        } catch (IOException e2) {
            Log.e(this.getClass().toString(), e2.getMessage()); return null;
        }
    }

    private static void scanFile(Context context, Uri imageUri){
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        context.sendBroadcast(scanIntent);
    }

    public Boolean verifyPermissions() {
        int permissionExternalMemory = ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
            String[] STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(requireActivity(), STORAGE_PERMISSIONS, 1);
            return false;
        }

        return true;
    }
}