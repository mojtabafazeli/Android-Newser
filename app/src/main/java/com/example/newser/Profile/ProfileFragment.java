package com.example.newser.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.newser.Login.LoginActivity;
import com.example.newser.Models.User;
import com.example.newser.Models.UserAccountSettings;
import com.example.newser.Models.UserSettings;
import com.example.newser.R;
import com.example.newser.Utils.BottomNavigationViewHelper;
import com.example.newser.Utils.FirebaseMethods;
import com.example.newser.Utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private androidx.appcompat.widget.Toolbar toolbar;

    private BottomNavigationViewEx bottomNavigationViewEx;
    private ImageView profilePhoto;
    private ImageView editProfilePhoto;
    private TextView displayName;
    private TextView editDisplayName;
    private TextView email;
    private TextView editEmail;
    private TextView editUsername;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods firebaseMethods;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));
        toolbar = view.findViewById(R.id.profileToolBar);
        bottomNavigationViewEx = view.findViewById(R.id.bottomNavViewBar);
        profilePhoto = view.findViewById(R.id.profile_image);
        setupToolbar();
        editProfilePhoto = view.findViewById(R.id.edit_profile_photo);
        displayName = view.findViewById(R.id.display_name);
        editDisplayName = view.findViewById(R.id.edit_display_name);
        email = view.findViewById(R.id.email);
        editEmail = view.findViewById(R.id.edit_email);
        editUsername = view.findViewById(R.id.edit_username);
        firebaseMethods = new FirebaseMethods(getActivity());
        setupBottomNavigationView();
        setupFirebaseAuth();
        return view;
    }

    private void setProfileWidget(UserSettings usersettings) {
        User user = usersettings.getUser();
        UserAccountSettings settings = usersettings.getSettings();

        UniversalImageLoader.setImage(settings.getProfile_photo(), profilePhoto, null, "");

        displayName.setText(settings.getDisplay_name());
//        editDisplayName.setText(settings.getDisplay_name());
//
        email.setText(user.getEmail());
//        editEmail.setText(user.getEmail());
    }

    private void setupToolbar() {
        toolbar.inflateMenu(R.menu.profile_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            Log.d(TAG, "OnMenuItemClick: clicked menu item: " + item);

            switch (item.getItemId()) {
                case R.id.edit_profile:
                    EditProfileFragment editFragment = new EditProfileFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, editFragment);
                    transaction.commit();
                    break;
                case R.id.sing_out:
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
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Log.d(TAG, "onAuthStateCahgned: signed-in:" + user.getUid());
            } else {
                Log.d(TAG, "onAuthStateChanged:signed-out");

            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setProfileWidget(firebaseMethods.getUserSettings(dataSnapshot));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

