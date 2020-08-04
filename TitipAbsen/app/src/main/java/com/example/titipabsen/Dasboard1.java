package com.example.titipabsen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class Dasboard1 extends AppCompatActivity {
    final String TAG = this.getClass().getName();
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard1);

        sessionManager = new SessionManager(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.logout:
                sessionManager.logout();

                return true;

        }
        return false;
    }


    public void pProfile(View view) {
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }
    public void logout(View view){
        sessionManager.logout();
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
        twice=true;
        Toast.makeText(Dasboard1.this,"Please Press Back again to Exit ",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice=false;
                Log.d(TAG,"twice ="+twice);
            }
        },3000);

    }

    public void editpass(View view) {
        Intent i = new Intent(this, edit_password.class);
        startActivity(i);
    }
    public void EditProfile(View view) {
        Intent i = new Intent(this, Edit_Profile.class);
        startActivity(i);
    }
    public void history(View view) {
        Intent i = new Intent(this, Mlist.class);
        startActivity(i);
    }
}
