package com.example.titipabsen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.titipabsen.model.ResObj;
import com.example.titipabsen.util.api.BaseApiService;

import com.example.titipabsen.util.api.UtilsApi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roger.catloadinglibrary.CatLoadingView;


import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;


import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.net.NetworkInterface.getNetworkInterfaces;

public class MainActivity extends AppCompatActivity {



    EditText user,pass;
    String usern;
    DatabaseReference  database;
    Button login;
    private ProgressDialog loading;
    CatLoadingView mView;
    BaseApiService userService;
    DatabaseReference myRef;
    View v;
    AlertDialogManager alert = new AlertDialogManager();
    SessionManager sessionManager;
    final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)  ;
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplicationContext());

        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        userService=UtilsApi.getAPIService();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Mahasiswa");
//        HashMap<String, String> user1 = sessionManager.getUserDetails();
//        sessionManager.checkLogin();


        // Read from the database
        login.setOnClickListener((view)->{
            String username = user.getText().toString();
            String password = pass.getText().toString();

            if (username.equals("")){
                user.setError("Silahkan Username Anda Lengkapi");
            }else if(password.equals("")){
                pass.setError("Silahkan Password Anda Lengkapi");
            }else{
                loading = ProgressDialog.show(MainActivity.this,
                        null,
                        "Please Wait",
                        true,
                        false);
                doLogin(username,password);
            }
        });
    }



    public String check() {
        Enumeration<NetworkInterface> networks = null;
        StringBuilder sb = new StringBuilder();
        try {
            networks = getNetworkInterfaces();
            NetworkInterface inter;
            while (networks.hasMoreElements()) {
                inter = networks.nextElement();
                byte[] mac = inter.getHardwareAddress();

                if (mac != null) {
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));

                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }

                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return sb.toString();


    }
    private void doLogin(final String username,final String password){
        String mac=check();
        Call<ResObj> call = userService.login(username);
        call.enqueue(new Callback<ResObj>() {
            @Override
            public void onResponse(Call<ResObj> call, Response<ResObj> response) {
                if (username.substring(0,3).equals("F1D")) {
                    myRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                if (response.isSuccessful()) {
                                    ResObj resobj = response.body();
                                    if (resobj.getNIM().equalsIgnoreCase(username) && resobj.getNIM().equalsIgnoreCase(password)) {
                                        DatabaseReference newPostRef = myRef.child(username);
                                        newPostRef.setValue(new Mahasiswa(resobj.getNama(),resobj.getNIM(),resobj.getFoto(),resobj.getNIM(),"",""));
                                        sessionManager.createSession(mac,resobj.getNama(),resobj.getNIM(),resobj.getFoto(),"","");
                                        masuk(mac,resobj.getNama(),resobj.getNIM(),resobj.getFoto());
                                    } else {

                                        alert.showAlertDialog(MainActivity.this, "Login failed..", "Wrong", false);
                                        loading.dismiss();
                                    }
                                } else {

                                    alert.showAlertDialog(MainActivity.this, "Login failed..", "No Detect", false);
                                    loading.dismiss();
                                }
                            }else {
                                Mahasiswa post = dataSnapshot.getValue(Mahasiswa.class);
                                if(post.getNim().equals(username) && post.getPassword().equals(password)){
                                    sessionManager.createSession(mac,post.getNama(),post.getNim(),post.getFoto(),post.nomor,post.email);
                                    masuk(mac,post.getNama(),post.getNim(),post.getFoto());
                                }
                                else{
                                    alert.showAlertDialog(MainActivity.this, "Login failed..", "username or password is wrong", false);
                                    loading.dismiss();
                                }

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                }else {
                    alert.showAlertDialog(MainActivity.this, "Login failed..", "The Mobile for Informatic Student Only", false);
                    loading.dismiss();
                }
            }



            @Override
            public void onFailure(Call<ResObj> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void masuk(String mac, String nama, String nim, String foto) {
        Intent i = new Intent(this, Dasboard1.class);
        startActivity(i);
        loading.dismiss();
    }
    boolean twice;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();,

        Log.d(TAG,"click");
        if (twice==true){
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            System.exit(0);
        }

        Log.d(TAG,"twice ="+twice);
        Toast.makeText(MainActivity.this,"Please Press Back again to Exit ",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice=false;
                Log.d(TAG,"twice ="+twice);
            }
        },3000);
        twice=true;

    }
}



