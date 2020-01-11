package com.example.donatorapp.Network;


import com.example.donatorapp.Utills.Constant;

import retrofit2.Retrofit;


public class RetrofitClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL_HTTP)
                    .build();
        }
        return retrofit;
    }
}