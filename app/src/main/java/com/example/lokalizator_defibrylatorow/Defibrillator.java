package com.example.lokalizator_defibrylatorow;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Defibrillator {

    @SerializedName("geometry")
    private Geometry geometry;

    @SerializedName("properties")
    private Properties properties;

    // --- KONSTRUKTORY ---
    public Defibrillator() {}

    public Defibrillator(Geometry geometry, Properties properties) {
        this.geometry = geometry;
        this.properties = properties;
    }

    // --- GETTERY I SETTERY DLA KLASY GŁÓWNEJ ---
    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    // =========================================================================
    // 1. KLASA WEWNĘTRZNA - GEOMETRY (Współrzędne)
    // =========================================================================
    public static class Geometry {

        @SerializedName("type")
        private String type;

        @SerializedName("coordinates")
        private List<Double> coordinates; // Pojedyncza lista [X, Y] zgodna z API

        // --- KONSTRUKTORY GEOMETRY ---
        public Geometry() {}

        public Geometry(String type, List<Double> coordinates) {
            this.type = type;
            this.coordinates = coordinates;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Double> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<Double> coordinates) {
            this.coordinates = coordinates;
        }
    }

    // =========================================================================
    // 2. KLASA WEWNĘTRZNA - PROPERTIES (Wszystkie szczegóły urządzenia)
    // =========================================================================
    public static class Properties {

        @SerializedName("defibrillator_id")
        private String defibrillatorId;

        @SerializedName("location_object_name")
        private String locationObjectName;

        @SerializedName("location_street")
        private String locationStreet;

        @SerializedName("location_building")
        private String locationBuilding;

        @SerializedName("device_description")
        private String deviceDescription;

        @SerializedName("device_access_description")
        private String deviceAccessDescription;

        @SerializedName("device_availability")
        private String deviceAvailability;

        // Nowe pola z Twojego JSONa:
        @SerializedName("location_city")
        private String locationCity;

        @SerializedName("location_postcode")
        private String locationPostcode;

        @SerializedName("device_manufacturer")
        private String deviceManufacturer;

        @SerializedName("device_public_access")
        private String devicePublicAccess;

        @SerializedName("location_description")
        private String locationDescription;

        @SerializedName("device_location")
        private String deviceLocation;

        // --- KONSTRUKTORY ---
        public Properties() {}

        // Konstruktor pomocniczy do tworzenia obiektów testowych
        public Properties(String defibrillatorId, String locationObjectName, String locationStreet,
                          String locationCity, String deviceAccessDescription, String deviceAvailability) {
            this.defibrillatorId = defibrillatorId;
            this.locationObjectName = locationObjectName;
            this.locationStreet = locationStreet;
            this.locationCity = locationCity;
            this.deviceAccessDescription = deviceAccessDescription;
            this.deviceAvailability = deviceAvailability;
        }

        // --- GETTERY I SETTERY ---
        public String getDefibrillatorId() { return defibrillatorId; }
        public void setDefibrillatorId(String id) { this.defibrillatorId = id; }

        public String getLocationObjectName() { return locationObjectName; }
        public void setLocationObjectName(String name) { this.locationObjectName = name; }

        public String getLocationStreet() { return locationStreet; }
        public void setLocationStreet(String street) { this.locationStreet = street; }

        public String getLocationBuilding() { return locationBuilding; }
        public void setLocationBuilding(String building) { this.locationBuilding = building; }

        public String getDeviceDescription() { return deviceDescription; }
        public void setDeviceDescription(String desc) { this.deviceDescription = desc; }

        public String getDeviceAccessDescription() { return deviceAccessDescription; }
        public void setDeviceAccessDescription(String desc) { this.deviceAccessDescription = desc; }

        public String getDeviceAvailability() { return deviceAvailability; }
        public void setDeviceAvailability(String avail) { this.deviceAvailability = avail; }

        public String getLocationCity() { return locationCity; }
        public void setLocationCity(String city) { this.locationCity = city; }

        public String getLocationPostcode() { return locationPostcode; }
        public void setLocationPostcode(String postcode) { this.locationPostcode = postcode; }

        public String getDeviceManufacturer() { return deviceManufacturer; }
        public void setDeviceManufacturer(String manufacturer) { this.deviceManufacturer = manufacturer; }

        public String getDevicePublicAccess() { return devicePublicAccess; }
        public void setDevicePublicAccess(String publicAccess) { this.devicePublicAccess = publicAccess; }

        public String getLocationDescription() { return locationDescription; }
        public void setLocationDescription(String desc) { this.locationDescription = desc; }

        public String getDeviceLocation() { return deviceLocation; }
        public void setDeviceLocation(String deviceLocation) { this.deviceLocation = deviceLocation; }
    }
}