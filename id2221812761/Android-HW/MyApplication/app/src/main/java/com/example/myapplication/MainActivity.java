package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button a1, a2;
    Button resetBtn;//تعريف متغير من نوع Button لتصفير النقاط
    TextView t1;
    int num1, num2, p = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* لم نحتاج لCasting لأننا نستخدم Android API 26 أو أحدث،
        والـ findViewById الآن يُعيد النوع الصحيح تلقائيًا
         */
        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        t1 = findViewById(R.id.p);
        //جلب الزر من الواجهة لاستخدامه في تصفير النقاط وإظهار رسالة مؤقتة قصيرة تعلم المستخدم بالحدث
        resetBtn = findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = 0;
                t1.setText("Points: " + p);
                Toast.makeText(MainActivity.this, "Points Reset", Toast.LENGTH_SHORT).show();
            }
        });
        roll();
        /*    هنا جعلنا الزر يملك Listener بواسطة دالة setOnClickListener()
        من ثم أنشأنا كائن من  Anonymous Class التي تقوم بتنفيذ دالة onClick()
        من الواجهة  View.OnClickListener
         */
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fun(num1, num2);
            }
        });

        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun(num2, num1);

            }
        });

    }

    void fun(int q1, int q2) {
        if (q1 > q2) {

            p++;
            Toast.makeText(MainActivity.this, " correct", Toast.LENGTH_SHORT).show();

        } else {
            p--;
            Toast.makeText(MainActivity.this, " wrong", Toast.LENGTH_SHORT).show();

        }
        t1.setText("Points: " + p);
        roll();
    }

    void roll() {
        Random random = new Random();
        num1 = random.nextInt(9);
        do {
            num2 = random.nextInt(9);
        } while (num1 == num2);
        a1.setText(num1 + "");
        a2.setText(num2 + "");
    }
}