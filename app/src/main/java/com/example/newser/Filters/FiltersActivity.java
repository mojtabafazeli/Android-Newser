package com.example.newser.Filters;

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

public class FiltersActivity extends AppCompatActivity {
    private static final String TAG = "FiltersActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_news_new);
        Log.d(TAG, "onCreate: started");

        setupBottomNavigationView ();
    }

    /*
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView() {
        Log.d ( TAG, "setting up BottomNavigationView" );
        LayoutBottomNavigationViewBinding layoutBottomNavigationViewBinding = DataBindingUtil.setContentView(this,R.layout.layout_bottom_navigation_view);
        BottomNavigationViewHelper.setupBottomNavigationView ( layoutBottomNavigationViewBinding.bottomNavViewBar );
        BottomNavigationViewHelper.enableNavigation ( FiltersActivity.this, layoutBottomNavigationViewBinding.bottomNavViewBar );
        Menu menu = layoutBottomNavigationViewBinding.bottomNavViewBar.getMenu ();
        MenuItem item = menu.getItem(1);
        item.setChecked ( true );
    }
}
