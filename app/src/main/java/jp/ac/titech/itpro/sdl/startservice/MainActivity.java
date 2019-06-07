package jp.ac.titech.itpro.sdl.startservice;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private BroadcastReceiver scanReceiver;
    private IntentFilter scanFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate in " + Thread.currentThread());
        setContentView(R.layout.activity_main);

        scanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d(TAG, "onReceive: " + action);
                if (action == null) {
                    return;
                }
                switch (action) {
                    case Service3.END_SERVICE:
                        colorChange(Color.GRAY);
                        Toast.makeText(context , "end service", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        scanFilter = new IntentFilter();
        scanFilter.addAction(Service3.END_SERVICE);
    }

    public void onClickTest1(View v) {
        Log.d(TAG, "onClickTest1 in " + Thread.currentThread());
        Intent intent = new Intent(this, Service1.class);
        intent.putExtra(Service1.EXTRA_MYARG, "Hello, Service1");
        startService(intent);
    }

    public void onClickTest2(View v) {
        Log.d(TAG, "onClickTest2 in " + Thread.currentThread());
        Intent intent = new Intent(this, Service2.class);
        intent.putExtra(Service2.EXTRA_MYARG, "Hello, Service2");
        startService(intent);
    }

    public void onClickTest3(View v){
        Log.d(TAG, "onClickTest3 in " + Thread.currentThread());
        Intent intent = new Intent(this, Service3.class);
        intent.putExtra(Service3.EXTRA_MYARG, "Hello, Service3");
        colorChange(Color.RED);
        startService(intent);
    }

    public void colorChange(int colorCode){
        Button button = findViewById(R.id.test3_button);
        Drawable colorDrawable = new ColorDrawable(colorCode);
        button.setBackgroundDrawable(colorDrawable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        registerReceiver(scanReceiver, scanFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        unregisterReceiver(scanReceiver);
    }
}
