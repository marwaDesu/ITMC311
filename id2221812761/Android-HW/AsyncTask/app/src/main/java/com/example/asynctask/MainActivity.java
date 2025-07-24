package com.example.asynctask;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnprocess;
    ProgressBar progressBar;
    TextView txtpercentage;
    TextView txtTimer; // إضافة عنصر لعرض الزمن
    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnprocess = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressbar);
        txtpercentage = findViewById(R.id.txtpercentage);
        txtTimer = findViewById(R.id.txtTimer); // الربط بعنصر XML

        btnprocess.setOnClickListener(v -> {
            btnprocess.setEnabled(false);
            new DoingAsyncTask().execute();
        });
    }

    //  ملاحظة: AsyncTask لم يعد يُوصى باستخدامه في SDK 30+ وتمت إزالته لاحقًا.
    // في التطبيقات الحقيقية نستخدم Executors أو Handler.
    private class DoingAsyncTask extends AsyncTask<Void, Integer, Void> {
        int progress_status;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "onPreExecute(): بدأت المعالجة", Toast.LENGTH_SHORT).show();
            progress_status = 0;
            startTime = SystemClock.elapsedRealtime(); // بداية المؤقت
            txtpercentage.setText("Processing 0%");
            txtTimer.setText("Time: 0 sec");
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (progress_status < 100) {
                progress_status += 5;
                publishProgress(progress_status);
                SystemClock.sleep(200); // محاكاة تأخير
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            txtpercentage.setText("Processing " + values[0] + "%");

            long elapsed = (SystemClock.elapsedRealtime() - startTime) / 1000;
            txtTimer.setText("Time: " + elapsed + " sec");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(MainActivity.this, "onPostExecute(): انتهت المعالجة", Toast.LENGTH_SHORT).show();
            txtpercentage.setText("Processing complete");
            btnprocess.setEnabled(true);
        }
    }
}
