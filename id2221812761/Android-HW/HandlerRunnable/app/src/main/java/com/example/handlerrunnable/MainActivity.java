package com.example.handlerrunnable;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    ProgressBar myBar;
    TextView lblTopCaption;
    EditText txtBox1;
    Button btnDoSomething;
    int accum = 0;
    final int MAX = 100;  // تم تحديده لتسهيل إعادة الاستخدام
    long startingMills = System.currentTimeMillis();
    String PATIENCE = "Some important data is being collected now. \nPlease be patient.";
    Handler myHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lblTopCaption = findViewById(R.id.lblTopCaption);
        myBar = findViewById(R.id.myBar);
        myBar.setMax(MAX);
        txtBox1 = findViewById(R.id.txtBox1);
        btnDoSomething = findViewById(R.id.btnDoSomething);

        // زر الواجهة
        btnDoSomething.setOnClickListener(v -> {
            Editable txt = txtBox1.getText();
            Toast.makeText(getBaseContext(), "You said >> " + txt, Toast.LENGTH_LONG).show();
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        // بدء الخيط الخلفي
        Thread myThread1 = new Thread(backgroundTask, "backAlias1");
        myThread1.start();
        myBar.setProgress(0);
        accum = 0;
    }

    // تحديث الواجهة
    private final Runnable foregroundTask = () -> {
        try {
            int progressStep = 5;
            long secondsPassed = (System.currentTimeMillis() - startingMills) / 1000;
            // تحديث النص ليشمل الوقت المتبقي - هذه هي الإضافة <<
            long secondsRemaining = (MAX - accum) / progressStep;

            lblTopCaption.setText(
                    PATIENCE + "\nElapsed sec: " + secondsPassed +
                            "\nRemaining approx.: " + secondsRemaining + " sec");

            myBar.incrementProgressBy(progressStep);
            accum += progressStep;

            if (accum >= myBar.getMax()) {
                lblTopCaption.setText("Background work is OVER!");
                myBar.setVisibility(View.INVISIBLE);
            }
        } catch (Exception ignored) {}
    };

    // تنفيذ العمل في الخلفية
    private final Runnable backgroundTask = () -> {
        try {
            for (int n = 0; n < 20; n++) {
                Thread.sleep(1000);
                myHandler.post(foregroundTask); // تمرير المهمة للتنفيذ في الثريد الأمامي
            }
        } catch (InterruptedException ignored) {}
    };
}