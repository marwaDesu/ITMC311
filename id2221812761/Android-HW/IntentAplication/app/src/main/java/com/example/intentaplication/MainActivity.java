package com.example.intentaplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Button btnOpenSecond;
    TextView txtWelcome;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpenSecond = findViewById(R.id.btnOpenSecond);
        txtWelcome = findViewById(R.id.txtWelcome);

        btnOpenSecond.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, SecondIntentActivity.class);
            startActivityForResult(i, 2);  // رقم الطلب 2
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //data != null
        //لأن Android لا يضمن دائمًا أن intent سترجع، ونتفادى حدوث Crash

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            // ⭐ إضافة لطيفة
            String message = data.getStringExtra("MESSAGE");
            Toast.makeText(this, "مرحبًا بكِ " + message + " 👋", Toast.LENGTH_LONG).show();

        }
    }
}
