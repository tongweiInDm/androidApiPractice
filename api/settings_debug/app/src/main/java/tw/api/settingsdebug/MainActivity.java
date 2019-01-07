package tw.api.settingsdebug;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_ws).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //整个修改无效，不知道为什么，但是不会报错
                Settings.System.putString(getContentResolver(),
                        Settings.System.LOCATION_PROVIDERS_ALLOWED, "network");
            }
        });
        findViewById(R.id.button_rs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = Settings.System.getString(getContentResolver(), Settings.System.LOCATION_PROVIDERS_ALLOWED);
                Toast.makeText(MainActivity.this, "LOCATION_PROVIDERS_ALLOWED:" + value, Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.button_wss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.Secure.putString(getContentResolver(),
                        "tongweiTestSKey",
                        "tongweiTestSValue" + String.valueOf(new Date(System.currentTimeMillis())));
            }
        });
        findViewById(R.id.button_rss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = Settings.Secure.getString(getContentResolver(), "tongweiTestSKey");
                Toast.makeText(MainActivity.this, "tongweiTestSKey:" + value, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        checkSettingsPermission();
    }

    private void checkSettingsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                startActivity(intent);
            } else {
                Toast.makeText(this, "已授权", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
