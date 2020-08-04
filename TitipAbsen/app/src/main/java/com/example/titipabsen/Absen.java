package com.example.titipabsen;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Absen {
    String nama, tanggal, nim,mac;


    public Absen(String mac,String nama, String tanggal, String nim){
        this.nama = nama;
        this.mac = mac;
        this.tanggal = tanggal;
        this.nim = nim;
    }
    public Absen(){
    }




}
