package com.example.lokalizator_bankomatw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DefibrillatorDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defibrillator_details);

        // 1. Powiązanie wszystkich widoków z pliku XML (wersja rozszerzona)
        TextView tvTitle = findViewById(R.id.tvDetailsTitle);
        TextView tvId = findViewById(R.id.tvDetailsId);
        TextView tvAddress = findViewById(R.id.tvDetailsAddress);
        TextView tvCity = findViewById(R.id.tvDetailsCity); // <--- TUTAJ BYŁ BRAK!
        TextView tvAvailability = findViewById(R.id.tvDetailsAvailability);
        TextView tvAccessDescription = findViewById(R.id.tvDetailsAccessDescription);
        TextView tvDeviceLocation = findViewById(R.id.tvDetailsDeviceLocation);
        TextView tvLocationDescription = findViewById(R.id.tvDetailsLocationDescription);
        TextView tvPublicAccess = findViewById(R.id.tvDetailsPublicAccess);
        TextView tvManufacturer = findViewById(R.id.tvDetailsManufacturer);
        TextView tvDescription = findViewById(R.id.tvDetailsDescription);

        // 2. Odbieranie KOMPLETU danych przekazanych z MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("aed_id", "Brak ID");
            String name = extras.getString("aed_name", "Nieznany obiekt");
            String street = extras.getString("aed_street", "Brak adresu");
            String building = extras.getString("aed_building", "");
            String postcode = extras.getString("aed_postcode", "00-000");
            String city = extras.getString("aed_city", "Warszawa");

            String avail = extras.getString("aed_avail", "Brak danych");
            String accessDesc = extras.getString("aed_access_desc", "Brak szczegółów");
            String devLocation = extras.getString("aed_device_location", "Brak danych");
            String locDesc = extras.getString("aed_loc_desc", "Brak dokładnego opisu");
            String publicAccess = extras.getString("aed_public_access", "Brak danych");
            String manufacturer = extras.getString("aed_manufacturer", "Nieznany");
            String desc = extras.getString("aed_desc", "Brak dodatkowego opisu");

            // 3. Wstrzykiwanie danych do pól tekstowych
            if (tvTitle != null) tvTitle.setText(name);
            if (tvId != null) tvId.setText("ID urządzenia: " + id);

            // Łączenie ulicy z numerem budynku (np. Rakowiecka 25/27)
            if (tvAddress != null) {
                tvAddress.setText(building.isEmpty() ? "ul. " + street : "ul. " + street + " " + building);
            }

            // DYNAMICZNE USTAWIENIE KODU I MIASTA (Naprawia problem z 00-000 Warszawa!)
            if (tvCity != null) tvCity.setText(postcode + " " + city);

            if (tvAvailability != null) tvAvailability.setText(avail);
            if (tvAccessDescription != null) tvAccessDescription.setText(accessDesc);
            if (tvDeviceLocation != null) tvDeviceLocation.setText(devLocation);
            if (tvLocationDescription != null) tvLocationDescription.setText(locDesc);
            if (tvPublicAccess != null) tvPublicAccess.setText(publicAccess);
            if (tvManufacturer != null) tvManufacturer.setText(manufacturer);
            if (tvDescription != null) tvDescription.setText(desc);
        }

        // 1. Znajdź przycisk po ID z pliku XML
        Button btnBack = findViewById(R.id.btnBack);

        // 2. Ustaw słuchacz kliknięć
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Zamyka bieżącą aktywność i automatycznie wraca do MainActivity (mapy)
                finish();
            }
        });
    }
}