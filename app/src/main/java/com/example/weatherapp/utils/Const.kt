package com.example.weatherapp.utils

import java.io.BufferedReader
import java.util.stream.Collectors

const val LOCATION_RUSSIAN = 1
const val LOCATION_WORLD = 2
const val YANDEX_WEATHER_URL = "https://api.weather.yandex.ru/v2/informers"
const val YANDEX_HEADER = "X-Yandex-API-Key"
const val YANDEX_BASE_URL = "https://api.weather.yandex.ru"
const val IS_WORLD = "IS_WORLD"
const val DB_WEATHER = "DB_WEATHER"