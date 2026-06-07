package com.example.lokalizator_defibrylatorow;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Etykieta do logów
    private static final String TAG = "MainActivity_DEBUG";
    private DefibrillatorRepository defibrillatorRepository;
    private GoogleMap mMap;
    private List<Defibrillator> downloadedDefibrillators;

    // Handler i Runnable do odliczania 10 sekund
    private final Handler autoRetryHandler = new Handler();
    private Runnable retryRunnable;

    private static final String API_TOKEN = BuildConfig.WARSAW_API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        defibrillatorRepository = new DefibrillatorRepository();

        // Pierwsze uruchomienie pobierania danych
        fetchDefibrillatorsData();
    }

    private void fetchDefibrillatorsData() {
        defibrillatorRepository.fetchAllDefibrillators(API_TOKEN, new DefibrillatorRepository.OnDataReadyCallback() {
            @Override
            public void onSuccess(List<Defibrillator> defibrillators) {
                // Sukces! Jeśli licznik 10s był aktywny, wyłączamy go
                stopAutomaticRetry();

                downloadedDefibrillators = defibrillators;
                Toast.makeText(MainActivity.this, "Pomyślnie załadowano " + defibrillators.size() + " urządzeń z API", Toast.LENGTH_SHORT).show();

                if (mMap != null) {
                    displayDefibrillatorsOnMap();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Błąd pobierania danych: " + errorMessage);

                // POPRAWIONO: Teraz okienko wyskakuje ZA KAŻDYM RAZEM, gdy pobieranie z API się nie uda
                showErrorChoiceDialog(errorMessage);
            }
        });
    }

    /**
     * Pokazuje okno dialogowe z informacją o błędzie i dwoma opcjami
     */
    private void showErrorChoiceDialog(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Błąd pobierania danych");
        builder.setMessage("Nie udało się pobrać danych z Warszawy.\n\n" +
                "Szczegóły: " + error + "\n\n" +
                "Czy chcesz załadować lokalne dane testowe (Mokotów / Stare Miasto)?");

        // OPCJA OK -> Rezygnujemy z API, ładujemy sztuczne dane na mapę
        builder.setPositiveButton("TAK, ładuj testowe", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopAutomaticRetry();
                loadMockDataFallback();
            }
        });

        // OPCJA ANULUJ -> Nie chcemy danych testowych, odpalamy odliczanie 10 sekund do następnej próby API
        builder.setNegativeButton("Anuluj (Ponów za 10s)", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Kolejna próba pobrania z API nastąpi za 10 sekund...", Toast.LENGTH_SHORT).show();
                startAutomaticRetry();
            }
        });

        builder.setCancelable(false); // Użytkownik MUSI kliknąć któryś przycisk
        builder.show();
    }

    private void loadMockDataFallback() {
        defibrillatorRepository.fetchMockData(new DefibrillatorRepository.OnDataReadyCallback() {
            @Override
            public void onSuccess(List<Defibrillator> defibrillators) {
                downloadedDefibrillators = defibrillators;
                if (mMap != null) {
                    displayDefibrillatorsOnMap();
                }
            }

            @Override
            public void onError(String errorMessage) {}
        });
    }

    /**
     * Planuje uruchomienie pobierania danych z API dokładnie za 10 sekund
     */
    private void startAutomaticRetry() {
        stopAutomaticRetry(); // Bezpieczeństwo: usuwamy ewentualne wiszące zadania

        retryRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Minęło 10 sekund! Uruchamiam ponowne zapytanie do serwera...");
                fetchDefibrillatorsData();
            }
        };

        // Wrzucamy zadanie do Handlera z opóźnieniem 10 000 ms
        autoRetryHandler.postDelayed(retryRunnable, 10000);
    }

    private void stopAutomaticRetry() {
        if (retryRunnable != null) {
            autoRetryHandler.removeCallbacks(retryRunnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAutomaticRetry(); // Zapobiega wyciekom pamięci po zamknięciu aplikacji
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Defibrillator def = (Defibrillator) marker.getTag();

                if (def != null && def.getProperties() != null) {
                    Intent intent = new Intent(MainActivity.this, DefibrillatorDetailsActivity.class);
                    Defibrillator.Properties props = def.getProperties();

                    intent.putExtra("aed_id", props.getDefibrillatorId());
                    intent.putExtra("aed_name", props.getLocationObjectName());
                    intent.putExtra("aed_street", props.getLocationStreet());
                    intent.putExtra("aed_building", props.getLocationBuilding());
                    intent.putExtra("aed_postcode", props.getLocationPostcode());
                    intent.putExtra("aed_city", props.getLocationCity());
                    intent.putExtra("aed_avail", props.getDeviceAvailability());
                    intent.putExtra("aed_access_desc", props.getDeviceAccessDescription());
                    intent.putExtra("aed_device_location", props.getDeviceLocation());
                    intent.putExtra("aed_loc_desc", props.getLocationDescription());
                    intent.putExtra("aed_public_access", props.getDevicePublicAccess());
                    intent.putExtra("aed_manufacturer", props.getDeviceManufacturer());
                    intent.putExtra("aed_desc", props.getDeviceDescription());

                    startActivity(intent);
                }
                return true;
            }
        });

        if (downloadedDefibrillators != null) {
            displayDefibrillatorsOnMap();
        }
    }

    private void displayDefibrillatorsOnMap() {
        if (mMap == null || downloadedDefibrillators == null) return;

        mMap.clear();

        for (Defibrillator def : downloadedDefibrillators) {
            if (def != null && def.getGeometry() != null && def.getGeometry().getCoordinates() != null) {
                List<Double> coords = def.getGeometry().getCoordinates();

                if (coords != null && coords.size() >= 2) {
                    double lon = coords.get(0);
                    double lat = coords.get(1);
                    LatLng position = new LatLng(lat, lon);

                    String title = "Defibrylator AED";
                    if (def.getProperties() != null && def.getProperties().getLocationObjectName() != null) {
                        title = def.getProperties().getLocationObjectName();
                    }

                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(position)
                            .title(title));

                    if (marker != null) {
                        marker.setTag(def);
                    }
                }
            }
        }

        LatLng warszawaCentrum = new LatLng(52.2297, 21.0122);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(warszawaCentrum, 11f));
    }
}