package com.simcoder.uber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button mDriver, mCustomer;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDriver = (Button) findViewById(R.id.driver);
        mCustomer = (Button) findViewById(R.id.customer);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
if(user != null) {
    final ProgressDialog dialog = ProgressDialog.show(this, "Login Current User",
            "Loading. Please wait...", true);
    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(user.getUid()).child("UserSignIn");
    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            try {


                String string = dataSnapshot.getValue().toString();
                if (string.equals("signInDriver")) {
                    Intent intent = new Intent(MainActivity.this, DriverMapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                } else if (string.equals("signInPatient")) {

                    Intent intent = new Intent(MainActivity.this, CustomerMapActivity.class);
                    startActivity(intent);
                    finish();
                    return;

                }

            }catch (NullPointerException e){
                Toast.makeText(MainActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });


}

    startService(new Intent(MainActivity.this, onAppKilled.class));
    mDriver.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, DriverLoginActivity.class);
            startActivity(intent);

        }
    });

    mCustomer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, CustomerLoginActivity.class);
            startActivity(intent);
        }
    });

    }
}
