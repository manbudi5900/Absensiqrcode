package com.example.titipabsen.util.api;

import com.example.titipabsen.model.ResObj;



import retrofit2.Call;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @Headers("authorization: Basic RjFEMDE2MDE4OkJVRElUR0IxMjM=")
    @GET("api/Mahasiswa/Profil")
    Call<ResObj> login(@Query("nim") String nim);


    @Headers("authorization: Basic RjFEMDE2MDE4OkJVRElUR0IxMjM=")
    @GET("foto/mahasiswa/{nim}/300/300/")
    Call<ResObj> profile(@Query("nim") String nim);


}
