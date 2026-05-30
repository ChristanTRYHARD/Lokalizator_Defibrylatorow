package com.example.lokalizator_bankomatw;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefibrillatorRepository {
    private static final String TAG = "RETROFIT_DEBUG";

    public interface OnDataReadyCallback {
        void onSuccess(List<Defibrillator> defibrillators);
        void onError(String errorMessage);
    }

    public void fetchAllDefibrillators(String token, final OnDataReadyCallback callback) {
        Log.d(TAG, "Rozpoczynam wysyłanie zapytania POST...");
        OtwarteDaneWarszawyAPI api = RetrofitClient.getApiService();

        Map<String, String> emptyBody = new HashMap<>();
        String formattedToken = token.startsWith("Bearer ") ? token : "Bearer " + token;

        Call<DefibrillatorResponse> call = api.getAllDefibrillators(formattedToken, emptyBody);

        call.enqueue(new Callback<DefibrillatorResponse>() {
            @Override
            public void onResponse(Call<DefibrillatorResponse> call, Response<DefibrillatorResponse> response) {
                Log.d(TAG, "Otrzymano odpowiedź z serwera. Kod HTTP: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    List<Defibrillator> defibrillatorsList = response.body().getResult();
                    if (defibrillatorsList != null && !defibrillatorsList.isEmpty()) {
                        callback.onSuccess(defibrillatorsList);
                    } else {
                        callback.onError("Lista obiektów w odpowiedzi jest pusta (null)");
                    }
                } else {
                    callback.onError("Błąd serwera. Kod HTTP: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DefibrillatorResponse> call, Throwable t) {
                Log.e(TAG, "Krytyczny błąd sieci!", t);
                callback.onError("Błąd sieci: " + t.getMessage());
            }
        });
    }

    /**
     * Metoda wywoływana jawnie, gdy użytkownik zgodzi się na dane testowe
     */
    public void fetchMockData(OnDataReadyCallback callback) {
        List<Defibrillator> mockList = new ArrayList<>();

        // URZĄDZENIE 1: URZĄD DZIELNICY MOKOTÓW
        Defibrillator def1 = new Defibrillator();
        Defibrillator.Geometry geo1 = new Defibrillator.Geometry();
        geo1.setType("Point");
        geo1.setCoordinates(Arrays.asList(21.0148748892902, 52.2086170695684));
        def1.setGeometry(geo1);

        Defibrillator.Properties prop1 = new Defibrillator.Properties();
        prop1.setDefibrillatorId("622");
        prop1.setLocationObjectName("Urząd Dzielnicy Mokotów m.st. Warszawy");
        prop1.setLocationStreet("Rakowiecka");
        prop1.setLocationBuilding("25/27");
        prop1.setLocationPostcode("02-517");
        prop1.setLocationCity("Warszawa");
        prop1.setDeviceAvailability("Ograniczona");
        prop1.setDeviceAccessDescription("Poniedziałek: 8:00-18:00; Wtorek-Piątek: 8:00-16:00 (Weryfikacja: 2026-05-15)");
        prop1.setDeviceLocation("Wewnątrz budynku");
        prop1.setLocationDescription("W głównym holu urzędu, na półpiętrze przy portierni.");
        prop1.setDevicePublicAccess("Publiczny");
        prop1.setDeviceManufacturer("Medtronic");
        prop1.setDeviceDescription("Urządzenie wyposażone w moduł powiadamiania służb ratunkowych CPR.");
        def1.setProperties(prop1);
        mockList.add(def1);

        // URZĄDZENIE 2: RYNEK STAREGO MIASTA
        Defibrillator def2 = new Defibrillator();
        Defibrillator.Geometry geo2 = new Defibrillator.Geometry();
        geo2.setType("Point");
        geo2.setCoordinates(Arrays.asList(21.0080, 52.2350));
        def2.setGeometry(geo2);

        Defibrillator.Properties prop2 = new Defibrillator.Properties();
        prop2.setDefibrillatorId("999");
        prop2.setLocationObjectName("Stare Miasto - Punkt AED Kapsuła");
        prop2.setLocationStreet("Rynek Starego Miasta");
        prop2.setLocationBuilding("15");
        prop2.setLocationPostcode("00-272");
        prop2.setLocationCity("Warszawa");
        prop2.setDeviceAvailability("Całodobowy");
        prop2.setDeviceAccessDescription("Dostępny 24/7 przez cały rok (Zainstalowano: 2026-01-10)");
        prop2.setDeviceLocation("Na zewnątrz");
        prop2.setLocationDescription("Na zewnętrznej elewacji kamienicy, w zielonej, podgrzewanej skrzynce.");
        prop2.setDevicePublicAccess("Publiczny");
        prop2.setDeviceManufacturer("Philips");
        prop2.setDeviceDescription("Kapsuła zabezpieczona alarmem dźwiękowym przy otworciu.");
        def2.setProperties(prop2);
        mockList.add(def2);

        callback.onSuccess(mockList);
    }
}