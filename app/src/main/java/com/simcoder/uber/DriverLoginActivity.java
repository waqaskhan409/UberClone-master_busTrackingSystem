package com.simcoder.uber;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverLoginActivity extends AppCompatActivity {
    private static final String TAG = "Driver";
    private TextInputEditText mEmail, mPassword;
    private Button mLogin;
    TextView mRegistration ;
    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        mAuth = FirebaseAuth.getInstance();
//        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
//        toolbar.setSubtitle("Driver's Map");
//        toolbar.inflateMenu(R.menu.menu_for_customer);

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(DriverLoginActivity.this, DriverMapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mEmail = (TextInputEditText) findViewById(R.id.iForEmail);
        mPassword = (TextInputEditText) findViewById(R.id.iForPassword);

        mLogin = (Button) findViewById(R.id.login);
        mRegistration = (TextView) findViewById(R.id.forRegistration);

        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(DriverLoginActivity.this, "Please Fill the text fields", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Log.d(TAG, "onComplete: "+ task.toString());
                                Toast.makeText(DriverLoginActivity.this, "sign up error", Toast.LENGTH_SHORT).show();
                            } else {
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id).child("name");
//                                String user_id = mAuth.getCurrentUser().getUid();
//                                DatabaseReference current_user_db1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id);
//                                current_user_db1.child("UserSignIn").setValue("signInDriver");
                                DatabaseReference current_user_db1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id);
                                current_user_db1.child("UserSignIn").setValue("signInDriver");
                                current_user_db.setValue(email);
                            }
                        }
                    });
                }
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(DriverLoginActivity.this, "Please Fill the text fields", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(DriverLoginActivity.this, "sign in error", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String user_id = mAuth.getCurrentUser().getUid();
//                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id).child("name");
//                                String user_id = mAuth.getCurrentUser().getUid();
//                                DatabaseReference current_user_db1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id);
//                                current_user_db1.child("UserSignIn").setValue("signInDriver");
                                DatabaseReference current_user_db1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user_id);
                                current_user_db1.child("UserSignIn").setValue("signInDriver");
//                                current_user_db.setValue(email);
                            }
                        }
                    });
                }

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
