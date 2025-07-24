package com.example.broadcastreceiver;

import android.app.Activity;
import android.content.Context;
import android.os.BatteryManager;
import android.os.Bundle;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent i) {
            int level = i.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int status = i.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int temperature = i.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1); // in 0.1°C

            ProgressBar pb = findViewById(R.id.progressbar);
            TextView tv = findViewById(R.id.textfield);

            pb.setProgress(level);

            //استفدت من المعلومات الأخرى التي توفرها intent وقمت بعرضها
            //مثلاً status هي حالة الشحن مثل (charging – full – discharging – unknown)
            // وtemperature هي حرارة البطارية ب 0.1 درجة مئوية
            String chargingStatus;
            switch (status) {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    chargingStatus = "Charging";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    chargingStatus = "Full";
                    break;
                default:
                    chargingStatus = "Not Charging";
            }

            float tempC = temperature / 10.0f; //القسمة هنا لأننا أردنا أن يتم التحويل إلى 1 درجة مئوية بدل 0.1

            //  تنسيق العرض في TextView
            tv.setText("🔋 Battery Level: " + level + "%\n" +
                    "⚡ Status: " + chargingStatus + "\n" +
                    "🌡️ Temp: " + tempC + "°C");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // تسجيل الـ BroadcastReceiver
        registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBatInfoReceiver); // منع تسرب الذاكرة
    }
}

