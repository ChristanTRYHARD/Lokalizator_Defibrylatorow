# Lokalizator Defibrylatorów AED

Aplikacja mobilna na Androida umożliwiająca lokalizację defibrylatorów AED (Automated External Defibrillator) na terenie Warszawy. W przypadku niedostępności API(OtwarteDaneWarszawy) wyświetlane są dane demonstracyjne.

## Funkcjonalności

- Pobieranie lokalizacji defibrylatorów z API Otwarte Dane Warszawa
- Wyświetlanie ich pozycji na mapie Google Maps
- Szczegóły wybranego defibrylatora (ekran szczegółów)

## Technologie

- **Język:** Java
- **Build system:** Gradle (Kotlin DSL)
- **Architektura:** MVVM (ViewModel + LiveData)
- **Sieć:** Retrofit 2 + OkHttp + Gson
- **Asynchroniczność:** Kotlin Coroutines
- **Mapy:** Google Maps SDK
- **UI:** ViewBinding, Material Design 3

## Wymagania

- Co najmniej Android 7.0+ (API 24)
- Klucz API Google Maps

## Konfiguracja przed uruchomieniem

1. Utwórz plik `local.properties` w katalogu głównym projektu.
2. Dodaj w nim **dwa klucze API**:

```properties
MAPS_API_KEY=twój_klucz_google_maps
WARSAW_API_KEY=twój_klucz_otwarte_dane_warszawa