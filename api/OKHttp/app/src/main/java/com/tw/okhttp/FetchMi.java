package com.tw.okhttp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FetchMi {
    @GET("{pageName}")
    Call<String> getContent(@Path("pageName")String pageName);
}
