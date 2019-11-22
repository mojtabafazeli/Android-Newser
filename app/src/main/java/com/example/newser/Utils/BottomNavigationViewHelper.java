package com.example.newser.Utils;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.newser.Couch.CouchActivity;
import com.example.newser.Filters.FiltersActivity;
import com.example.newser.News.NewsActivity;
import com.example.newser.Profile.ProfileActivity;
import com.example.newser.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationView";
    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d (TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
        bottomNavigationViewEx.enableAnimation ( false );
        bottomNavigationViewEx.enableItemShiftingMode ( false );
        bottomNavigationViewEx.enableShiftingMode ( false );
        bottomNavigationViewEx.setTextVisibility ( false );
    }

    public static void enableNavigation (final Context context,BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener ( new BottomNavigationView.OnNavigationItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.couch:
                        Intent couchIntent = new Intent(context, CouchActivity.class );
                        context.startActivity( couchIntent );
                        break;
                    case R.id.news:
                        Intent newsIntent = new Intent(context, NewsActivity.class );
                        context.startActivity( newsIntent );
                        break;
                    case R.id.filters:
                        Intent filtersIntent = new Intent(context, FiltersActivity.class );
                        context.startActivity( filtersIntent );
                        break;
                    case R.id.profile:
                        Intent profileIntent  = new Intent (context, ProfileActivity.class);
                        context.startActivity(profileIntent );
                }
                return false;
            }
        } );
    }
}
