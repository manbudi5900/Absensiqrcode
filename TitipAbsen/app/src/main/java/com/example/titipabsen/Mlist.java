package com.example.titipabsen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Mlist extends AppCompatActivity {
    private RecyclerView rvView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference database;
    FirebaseDatabase FDB;
    AdapterHistoryRecyclerView adapter;
    List<History> listData;
    SessionManager sessionManager;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        rvView = findViewById(R.id.list);
        layoutManager =new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
        rvView.setItemAnimator(new DefaultItemAnimator());
        rvView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        database = FirebaseDatabase.getInstance().getReference();
        sessionManager = new SessionManager(getApplicationContext());
        user = sessionManager.getUserDetails();
        String nim = user.get(SessionManager.kunci_nim);
        listData = new ArrayList<>();
        adapter = new AdapterHistoryRecyclerView(listData);
        FDB = FirebaseDatabase.getInstance();
        getDataFirebase(nim);


    }

    void getDataFirebase(String nim){
        Log.d("budi",getWaktu());
        database = FDB.getReference("History").child(nim).child(getWaktu());
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                History data = dataSnapshot.getValue(History.class);
                listData.add(data);
                rvView.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("Budichange",nim);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Budiremove",nim);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Budicancel",nim);
            }
        });
    }


    private String getWaktu() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
}



class AdapterHistoryRecyclerView extends RecyclerView.Adapter<AdapterHistoryRecyclerView.Viewholder> {

    List listArray;

    public AdapterHistoryRecyclerView(List listArray) {
        this.listArray = listArray;
    }


    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absen,parent,false);
        return new Viewholder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull AdapterHistoryRecyclerView.Viewholder viewholder, int i) {
        History history1 = (History) listArray.get(i);
        viewholder.nim.setText(history1.getNim());
        viewholder.matkul.setText(history1.getKodem());
        viewholder.jam.setText(history1.getJam());
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView nim;
        TextView jam;
        TextView matkul;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            nim = itemView.findViewById(R.id.person_name);
            matkul = itemView.findViewById(R.id.matkul);
            jam = itemView.findViewById(R.id.jam);
        }
    }

    @Override
    public int getItemCount() {
        return listArray.size();
    }
}