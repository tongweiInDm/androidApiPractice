package tw.api.chooseractivitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TestChooserIntent";

    private static final int REQUEST_CODE_PICK = 123;
    private static final int REQUEST_CODE_GETCONTENT = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CheckBox checkBoxWrap = findViewById(R.id.checkBox_wrap);
        final CheckBox checkBoxPkg = findViewById(R.id.checkBox_fill_package_name);

        findViewById(R.id.text_pick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testPick(checkBoxWrap.isChecked(), checkBoxPkg.isChecked());
            }
        });

        findViewById(R.id.text_getContent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testGetContent(checkBoxWrap.isChecked(), checkBoxPkg.isChecked());
            }
        });
    }

    private void testPick(boolean needWrapIntent, boolean needFillPackageName) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        if (needFillPackageName) {
            intent.setPackage(getPackageName());
        }
        intent.setType("image/*");
        Intent finalIntent = intent;
        if (needWrapIntent) {
            finalIntent = Intent.createChooser(intent, "tongwei");
        }
        startActivityForResult(finalIntent, REQUEST_CODE_PICK);
    }

    private void testGetContent(boolean needWrapIntent, boolean needFillPackageName) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        if (needFillPackageName) {
            intent.setPackage(getPackageName());
        }
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        //noinspection deprecation
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        Intent finalIntent = intent;
        if (needWrapIntent) {
            finalIntent = Intent.createChooser(intent, "tongwei");
        }
        startActivityForResult(finalIntent, REQUEST_CODE_GETCONTENT);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "requestCode:" + requestCode + ", resultCode:" + resultCode + ", data" + data);
    }
}
