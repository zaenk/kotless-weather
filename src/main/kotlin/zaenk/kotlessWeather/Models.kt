package zaenk.kotlessWeather

import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

@Serializable
data class Forecast(
    val name: String,
    val temperature: Int,
    val temperatureUnit: TemperatureUnit
) {
    fun convertTo(unit: TemperatureUnit) = when (unit) {
        temperatureUnit -> this
        TemperatureUnit.CELSIUS -> copy(
            temperature = TemperatureUnit.fahrenheitToCelsius(temperature),
            temperatureUnit = unit
        )
        TemperatureUnit.FAHRENHEIT -> copy(
            temperature = TemperatureUnit.celsiusToFahrenheit(temperature),
            temperatureUnit = unit
        )
    }
}

enum class TemperatureUnit {
    CELSIUS,
    FAHRENHEIT;

    companion object {
        fun celsiusToFahrenheit(value: Int): Int = ((value * (9 / 5.0)) + 32).roundToInt()
        fun fahrenheitToCelsius(value: Int): Int = ((value - 32) * (5 / 9.0)).roundToInt()

        fun fromString(value: String) = when (value) {
            "F" -> FAHRENHEIT
            "C" -> CELSIUS
            else -> error("Not supported unit: $value")
        }
    }
}

data class Station(
    val gridId: String,
    val gridX: Int,
    val gridY: Int
)
