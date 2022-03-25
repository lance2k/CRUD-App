package com.example.morales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnCreate, btnRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createActivity();
            }
        });

        btnRUD = findViewById(R.id.btnRUD);
        btnRUD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rudActivity();
            }
        });
    }
    public void createActivity(){
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }
    public void rudActivity(){
        Intent intent = new Intent(this, RUDActivity.class);
        startActivity(intent);
    }

}