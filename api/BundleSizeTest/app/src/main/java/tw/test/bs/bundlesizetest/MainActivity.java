package tw.test.bs.bundlesizetest;

import android.content.Intent;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

//测试目的:为了测出一个FileDescriptor占用的字节数
//测试结论:一个FileDescriptor占用大约100个字节
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "BundleSizeTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i < 1024 * 512;i++) {
            stringBuilder.append(random.nextBoolean() ? "0" : "1");
        }
        //测试1024个fd和512k的数据,bundle会占多大的空间
        Bundle bundle = new Bundle();
        bundle.putInt("code", random.nextInt());
        bundle.putString("data", stringBuilder.toString());
        ArrayList<ParcelFileDescriptor> fileDescriptors = new ArrayList<>();
        for (int i = 0;i < 64;i++) {
            File file = new File(getFilesDir(), i + "_" + String.valueOf(UUID.randomUUID()));
            try {
                file.createNewFile();
                fileDescriptors.add(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));
            } catch (IOException e) {
                Log.d(TAG, "Fail to create file " + file.getAbsolutePath(), e);
            }
        }

        PrintWriter writer = new PrintWriter(new FileOutputStream(fileDescriptors.get(0).getFileDescriptor()));
        writer.write(String.valueOf(new Date(System.currentTimeMillis())) + ", random int:" + random.nextLong());
        writer.flush();
        writer.close();

//        bundle.putParcelableArrayList("files", fileDescriptors);
        Parcel parcel = Parcel.obtain();
        parcel.writeValue(bundle);
//        byte datas[] = parcel.marshall();
//        Log.d(TAG, "size " + datas.length);
        Log.d(TAG, "parcel.dataSize(): " + parcel.dataSize());

//        Intent intent = new Intent(this, BundleSizeReceiver.class);
//        intent.putExtra("data", bundle);
//        sendBroadcast(intent);
    }
}
