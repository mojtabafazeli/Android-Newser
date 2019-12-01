package com.example.newser.Login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newser.R;
import com.example.newser.Utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private Context mContext;
    private String email, username, password;
    private EditText mEmail,mPassword,mUsername;
    private Button btnRegister;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private String append = "";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mContext = SignupActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);

        initWidgets();
        setupFirebaseAuth();
        init();

    }

    private void init() {
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                username = mUsername.getText().toString();
                password = mPassword. getText().toString();

                if (checkInputs(email, username, password)) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    firebaseMethods.registerNewEmail(email,password,username);
                }
            }
        });
    }

    private boolean checkInputs(String email, String username, String password){
        Log.d(TAG, "checkInputs: checking inputs for null values.");
        if (email.equals("") || username.equals("") || password.equals("")){
            Toast.makeText(mContext, "All fields must be filled out.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private void initWidgets() {
        Log.d(TAG, "initWidget: Initializing Widgets.");
        mProgressBar= findViewById(R.id.progressbar);
        mProgressBar.setVisibility(View.GONE);
        mEmail =  findViewById(R.id.input_email);
        mUsername = findViewById(R.id.input_name);
        mPassword = findViewById(R.id.input_password);
        btnRegister = findViewById(R.id.btn_signup);
        mContext = SignupActivity.this;
    }

    private boolean isStringNull(String string) {
        if (string.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private void setupFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateCahgned: signed-in:" + user.getUid());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //1st check: Make sure the username is not already in use
                            if (firebaseMethods.checkIfUsernameExists(username, dataSnapshot)){
                                append = myRef.push().getKey().substring(3,10);
                            }
                            username = username + append;
                            //add new user to datbase
                            firebaseMethods.addNewUser(email,username,"", "","");
                            Log.d(TAG, "signup success");
                            Toast.makeText(mContext,"signup successful.",Toast.LENGTH_LONG).show();
                            //ad new user_account_settings to the database
                             mAuth.signOut();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    finish();
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed-out");
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