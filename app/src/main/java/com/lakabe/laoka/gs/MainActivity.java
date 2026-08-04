package com.lakabe.laoka.gs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(toolbar, navController);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_fady:
                navController.navigate(R.id.navigation_fady);
                return true;
            case R.id.action_heart:
                navController.navigate(R.id.navigation_heart);
                return true;
            case R.id.action_insert:
                navController.navigate(R.id.navigation_insert);
                return true;
            case R.id.action_noted:
                showNote();
                return true;
            case R.id.action_shared:
                showShare();
                return true;
            case R.id.action_about:
                navController.navigate(R.id.navigation_about);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showNote(){
        String appPackageName = getPackageName();
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appPackageName));
            startActivity(intent);
        } catch(android.content.ActivityNotFoundException e){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appPackageName));
            startActivity(intent);
        }
    }

    private void showShare(){
        String appPackageName = getPackageName();
        String url = "http://play.google.com/store/apps/details?id="+appPackageName;
        Intent send = new Intent();
        send.setAction(Intent.ACTION_SEND);
        send.putExtra(Intent.EXTRA_TEXT, "Application vaovao miresaka laoka: " + url);
        send.setType("text/*");
        startActivity(Intent.createChooser(send, "Ankafiziko ity application ity!"));
    }
}