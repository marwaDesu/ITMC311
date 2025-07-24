package com.example.intentaplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
public class SecondIntentActivity extends AppCompatActivity {

    EditText inputName;
    Button btnSendBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_intent_xml); // ملف XML الثاني (يتم إنشاؤه تلقائيًا عند إضافة الـ Activity)

        inputName = findViewById(R.id.nameInput);
        btnSendBack = findViewById(R.id.submitBtn);

        btnSendBack.setOnClickListener(v -> {
            // ⭐ إضافة لطيفة
            String message = inputName.getText().toString().trim();
            Intent i = new Intent();
            i.putExtra("MESSAGE", message);
            setResult(Activity.RESULT_OK, i);
            finish(); // الخروج من SecondActivity والعودة إلى MainActivity
        });
    }
}
