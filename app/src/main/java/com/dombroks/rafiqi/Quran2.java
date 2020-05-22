package com.dombroks.rafiqi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Quran2 extends AppCompatActivity {
    private TextView content;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran2);
        helper = new DatabaseHelper(this, null, null, 5);

        Intent intent = getIntent();

        content = findViewById(R.id.content);
        String id = intent.getStringExtra("item");
        content.setText(helper.getContentById(id));

    }
}
