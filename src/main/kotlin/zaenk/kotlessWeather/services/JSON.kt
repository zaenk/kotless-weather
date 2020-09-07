package zaenk.kotlessWeather.services

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.serializer

object JSON {
    val json = Json(JsonConfiguration.Stable)

    @OptIn(kotlinx.serialization.ImplicitReflectionSerializer::class)
    inline fun <reified T : Any> encodeToString(value: T): String {
        return json.stringify(T::class.serializer(), value)
    }
}
