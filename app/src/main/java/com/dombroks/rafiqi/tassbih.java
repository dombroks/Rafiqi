package com.dombroks.rafiqi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class tassbih extends AppCompatActivity {
    private LinearLayout plusLinearLayout;
    private int index;
    private TextView counter;
    private ImageView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tassbih);

        plusLinearLayout = findViewById(R.id.linearLayout);
        goBack = findViewById(R.id.left);
        counter = findViewById(R.id.index);
        index = 0;
        counter.setText(String.valueOf(index));

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tassbih.this, MainActivity.class);
                startActivity(intent);
            }
        });


        plusLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                counter.setText(String.valueOf(index));
                if (index == 34) {
                    index = 0;
                    counter.setText(String.valueOf(index));

                }
            }
        });



    }

}
