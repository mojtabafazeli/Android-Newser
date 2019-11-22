package com.example.newser.Profile;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.newser.R;
import com.example.newser.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_profile );
        mProgressBar = findViewById ( R.id.profile_progressBar );
        mProgressBar.setVisibility ( View.GONE );
        setupBottomNavigationView ();
        setupToolbar ();
    }

    private void setupToolbar() {
        androidx.appcompat.widget.Toolbar toolbar =  findViewById ( R.id.profileToolBar );
        setSupportActionBar ( toolbar);

        toolbar.setOnMenuItemClickListener (new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d ( TAG, "OnMenuItemClick: clicked menu item: " + item );

                switch (item.getItemId ()) {
                    case R.id.profileMenu:
                        Log.d ( TAG, "onMenuItemClicked: Navigating" );
                        break;
                }
                return false;
            }
        });
    }

    /*
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView() {
        Log.d ( TAG, "setupBottomNavigationView: setting up BottomNavigationView" );
        BottomNavigationViewEx bottomNavigationViewEx = findViewById ( R.id.bottomNavViewBar );
        BottomNavigationViewHelper.setupBottomNavigationView ( bottomNavigationViewEx );
        BottomNavigationViewHelper.enableNavigation ( ProfileActivity.this, bottomNavigationViewEx );
        Menu menu = bottomNavigationViewEx.getMenu ();
        MenuItem item = menu.getItem(1);
        item.setChecked ( true );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate(R.menu.profile_menu, menu);
        return true;
    }
}
