package com.example.titipabsen;

public class Mahasiswa {
    String nama;
    String nim;
    String foto;
    String password;
    String email;
    String nomor;

    public Mahasiswa(String nama, String nim, String foto,String pass,String email, String nomor){
        this.nama=nama;
        this.nim=nim;
        this.foto=foto;
        this.password=pass;
        this.email=email;
        this.nomor=nomor;
    }
    public Mahasiswa(){
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

