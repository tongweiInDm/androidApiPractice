package tw.api.chooseractivitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CheckBox checkBoxWrap = findViewById(R.id.checkBox_wrap);
        final CheckBox checkBoxPkg = findViewById(R.id.checkBox_fill_package_name);

        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test(checkBoxWrap.isChecked(), checkBoxPkg.isChecked());
            }
        });
    }

    private void test(boolean needWrapIntent, boolean needFillPackageName) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        if (needFillPackageName) {
            intent.setPackage(getPackageName());
        }
        intent.setType("image/*");
        Intent finalIntent = intent;
        if (needWrapIntent) {
            finalIntent = Intent.createChooser(intent, "tongwei");
        }
        startActivity(finalIntent);
    }
}
