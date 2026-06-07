package com.example.lokalizator_defibrylatorow;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OtwarteDaneWarszawyAPI {

    /*
    @POST to rozkaz dla Retrofita: „wyślij zapytanie POST pod ten adres, gdy ktoś zawoła tę metodę".
    @Header wymagany nagłówek w każdym zapytaniu. Zgodnie z oczekiwaniami api dane.um.warszawa.pl
    @Body zawartość zapytania, w tym przypadku obiekt typu DefibrillatorRequest.
     */
    @POST("/api/action/get_scb_lokalizacja_defibrylatorow")
    Call<DefibrillatorResponse> getAllDefibrillators(
            @Header("Authorization") String token,
            @Body Map<String, String> body
    );

    @POST("/api/action/get_scb_lokalizacja_defibrylatorow")
    Call<DefibrillatorResponse> getSpecificDefibrillator(
            @Header("Authorization") String token,
            @Body DefibrillatorRequest request
    );
}