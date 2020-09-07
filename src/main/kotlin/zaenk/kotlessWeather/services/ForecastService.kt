package zaenk.kotlessWeather.services

import zaenk.kotlessWeather.Forecast
import zaenk.kotlessWeather.TemperatureUnit

object ForecastService {
    fun getForecast(lat: Float, long: Float): Forecast =
        Forecast(
            name = "This afternoon",
            temperature = 20,
            temperatureUnit = TemperatureUnit.CELSIUS
        )
}
