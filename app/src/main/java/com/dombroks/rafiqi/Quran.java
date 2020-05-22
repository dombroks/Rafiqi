package com.dombroks.rafiqi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dombroks.rafiqi.Adapter.rvAdapter;
import com.dombroks.rafiqi.Model.Surrah;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Quran extends AppCompatActivity {
    private ImageView goBack;
    private RecyclerView recyclerView;
    private rvAdapter adapter;
    private List<Surrah> surrahList = new ArrayList<>();
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran);
        goBack = findViewById(R.id.left);
        recyclerView = findViewById(R.id.rv);

        mDBHelper = new DatabaseHelper(this, null, null, 5);
        // String Sura = mDBHelper.laodQuery();


        adapter = new rvAdapter(mDBHelper.getSurrahs());

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quran.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
