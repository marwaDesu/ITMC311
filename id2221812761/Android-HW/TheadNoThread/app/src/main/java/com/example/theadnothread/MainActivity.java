package com.example.theadnothread;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // ❗ زر ينفذ العملية الطويلة على Main Thread ➤ سيجمد التطبيق مؤقتًا
    public void runOnMainThread(android.view.View view) {
        Toast.makeText(this, "بدأ التنفيذ في Main Thread", Toast.LENGTH_SHORT).show();

        long endTime = System.currentTimeMillis() + 20000;

        while (System.currentTimeMillis() < endTime) {
            //وظيفة synchronized هي منع أكثر من Thread من الوصول لجزء من الكود
            synchronized (this) {
                try {
                    wait(endTime - System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ✅ زر ينفذ نفس العملية في Thread خلفي ➤ لن يتجمد التطبيق
    public void runOnBackgroundThread(android.view.View view) {
        Toast.makeText(this, "بدأ التنفيذ في Background Thread", Toast.LENGTH_SHORT).show();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long endTime = System.currentTimeMillis() + 20000;

                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime - System.currentTimeMillis());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}