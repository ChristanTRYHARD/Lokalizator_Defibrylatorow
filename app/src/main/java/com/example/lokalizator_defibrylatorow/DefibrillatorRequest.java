package com.example.lokalizator_defibrylatorow;

import com.google.gson.annotations.SerializedName;

public class DefibrillatorRequest {

    // To jest naklejka na tym jednym polu, która mówi Gsonowi: „jak będziesz pakował ten obiekt do JSONa, użyj innej nazwy klucza."
    @SerializedName("defibrillator_id")
    private String defibrillatorId;

    // Konstruktor – ułatwi Ci tworzenie zapytania w przyszłości
    public DefibrillatorRequest(String defibrillatorId) {
        this.defibrillatorId = defibrillatorId;
    }

    public String getDefibrillatorId() {
        return defibrillatorId;
    }

    public void setDefibrillatorId(String defibrillatorId) {
        this.defibrillatorId = defibrillatorId;
    }
}