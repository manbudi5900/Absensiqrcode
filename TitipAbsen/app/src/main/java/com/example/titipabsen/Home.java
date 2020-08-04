package com.example.titipabsen;

import android.Manifest;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.titipabsen.model.ResObj;
import com.example.titipabsen.util.api.BaseApiService;
import com.example.titipabsen.util.api.UtilsApi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannner;
    Button button;
    String mac,nama,nim,foto;
    BaseApiService userService;
    SessionManager sessionManager;
    private static final int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 1;
    DatabaseReference myRef1;
    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String nama = user.get(SessionManager.kunci_nama);
        String foto = user.get(SessionManager.kunci_foto);
        String nim = user.get(SessionManager.kunci_nim);
        TextView nama1 = (TextView) findViewById(R.id.nama);
        TextView nama2 = (TextView) findViewById(R.id.namap);
        TextView nim1 = (TextView) findViewById(R.id.nim);
        TextView nomor = (TextView) findViewById(R.id.hp);
        TextView email = (TextView) findViewById(R.id.email);
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        myRef1 = database1.getReference("Absen");
        userService= UtilsApi.getAPIService();
        ImageView imageView = (ImageView)findViewById(R.id.foto);



        Call<ResObj> call = userService.login(nim);
        call.enqueue(new Callback<ResObj>() {

            @Override
            public void onResponse(Call<ResObj> call, Response<ResObj> response) {

                if (response.isSuccessful()) {
                    ResObj resobj = response.body();

                    Glide.with(Home.this)
                            // LOAD URL DARI INTERNET
                            .load(resobj.getFoto()+"/RjFEMDE2MDE4OkJVRElUR0IxMjM=")
                            //. LOAD GAMBAR SAAT TERJADI KESALAHAN MEMUAT GMBR UTAMA
                            .error(R.drawable.fail)
                            .into(imageView);


                }
            }

            @Override
            public void onFailure(Call<ResObj> call, Throwable t) {
                Log.d("gagal","gagal");
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Mahasiswa");
        myRef.child(nim).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Mahasiswa post = dataSnapshot.getValue(Mahasiswa.class);

                    nomor.setText(post.getNomor());
                    email.setText(post.getEmail());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });


//        Call<ResObj> call = userService.login(nim);
//        call.enqueue(new Callback<ResObj>() {
//            @Override
//            public void onResponse(Call<ResObj> call, Response<ResObj> response) {
//                if (response.isSuccessful()) {
//                    ResObj resobj = response.body();
//                    Glide.with(Home.this)
//                            // LOAD URL DARI INTERNET
//                            .load(resobj.getFoto())
//
// LOAD GAMBAR AWAL SEBELUM GAMBAR UTAMA MUNCUL, BISA DARI LOKAL DAN INTERNET
//                            .placeholder(R.drawable.bg_circle)
//                            //. LOAD GAMBAR SAAT TERJADI KESALAHAN MEMUAT GMBR UTAMA
//                            .error(R.drawable.camera)
//                            .into(foto1);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResObj> call, Throwable t) {
//
//            }
//        });
//
        nama1.setText(nama);
        nama2.setText(nama);
        nim1.setText(nim);


    }

    public void scan(View view) {
        generate();
    }
    private void generate(){
        getPermissions();
        mScannner =new ZXingScannerView(this);
            setContentView(mScannner);
            mScannner.setResultHandler(this);
            mScannner.startCamera();
    }


    @Override
    protected  void onPause() {
        super.onPause();

        finish();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Home.this, Dasboard1.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_GET_ACCOUNTS: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(Home.this, "Permission denied to get Account", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void getPermissions() {
        /* Check and Request permission */
        if (ContextCompat.checkSelfPermission(Home.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Home.this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_GET_ACCOUNTS);
        }


    }
    private String getTanggal() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getWaktu() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    @Override
    public void handleResult(Result result) {
        HashMap<String, String> user = sessionManager.getUserDetails();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1= database.getReference("History");
        String text,format;
        text=result.getText().toString();
        format=result.getBarcodeFormat().toString();
        String []tampung=text.split("/");
        String tampung1=tampung[0]; //Kode matkul
        String tampung2=tampung[1];//kode Dosen
        String tampung3=tampung[2];//kode bulan
        String tampung4=tampung[3];//kode tanggal
        if(!tampung1.equals("") && !tampung2.equals("") && !tampung3.equals("") && !tampung4.equals("")) {
            DatabaseReference newPostRef1 = myRef1.child(user.get(SessionManager.kunci_nim)).child(getTanggal()).child(tampung1);
            newPostRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        newPostRef1.setValue(new History(user.get(SessionManager.kunci_nama), getTanggal(), user.get(SessionManager.kunci_nim), tampung1,getWaktu()));
                        Home.this.finish();
                    } else {
                        showDialog();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

            DatabaseReference myRef= database.getReference("Absen");
            DatabaseReference newPostRef = myRef.child(tampung1).child(tampung2).child(tampung3).child(tampung4).child(user.get(SessionManager.kunci_mac));
            newPostRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        newPostRef.setValue(new Absen(user.get(SessionManager.kunci_mac), user.get(SessionManager.kunci_nama), getTanggal() + " " + getWaktu(), user.get(SessionManager.kunci_nim)));
                        Home.this.finish();
                    } else {
                        showDialog();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
    }
    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Keluar dari aplikasi?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Device Sudah Digunakan!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setNegativeButton("Close",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Home.this.finish();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

}
