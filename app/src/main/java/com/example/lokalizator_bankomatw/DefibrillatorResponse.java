package com.example.lokalizator_bankomatw;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DefibrillatorResponse {

    // API Warszawy zazwyczaj zwraca listę obiektów w polu "result" lub pod nazwą zasobu
    @SerializedName("result")
    private List<Defibrillator> result;

    public List<Defibrillator> getResult() {
        return result;
    }

    public void setResult(List<Defibrillator> result) {
        this.result = result;
    }
}