package com.example.lokalizator_defibrylatorow;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OtwarteDaneWarszawyAPI {

    // Przywracamy właściwy adres warszawskich defibrylatorów
    @POST("https://dane.um.warszawa.pl/api/action/get_scb_lokalizacja_defibrylatorow")
    Call<DefibrillatorResponse> getAllDefibrillators(
            @Header("Authorization") String token,
            @Body Map<String, String> body
    );

    @POST("https://dane.um.warszawa.pl/api/action/get_scb_lokalizacja_defibrylatorow")
    Call<DefibrillatorResponse> getSpecificDefibrillator(
            @Header("Authorization") String token,
            @Body DefibrillatorRequest request
    );
}