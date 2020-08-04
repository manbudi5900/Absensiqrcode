package com.example.titipabsen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Edit_Profile extends AppCompatActivity {
    AlertDialogManager alert = new AlertDialogManager();
    FirebaseDatabase  database = FirebaseDatabase.getInstance();
    EditText email ;
    EditText nomor ;
    SessionManager sessionManager;
    private ProgressDialog loading;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);
        email =  findViewById(R.id.email);
        nomor =  findViewById(R.id.nomor);


        ;
        Button save = findViewById(R.id.save);

        sessionManager = new SessionManager(getApplicationContext());
        user= sessionManager.getUserDetails();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = email.getText().toString();
                String nomor1 = nomor.getText().toString();
                if (email1.equals("")){
                    email.setError("Silahkan Email Anda Lengkapi");
                }else if(nomor1.equals("")){
                    nomor.setError("Silahkan Nomor Anda Lengkapi");
                }else{

                    updateData();;
                }

            }
        });



    }





    private void updateData() {
        database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference();
        myref.child("Mahasiswa").child(user.get(SessionManager.kunci_nim)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("NIM",email.getText().toString());
                dataSnapshot.getRef().child("email").setValue(email.getText().toString());
                dataSnapshot.getRef().child("nomor").setValue(nomor.getText().toString());
                Edit_Profile.this.finish();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });
    }
}
