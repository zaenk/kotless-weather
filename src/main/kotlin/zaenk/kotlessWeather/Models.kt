package zaenk.kotlessWeather

import kotlinx.serialization.Serializable

@Serializable
data class Forecast(
    val name: String,
    val temperature: Int,
    val temperatureUnit: TemperatureUnit
)

enum class TemperatureUnit {
    CELSIUS,
    FAHRENHEIT;
}
