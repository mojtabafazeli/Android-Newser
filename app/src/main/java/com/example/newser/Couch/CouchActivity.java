package com.example.newser.Couch;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.newser.R;
import com.example.newser.Utils.BottomNavigationViewHelper;
import com.example.newser.databinding.LayoutBottomNavigationViewBinding;


public class CouchActivity extends AppCompatActivity {
    private static final String TAG = "CouchActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_news);
        Log.d(TAG, "onCreate: started");

        setupBottomNavigationView ();

    }

    /*
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView() {
        Log.d ( TAG, "setupBottomNavigationView: setting up BottomNavigationView" );
        LayoutBottomNavigationViewBinding layoutBottomNavViewBinding = DataBindingUtil.setContentView(this, R.layout.layout_bottom_navigation_view);
        BottomNavigationViewHelper.setupBottomNavigationView (  layoutBottomNavViewBinding.bottomNavViewBar );
        BottomNavigationViewHelper.enableNavigation ( CouchActivity.this,  layoutBottomNavViewBinding.bottomNavViewBar );
        Menu menu =  layoutBottomNavViewBinding.bottomNavViewBar.getMenu ();
        MenuItem item = menu.getItem(1);
        item.setChecked ( true );
    }
}
