package com.challenge.weather.service;

public class Constants {

    public static final String KEY_PLACEHOLDER = "KEY_VALUE";
    public static final String LAT_PLACEHOLDER = "LAT_VALUE";
    public static final String LON_PLACEHOLDER = "LON_VALUE";
    public static final String ZIP_PLACEHOLDER = "ZIP_VALUE";
    public static final String CITY_ID_PLACEHOLDER = "CITY_ID_VALUE";
    public static final String ICON_ID_PLACEHOLDER = "ICON_ID_PLACEHOLDER";

    public static final long OPEN_WEATHER_MAP_REFRESH_INTERVAL = 600000;

    //
    // OpenWeatherMap
    //
    public static String OPEN_WEATHER_MAP_KEY =
            "9ba491a50a47be854b19d956d01429f1";

    public static final String WEATHER_ZIP_CODE_URI =
            "http://api.openweathermap.org/data/2.5/weather?zip=" + ZIP_PLACEHOLDER + "&APPID=" + KEY_PLACEHOLDER + "&units=metric";

    public static final String WEATHER_CITY_ID_URI =
            "http://api.openweathermap.org/data/2.5/weather?id=" + CITY_ID_PLACEHOLDER + "&APPID=" + KEY_PLACEHOLDER + "&units=metric";

    public static final String WEATHER_ICON_URI = "http://openweathermap.org/img/wn/" + ICON_ID_PLACEHOLDER + "@2x.png";

    //
    // OpenCageData
    //
    public static String OPEN_CAGE_DATA_KEY =
            "21f5849e9fa64faf94073656efbcbe0b";

    public static final String REVERSE_GEOCODING_URI =
            "https://api.opencagedata.com/geocode/v1/json?key=" + KEY_PLACEHOLDER + "&q=" + LAT_PLACEHOLDER + "+" + LON_PLACEHOLDER + "&pretty=1&no_annotations=1";
}
