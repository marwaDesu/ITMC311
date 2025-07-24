package com.example.handlermessage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Bundle;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ProgressBar bar1, bar2;
    TextView msgWorking, msgReturned;
    Button btnStartThread; // زر لتشغيل الخيط يدويًا
    boolean isRunning = false;
    final int MAX_SEC = 60;

    String strTest = "global value seen by all threads ";
    int intTest = 0;

    /* msg -> { ... }
هذه هي صيغة Lambda Expression في Java
    وتُستخدم لتبسيط كتابة الـ anonymous classes أو الدوال المجهولة
     ننشئ كائن Handler ونمرر له كائن Callback مكتوب بصيغة lambda، والذي يجب أن يطبّق الدالة
    msg هنا يوجد معامل في الدالة وهو
    ولكن في حال عدم وجود معامل نضع ()*/
    Handler handler = new Handler(msg -> {
        String returnedValue = (String) msg.obj;
        msgReturned.setText("Returned by background thread:\n\n" + returnedValue);
        bar1.incrementProgressBy(2);

        if (bar1.getProgress() == MAX_SEC) {
            msgReturned.setText("Done \n Background thread has been stopped");
            isRunning = false;
        }

        if (bar1.getProgress() == bar1.getMax()) {
            msgWorking.setText("Done");
            bar1.setVisibility(View.INVISIBLE);
            bar2.setVisibility(View.INVISIBLE);
        } else {
            msgWorking.setText("Working... " + bar1.getProgress());
        }

        return true;
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar1 = findViewById(R.id.progress);
        bar2 = findViewById(R.id.progress2);
        bar1.setMax(MAX_SEC);
        bar1.setProgress(0);
        msgWorking = findViewById(R.id.TextView01);
        msgReturned = findViewById(R.id.TextView02);

        btnStartThread = findViewById(R.id.btnStartThread); //  ربط الزر بالكود
        btnStartThread.setOnClickListener(v -> startBackgroundThread()); //  الضغط ينفذ الخيط

        strTest += "-01";
        intTest = 1;
    }

    @Override
    public void onStop() {
        super.onStop();
        isRunning = false;
    }
    //  بدل onStart، وضعنا خيط الخلفية في دالة مستقلة لتُنفذ المهمة عند الضغط على الزر
    private void startBackgroundThread() {
        isRunning = true;

        Thread background = new Thread(() -> {
            try {
                for (int i = 0; i < MAX_SEC && isRunning; i++) {
                    Thread.sleep(1500);
                    Random rnd = new Random();
                    String data = "Thread Value: " + rnd.nextInt(101);
                    data += "\n" + strTest + " " + intTest;
                    intTest++;
                    Message msg = handler.obtainMessage(1, data);
                    if (isRunning) {
                        handler.sendMessage(msg);
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        });
        background.start();
    }
}

