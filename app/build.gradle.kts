import java.util.Properties

// Wtyczka aplikacji Android (com.android.application)
plugins {
    alias(libs.plugins.android.application)
}

// Wczytanie pliku local.properties
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use {
        localProperties.load(it)
    }
}

// Główna konfiguracja modułu aplikacji
android {
    // Identyfikator przestrzeni nazw dla wygenerowanych klas (R, BuildConfig, itp.)
    namespace = "com.example.lokalizator_defibrylatorow"

    // Poziom API używany do kompilacji (tu: Android 16 / API 36)
    compileSdk = 36

    // Domyślna konfiguracja wspólna dla wszystkich wariantów builda (debug i release)
    defaultConfig {
        // Unikalny identyfikator aplikacji w Google Play (raz ustawiony, nie powinien się zmieniać)
        applicationId = "com.example.lokalizator_defibrylatorow"

        // Minimalna wersja Androida: API 24 (Android 7.0 Nougat)
        minSdk = 24

        // Docelowa wersja Androida, na której aplikacja była testowana (API 36)
        targetSdk = 36

        // Wewnętrzny numer wersji (musi być zwiększany przed każdą publikacją w sklepie)
        versionCode = 1

        // Nazwa wersji wyświetlana użytkownikowi
        versionName = "1.0"

        // Runner uruchamiający testy instrumentacyjne (UI tests)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Wstrzyknięcie klucza API Google Maps do manifestu jako placeholder ${MAPS_API_KEY}
        manifestPlaceholders["MAPS_API_KEY"] = localProperties.getProperty("MAPS_API_KEY")
            ?: error("MAPS_API_KEY nie jest zdefiniowany w local.properties")
    }

    // Włączenie dodatkowych funkcji builda
    buildFeatures {
        // ViewBinding: bezpieczne, kompilowane odwołania do widoków zamiast findViewById
        viewBinding = true
    }

    // Konfiguracja wariantów builda (debug / release)
    buildTypes {
        // Ustawienia dla wersji produkcyjnej (release)
        release {
            // Wyłączenie minifikacji kodu (ProGuard/R8) – false = szybszy build, większy APK
            isMinifyEnabled = false

            // Pliki konfiguracyjne ProGuard: domyślne reguły Androida + własne reguły projektu
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Wersja języka Java używana do kompilacji (źródło i cel)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// Zależności (biblioteki zewnętrzne)
dependencies {

    // Podstawowe biblioteki AndroidX (UI, kompatybilność, layouty)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Testy jednostkowe (JUnit)
    testImplementation(libs.junit)

    // Testy instrumentacyjne (UI tests na urządzeniu / emulatorze)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit + OkHttp – komunikacja HTTP z API Warszawy
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Kotlin Coroutines – asynchroniczne operacje (np. zapytania sieciowe)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // AndroidX Lifecycle – ViewModel, LiveData, lifecycle-aware components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")

    // Google Maps SDK – wyświetlanie mapy z lokalizacją defibrylatorów
    implementation("com.google.android.gms:play-services-maps:18.2.0")
}