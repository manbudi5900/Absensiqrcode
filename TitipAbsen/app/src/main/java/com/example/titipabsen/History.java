package com.example.titipabsen;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class History {
    String nama, tanggal, nim,kodem,jam;


    public History(String nama, String tanggal, String nim,String kodem, String jam ) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.nim = nim;
        this.kodem = kodem;
        this.jam=jam;
    }
    public History() {

    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getKodem() {
        return kodem;
    }

    public void setKodem(String kodem) {
        this.kodem = kodem;
    }
}
