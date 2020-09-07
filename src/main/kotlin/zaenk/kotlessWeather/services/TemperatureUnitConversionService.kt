package zaenk.kotlessWeather.services

import io.kotless.dsl.conversion.ConversionException
import io.kotless.dsl.conversion.ConversionService
import zaenk.kotlessWeather.TemperatureUnit
import java.lang.reflect.Type

object TemperatureUnitConversionService : ConversionService() {
    override fun convertFrom(value: String, type: Type): Any {
        println(type)
        if (type.typeName == TemperatureUnit::class.java.canonicalName) {
            return TemperatureUnit.fromString(value)
        }
        throw ConversionException("Type is not supported")
    }

    override fun convertTo(value: Any): String {
        if (value is TemperatureUnit) {
            return value.toString()
        }
        throw ConversionException("Type is not supported")
    }
}
