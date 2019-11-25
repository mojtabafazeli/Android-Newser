package com.example.newser.Profile;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.newser.R;
import com.example.newser.Utils.BottomNavigationViewHelper;
import com.example.newser.databinding.ActivityProfileNewBinding;
import com.example.newser.databinding.LayoutBottomNavigationViewBinding;
import com.example.newser.databinding.SnippetTopProfileBarBinding;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        ActivityProfileNewBinding activityProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_profile_new);
        activityProfileBinding.profileProgressBar.setVisibility ( View.GONE );
        setupBottomNavigationView ();
        setupToolbar ();
    }

    private void setupToolbar() {
        SnippetTopProfileBarBinding snippetTopProfileBarBinding = DataBindingUtil.setContentView(this,R.layout.snippet_top_profile_bar);
        setSupportActionBar ( snippetTopProfileBarBinding.profileToolBar);

        snippetTopProfileBarBinding.profileToolBar.setOnMenuItemClickListener (item -> {
            Log.d ( TAG, "OnMenuItemClick: clicked menu item: " + item );

            switch (item.getItemId ()) {
                case R.id.profileMenu:
                    Log.d ( TAG, "onMenuItemClicked: Navigating" );
                    break;
            }
            return false;
        });
    }

    /*
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView() {
        Log.d ( TAG, "setupBottomNavigationView: setting up BottomNavigationView" );
        LayoutBottomNavigationViewBinding layoutBottomNavViewBinding = DataBindingUtil.setContentView(this, R.layout.layout_bottom_navigation_view);
        BottomNavigationViewHelper.setupBottomNavigationView (  layoutBottomNavViewBinding.bottomNavViewBar );
        BottomNavigationViewHelper.enableNavigation ( ProfileActivity.this,  layoutBottomNavViewBinding.bottomNavViewBar );
        Menu menu =  layoutBottomNavViewBinding.bottomNavViewBar.getMenu ();
        MenuItem item = menu.getItem(1);
        item.setChecked ( true );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate(R.menu.profile_menu, menu);
        return true;
    }
}
