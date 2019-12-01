package com.example.newser.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.newser.Login.LoginActivity;
import com.example.newser.R;
import com.example.newser.Utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment"
            ;
    private androidx.appcompat.widget.Toolbar toolbar;
    private BottomNavigationViewEx bottomNavigationViewEx;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        toolbar = view.findViewById(R.id.profileToolBar);
        bottomNavigationViewEx = view.findViewById(R.id.bottomNavViewBar);

        setupToolbar();
        setupBottomNavigationView();
        setupFirebaseAuth();
        return view;
    }

    private void setupToolbar() {
        toolbar.inflateMenu(R.menu.profile_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            Log.d(TAG, "OnMenuItemClick: clicked menu item: " + item);

            switch (item.getItemId()) {
                case R.id.profileMenu:
                    mAuth.signOut();
                    getActivity().finish();
                    Log.d(TAG, "onMenuItemClicked: Navigating");
                    break;
            }
            return false;
        });
    }

    /*
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(getActivity(), bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);
    }

    //        setupFirebaseAuth();
    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateCahgned: signed-in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed-out");
                    Log.d(TAG, "navigating back to login screen");
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}

