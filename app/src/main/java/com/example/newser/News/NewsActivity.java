package com.example.newser.News;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.newser.R;
import com.example.newser.Utils.BottomNavigationViewHelper;
import com.example.newser.Utils.SectionPagerAdapter;
import com.example.newser.databinding.LayoutBottomNavigationViewBinding;
import com.example.newser.databinding.LayoutCenterViewpagerBinding;
import com.example.newser.databinding.LayoutTopTabsBinding;

import java.util.Objects;


public class NewsActivity extends AppCompatActivity {
    private static final String TAG = "NewsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_news);

        setupBottomNavigationView ();
        setupViewPager();
    }

    private void setupViewPager (){
        LayoutCenterViewpagerBinding layoutCenterViewpagerBinding = DataBindingUtil.setContentView(this,R.layout.layout_center_viewpager);
        SectionPagerAdapter adapter = new SectionPagerAdapter ( getSupportFragmentManager () );
        adapter.addFragment ( new AllFragment () );
        adapter.addFragment ( new WorldFragment () );
        adapter.addFragment ( new CountryFragment () );
        adapter.addFragment ( new CityFragment () );
        layoutCenterViewpagerBinding.container.setAdapter ( adapter );

        LayoutTopTabsBinding layoutTopTabsBinding = DataBindingUtil.setContentView(this, R.layout.layout_top_tabs);
        layoutTopTabsBinding.tabs.setupWithViewPager ( layoutCenterViewpagerBinding.container );
        Objects.requireNonNull(layoutTopTabsBinding.tabs.getTabAt(0)).setIcon(R.drawable.ic_all);
        Objects.requireNonNull(layoutTopTabsBinding.tabs.getTabAt(1)).setIcon (R.drawable.ic_world);
        Objects.requireNonNull(layoutTopTabsBinding.tabs.getTabAt(2)).setIcon (R.drawable.ic_country);
        Objects.requireNonNull(layoutTopTabsBinding.tabs.getTabAt(3)).setIcon (R.drawable.ic_city);
    }

    /*
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView() {
        Log.d ( TAG, "setupBottomNavigationView: setting up BottomNavigationView" );
        LayoutBottomNavigationViewBinding layoutBottomNavViewBinding = DataBindingUtil.setContentView(this, R.layout.layout_bottom_navigation_view);
        BottomNavigationViewHelper.setupBottomNavigationView (  layoutBottomNavViewBinding.bottomNavViewBar );
        BottomNavigationViewHelper.enableNavigation ( NewsActivity.this,  layoutBottomNavViewBinding.bottomNavViewBar );
        Menu menu =  layoutBottomNavViewBinding.bottomNavViewBar.getMenu ();
        MenuItem item = menu.getItem(1);
        item.setChecked ( true );
    }
}
