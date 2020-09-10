package zaenk.kotlessWeather.services

import zaenk.kotlessWeather.Forecast
import zaenk.kotlessWeather.store.Stations

object ForecastService {
    fun getForecast(lat: Float, long: Float): Forecast {
        val station = Stations.getByCoordinates(lat, long) ?: ForecastApi.getStations(lat, long).also { station ->
            Stations.putForCoordinates(lat, long, station)
        }
        return ForecastApi.getForecast(station)
    }
}
