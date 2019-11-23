package com.example.newser.News;


import android.os.Bundle;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.example.newser.R;
import com.example.newser.Utils.BottomNavigationViewHelper;
import com.example.newser.Utils.SectionPagerAdapter;
import com.example.newser.databinding.ActivityNewsBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


import java.util.Objects;


public class NewsActivity extends AppCompatActivity {
    private static final String TAG = "NewsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        ActivityNewsBinding activityNewsBinding = DataBindingUtil.setContentView(this, R.layout.activity_news);

        setupBottomNavigationView ();
        setupViewPager();
    }

    private void setupViewPager (){
        SectionPagerAdapter adapter = new SectionPagerAdapter ( getSupportFragmentManager () );
        adapter.addFragment ( new AllFragment () );
        adapter.addFragment ( new WorldFragment () );
        adapter.addFragment ( new CountryFragment () );
        adapter.addFragment ( new CityFragment () );

        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter ( adapter );
        TabLayout tabs =  findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabs.getTabAt(0)).setIcon(R.drawable.ic_all);
        Objects.requireNonNull(tabs.getTabAt(1)).setIcon (R.drawable.ic_world);
        Objects.requireNonNull(tabs.getTabAt(2)).setIcon (R.drawable.ic_country);
        Objects.requireNonNull(tabs.getTabAt(3)).setIcon (R.drawable.ic_city);
    }

    /*
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView() {
        Log.d ( TAG, "setupBottomNavigationView: setting up BottomNavigationView" );
        BottomNavigationViewEx bottomNavigationView = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView ( bottomNavigationView );
        BottomNavigationViewHelper.enableNavigation ( NewsActivity.this,  bottomNavigationView );
        Menu menu =  bottomNavigationView.getMenu ();
        MenuItem item = menu.getItem(1);
        item.setChecked ( true );
    }
}
