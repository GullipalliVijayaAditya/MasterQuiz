package com.example.android.masterquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.masterquiz.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {

    private EditText newUserName, newUserEmail, newUserAge;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        newUserName = findViewById(R.id.text_username__Updateprofile);
        newUserEmail = findViewById(R.id.text_email_Updateprofile);
        newUserAge = findViewById(R.id.text_age_Updateprofile);
        save = findViewById(R.id.button_save_Updateprofile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                com.example.android.masterquiz.UserProfile userProfile = dataSnapshot.getValue(com.example.android.masterquiz.UserProfile.class);
                newUserName.setText(userProfile.getUserName());
                newUserAge.setText(userProfile.getUserAge());
                newUserEmail.setText(userProfile.getUserEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UpdateProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = newUserName.getText().toString();
                String age = newUserAge.getText().toString();
                String email = newUserEmail.getText().toString();

                com.example.android.masterquiz.UserProfile userProfile = new com.example.android.masterquiz.UserProfile(age, email, name);

                databaseReference.setValue(userProfile);

                finish();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
