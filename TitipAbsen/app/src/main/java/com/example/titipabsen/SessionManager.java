package com.example.titipabsen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    private static final String pref_name = "crudpref";
    private static final String is_login = "islogin";
    public static final String kunci_nim = "keynim";
    public static final String kunci_nama = "keynama";
    public static final String kunci_mac = "keymac";
    public static final String kunci_foto = "keyfoto";
    public static final String kunci_email = "keyemail";
    public static final String kunci_nomor = "keynomor";
    public static final boolean cek = false;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
    }

    public boolean cekLogin() {
        return cek;
    }

    public void createSession(String mac,String nama,String nim,String foto,String nomor, String email){
        editor.putBoolean(is_login, false);
        editor.putString(kunci_nim, nim);
        editor.putString(kunci_nama, nama);
        editor.putString(kunci_foto, foto);
        editor.putString(kunci_mac, mac);
        editor.putString(kunci_nomor, nomor);
        editor.putString(kunci_email, email);
        editor.putBoolean(String.valueOf(cek), true);
        editor.commit();
    }

    public void checkLogin(){
        if (this.is_login()){
            Intent i = new Intent(context, Dasboard1.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }else {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public boolean is_login() {
        return pref.getBoolean(is_login, true);
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(kunci_nim, pref.getString(kunci_nim, null));
        user.put(kunci_nama, pref.getString(kunci_nama, null));
        user.put(kunci_mac, pref.getString(kunci_mac, null));
        user.put(kunci_foto, pref.getString(kunci_foto, null));
        user.put(kunci_email, pref.getString(kunci_email, null));
        user.put(kunci_nomor, pref.getString(kunci_nomor, null));
        return user;
    }
}
