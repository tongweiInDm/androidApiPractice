package tw.api.androidfilepermissiontest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(dir, "tongwei.txt");

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(file));
            writer.append("tongwei " + new Date(System.currentTimeMillis()));
        } catch (FileNotFoundException e) {

        } finally {
            if(writer != null) {
                writer.close();
            }
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            ((TextView) findViewById(R.id.tv_text)).setText(file.getPath() + ":\n" + reader.readLine());
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }

    }

}
