package com.tw.okhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "OKHttpTest";

    OkHttpClient mClient;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mClient == null) {
            mClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    return chain.proceed(chain.request());
                }
            }).build();
            Log.d(TAG, "Create ok http ok");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .client(mClient)
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, Object>() {
                            @Override
                            public Object convert(ResponseBody value) throws IOException {
                                String string = value.string();
                                Log.d(TAG, "ResponseBody:" + string.substring(0, 10));
                                return string;
                            }
                        };
                    }

                    @Override
                    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
                        return new Converter<Object, RequestBody>() {
                            @Override
                            public RequestBody convert(Object value) throws IOException {
                                Log.d(TAG, "request:" + value);
                                return null;
                            }
                        };
                    }

                    @Override
                    public Converter<?, String> stringConverter(Type type, Annotation[] annotations,
                                                                          Retrofit retrofit) {
                        return new Converter<Object, String>() {
                            @Override
                            public String convert(Object value) throws IOException {
                                Log.d(TAG, "convert:" + String.valueOf(value).substring(0, 10));
                                return String.valueOf(value);
                            }
                        };
                    }
                })
                .baseUrl("https://www.mi.com/")
                .build();

        final FetchMi fetchMi = retrofit.create(FetchMi.class);

        mTextView = findViewById(R.id.text_hello);
        findViewById(R.id.btn_fetch).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Task().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

        findViewById(R.id.btn_retrofit_fetch).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Call<String> call = fetchMi.getContent("redminote7");
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                        String bodyString = response.body();
                        Log.d(TAG, "onResponse:" + bodyString.substring(0, 20));
                        mTextView.setText(bodyString);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d(TAG, "onFailure:" + t);
                    }
                });
            }
        });

        mTextView.setText(String.valueOf(android.os.Process.myPid()));
    }

    private class Task extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                return fetch("https://www.mi.com");
            } catch (IOException e) {
                Log.e(TAG, "Fail to fetch data.", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            mTextView.setText(result);
        }
    }

    String fetch(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }
}
