package com.example.newser.Profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.newser.Models.User;
import com.example.newser.Models.UserAccountSettings;
import com.example.newser.Models.UserSettings;
import com.example.newser.Utils.FirebaseMethods;
import com.example.newser.Utils.UniversalImageLoader;
import com.example.newser.R;
import com.example.newser.dialogs.ConfirmPasswordDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class EditProfileFragment extends Fragment implements ConfirmPasswordDialog.OnConfirmPasswordListener {
    private static final String TAG = "ProfileActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods firebaseMethods;

    //EditProfile Fragment Widgets
    private EditText editUsername, editDisplayName, editDescription, editEmail, editPhoneNumber;
    private TextView changeProfileView;
    private ImageView editProfilePhoto, saveButton;
    private String userID;
    private UserSettings mUserSettings;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        editProfilePhoto = view.findViewById(R.id.edit_profile_photo);
        editUsername = view.findViewById(R.id.edit_username);
        editDescription = view.findViewById(R.id.edit_description);
        editEmail = view.findViewById(R.id.edit_email);
        editPhoneNumber = view.findViewById(R.id.edit_phoneNumber);
        editDisplayName = view.findViewById(R.id.edit_display_name);
        firebaseMethods = new FirebaseMethods(getActivity());


        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));

        setupFirebaseAuth();

        ImageView backArrow = view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment fragment = new ProfileFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });

        saveButton = view.findViewById(R.id.saveChanges);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileSettings();
            }
        });
        return view;
    }

    private void saveProfileSettings() {
        final String userName = editUsername.getText().toString();
        final String displayName = editDisplayName.getText().toString();
        final String description = editDescription.getText().toString();
        final String email = editEmail.getText().toString();

        //case1: the user did not change their username;
        if (!mUserSettings.getUser().getUsername().equals(userName)) {
            checkIfUsernameExists(userName);
        }
        if (!mUserSettings.getUser().getEmail().equals(email)) {
            // setp1)reauthenticate
            //        -confirm the password and email
            ConfirmPasswordDialog dialog = new ConfirmPasswordDialog();
            dialog.show(getFragmentManager(), getString(R.string.confirm_password_dialog));
            dialog.setTargetFragment(EditProfileFragment.this, 1);
            // step2) chech if the email is already registered
            //        -fetchProvidersFromEmail(String email)
            // step3) change the email
            //          -submit
        }

        if(!mUserSettings.getSettings().getDisplay_name().equals(displayName)){
            firebaseMethods.updateUserAccountSettings(displayName,null);
        }
        if(!mUserSettings.getSettings().getDescription().equals(description)){
            firebaseMethods.updateUserAccountSettings(null,description);
        }

    }

    private void checkIfUsernameExists(final String username) {
        Log.d(TAG, "CHECKNIG IF THE USER NAME EXISTS" + username);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    firebaseMethods.updateUsername(username);
                    Toast.makeText(getActivity(), "Username changed", Toast.LENGTH_LONG).show();
                }

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    if (singleSnapshot.exists()) {
                        Log.d(TAG, "checkIfusernameExists: ");
                        Toast.makeText(getActivity(), "that username already exists.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setProfileWidget(UserSettings usersettings) {
        User user = usersettings.getUser();

        mUserSettings = usersettings;
        UserAccountSettings settings = usersettings.getSettings();

        UniversalImageLoader.setImage(settings.getProfile_photo(), editProfilePhoto, null, "");

        editDisplayName.setText(settings.getDisplay_name());
//        editDisplayName.setText(settings.getDisplay_name());
//
        editEmail.setText(usersettings.getUser().getEmail());
//        editEmail.setText(user.getEmail());

        editUsername.setText(usersettings.getUser().getUsername());
//        editEmail.setText(user.getEmail());

        editDescription.setText(usersettings.getSettings().getDescription());
//        editEmail.setText(user.getEmail());

        editEmail.setText(user.getEmail());
//        editEmail.setText(user.getEmail());

    }

    //        setupFirebaseAuth();
    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        userID = mAuth.getCurrentUser().getUid();
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

    @Override
    public void onConfirmPassword(String password) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), password);
        mAuth.getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "user re-authenticate");
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User re-authenticated");
                            mAuth.fetchSignInMethodsForEmail(editEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    if (task.isSuccessful()) {
                                        try {
                                            if (task.getResult().getSignInMethods().size() == 1) {
                                                Toast.makeText(getActivity(), "That email is already in use", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Log.d(TAG, "onComplete: That email is available.");
                                                mAuth.getCurrentUser().updateEmail(editEmail.getText().toString())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Log.d(TAG, "User email address updated.");
                                                                    Toast.makeText(getActivity(), "email updated", Toast.LENGTH_SHORT).show();
                                                                    firebaseMethods.updateEmail(editEmail.getText().toString());
                                                                }
                                                            }
                                                        });
                                            }
                                        } catch (NullPointerException e) {
                                            Log.e(TAG, "onComplete: NullPointerException: " + e.getMessage());
                                        }
                                    }else {
                                        Log.d(TAG, "User re-authentication failed");
                                    }
                                }
                            });
                        }
                    }
                });
    }
}