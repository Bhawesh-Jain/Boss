package com.boss.Retrofit;

import android.content.Context;

import com.boss.util.BaseUrl;
import com.boss.util.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static ApiService getClient(Context context) {
  /*      if(!AppController.getInstance(context).isOnline()){
            Toast.makeText(AppController.getContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }*/
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES);

        if (BaseUrl.Development.equals(Constants.Key.Debug)) {
            client.addInterceptor(interceptor);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
        return retrofit.create(ApiService.class);
    }
}
