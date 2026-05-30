package com.example.lokalizator_bankomatw;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://dane.um.warszawa.pl/";
    private static Retrofit retrofit = null;

    // Singleton - gwarantuje tylko jedną instancję w pamięci
    public static OtwarteDaneWarszawyAPI getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(OtwarteDaneWarszawyAPI.class);
    }
}