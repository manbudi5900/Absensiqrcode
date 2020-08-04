package com.example.titipabsen;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class edit_password extends AppCompatActivity {
    SessionManager sessionManager;
    private ProgressDialog loading;
    HashMap<String, String> user;
    EditText passL,passB,passBK ;
    FirebaseDatabase  database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        passL =  findViewById(R.id.passL);
        passB =  findViewById(R.id.passB);
        passBK =  findViewById(R.id.passBK);


        ;
        Button save = findViewById(R.id.saveP);

        sessionManager = new SessionManager(getApplicationContext());
        user= sessionManager.getUserDetails();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passL1 = passL.getText().toString();
                String passB1 = passB.getText().toString();
                String passBK1 = passBK.getText().toString();
                if (passL1.equals("")){
                    passL.setError("Silahkan Email Anda Lengkapi");
                }else if(passB1.equals("")){
                    passB.setError("Silahkan Nomor Anda Lengkapi");
                } else if(passBK1.equals("")){
                    passBK.setError("Silahkan Nomor Anda Lengkapi");
                }else{
                    updateData();;
                }

            }
        });
    }

    private void updateData() {
        String passL1 = passL.getText().toString();
        String passB1 = passB.getText().toString();
        String passBK1 = passBK.getText().toString();
        database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference();
        myref.child("Mahasiswa").child(user.get(SessionManager.kunci_nim)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Mahasiswa post = dataSnapshot.getValue(Mahasiswa.class);
                if (passL1.equals(post.getPassword())){
                    if (passB1.equals(passBK1)){
                        dataSnapshot.getRef().child("password").setValue(passB1);
                        edit_password.this.finish();
                    }else{
                        Toast.makeText(edit_password.this, "Password Baru Tidak SEsuai Denagn Konfirmasi", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(edit_password.this, "Password Anda Tidak sesuai", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });
    }

}
