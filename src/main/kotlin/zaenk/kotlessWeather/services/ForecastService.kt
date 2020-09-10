package zaenk.kotlessWeather.services

import zaenk.kotlessWeather.Forecast

object ForecastService {
    fun getForecast(lat: Float, long: Float): Forecast {
        val station = ForecastApi.getStations(lat, long)
        return ForecastApi.getForecast(station)
    }
}
