package com.example.newser.Couch;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.newser.News.AllFragment;
import com.example.newser.News.CityFragment;
import com.example.newser.News.CountryFragment;
import com.example.newser.News.WorldFragment;
import com.example.newser.R;
import com.example.newser.Utils.BottomNavigationViewHelper;
import com.example.newser.Utils.Permissions;
import com.example.newser.Utils.SectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.Objects;


public class CouchActivity extends AppCompatActivity {
    private static final String TAG = "CouchActivity";
    private static final int ACTIVITY_NUMBER = 2;

    private Context mContext = CouchActivity.this;

    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couch);
        Log.d(TAG, "onCreate: started");

        if(checkPermissionsArray(Permissions.PERMISSIONS)) {

        }else{
            verifyPermissionsArray(Permissions.PERMISSIONS);
        }

        setupViewPager();
        setupBottomNavigationView();

    }

    private void verifyPermissionsArray(String[] permissions) {
        ActivityCompat.requestPermissions(
                CouchActivity.this,
                permissions,
                1
        );
    }

    private boolean checkPermissionsArray(String[] permissions) {
        Log.d(TAG, "checkPermissons started");

        for (int i = 0; i < permissions.length; i++) {
            String check = permissions[i];
            if (!checkPermissions(check)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkPermissions(String permission) {
        int permissionRequest = ActivityCompat.checkSelfPermission(CouchActivity.this, permission);
        if (permissionRequest != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationView = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(CouchActivity.this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);
    }

    private void setupViewPager(){
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllFragment());
        adapter.addFragment(new WorldFragment());
        adapter.addFragment(new CountryFragment());
        adapter.addFragment(new CityFragment());
        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabs.getTabAt(0)).setIcon(R.drawable.ic_all);
        Objects.requireNonNull(tabs.getTabAt(1)).setIcon(R.drawable.ic_world);
        Objects.requireNonNull(tabs.getTabAt(2)).setIcon(R.drawable.ic_country);
        Objects.requireNonNull(tabs.getTabAt(3)).setIcon(R.drawable.ic_city);

    }
}
