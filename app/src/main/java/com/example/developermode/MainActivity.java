package com.example.developermode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        status = (TextView) findViewById(R.id.status);

        Button dev = (Button) findViewById(R.id.developer_button);
        dev.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openDeveloperOptions();
            }
        });

        Button debug = (Button) findViewById(R.id.debug_button);
        debug.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                openDeveloperOptions();
            }
        });

        Button refresh = (Button) findViewById(R.id.refresh_button);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                updateStatus();
            }
        });

        updateStatus();
    }

    private void openDeveloperOptions() {
        try {
            startActivity(new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
        } catch (Exception e) {
            startActivity(new Intent(Settings.ACTION_SETTINGS));
        }
    }

    private void updateStatus() {
        boolean enabled = false;
        try {
            enabled = Settings.Global.getInt(getContentResolver(), "adb_enabled", 0) == 1;
        } catch (Exception ignored) {
            try {
                enabled = Settings.Secure.getInt(getContentResolver(), "adb_enabled", 0) == 1;
            } catch (Exception ignoredAgain) { }
        }

        if (enabled) {
            status.setText(getString(R.string.debug_enabled));
        } else {
            status.setText(getString(R.string.debug_disabled));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (status != null) updateStatus();
    }
}
