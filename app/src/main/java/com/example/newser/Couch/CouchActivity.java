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
import androidx.databinding.DataBindingUtil;

import com.example.newser.News.NewsActivity;
import com.example.newser.R;
import com.example.newser.Utils.BottomNavigationViewHelper;
import com.example.newser.Utils.Permissions;
import com.example.newser.databinding.ActivityNewsNewBinding;
import com.example.newser.databinding.LayoutBottomNavigationViewBinding;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


public class CouchActivity extends AppCompatActivity {
    private static final String TAG = "CouchActivity";
    private static final int ACTIVITY_NUMBER = 2;

    private Context mContext = CouchActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_new);
        Log.d(TAG, "onCreate: started");

        if(checkPermissionsArray(Permissions.PERMISSIONS)) {

        }else{
            verifyPermissionsArray(Permissions.PERMISSIONS);
        }

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
}
