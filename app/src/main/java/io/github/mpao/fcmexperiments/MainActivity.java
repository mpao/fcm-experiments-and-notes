package io.github.mpao.fcmexperiments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean isEnabled;
    private TextView type, message, params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SwitchCompat enable = findViewById(R.id.enable);
        type    = findViewById(R.id.type);
        message = findViewById(R.id.message);
        params  = findViewById(R.id.params);
        enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isEnabled = isChecked;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(new MyReceiver(), new IntentFilter("listenToThis"));
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            unregisterReceiver(new MyReceiver());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            type.setText( intent.getStringExtra("type"));
            message.setText( intent.getStringExtra("message") );
        }

    }

}
