package com.example.newser.Utils;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.newser.Models.User;
import com.example.newser.Models.UserAccountSettings;
import com.example.newser.Models.UserSettings;
import com.example.newser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseMethods {
    private static final String TAG = "FirebaseMethods";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String userID;

    private Context mContext;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mContext = context;

        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public void updateUserAccountSettings(String displayName, String description) {
        Log.d(TAG, "Updating settings");

        if(displayName!=null) {
            myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child("display_name")
                    .setValue(displayName);
        }
        if(description!=null) {
            myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child("description")
                    .setValue(description);
        }
    }

    public void updateUsername(String username) {
        Log.d(TAG, "Updating usename");
        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);
        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);
    }

    public boolean checkIfUsernameExists(String username, DataSnapshot datSanpshot) {
        Log.d(TAG, "checkIfUsernameExists: checking if " + username + "already exists.");
        User user = new User();

        for (DataSnapshot ds : datSanpshot.child(userID).getChildren()) {
            Log.d(TAG, "checkIfUsernameExists: datasnapshot: " + ds);

            user.setUsername(ds.getValue(User.class).getUsername());

            if (StringManipulation.expandUsername(user.getUsername()).equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void registerNewEmail(final String email, String password, final String username) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, "Failure", Toast.LENGTH_LONG).show();
                        } else if (task.isSuccessful()) {
                            //send verification email
                            sendVerificationEmail();
                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: Authstate changed: " + userID);
                        }
                    }
                });
    }


    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener((new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(mContext, "couldn't send verification email.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }));
        }
    }

    public void addNewUser(String email, String username, String display_name, String description, String profile_photo) {
        User user = new User(userID, email, StringManipulation.condenseUsername(username));
        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .setValue(user);

        UserAccountSettings settings = new UserAccountSettings(
                description,
                display_name,
                profile_photo
        );
        myRef.child(mContext.getString((R.string.dbname_user_account_settings)))
                .child(userID)
                .setValue(settings);
    }

    public UserSettings getUserSettings(DataSnapshot dataSnapshot) {
        Log.d(TAG, "getUserAccountSettings: retrieving");

        UserAccountSettings settings = new UserAccountSettings();
        User user = new User();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            //user_account_settings node
            if (ds.getKey().equals(mContext.getString(R.string.dbname_user_account_settings))) {
                try {
                    settings.setDisplay_name(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getDisplay_name()
                    );
                    settings.setDescription(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getDescription()
                    );
                    settings.setProfile_photo(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getProfile_photo()
                    );
                } catch (NullPointerException e) {
                    Log.e(TAG, "getUserAccountSettings: NullPointException" + e.getMessage());
                }
            }
            //user_account_settings node
            if (ds.getKey().equals(mContext.getString(R.string.dbname_users))) {
                user.setUsername(
                        ds.child(userID)
                                .getValue(User.class)
                                .getUsername()
                );
                user.setEmail(
                        ds.child(userID)
                                .getValue(User.class)
                                .getEmail()
                );
                user.setUser_id(
                        ds.child(userID)
                                .getValue(User.class)
                                .getUser_id()
                );
            }
        }
        return new UserSettings(user, settings);
    }

    public void updateEmail(String email) {
        Log.d(TAG, "Updating usename");
        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .child("email")
                .setValue(email);
    }
}

