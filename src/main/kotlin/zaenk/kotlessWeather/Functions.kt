package zaenk.kotlessWeather

import io.kotless.MimeType
import io.kotless.dsl.lang.http.Get
import io.kotless.dsl.lang.http.StaticGet
import io.kotless.dsl.lang.http.json
import io.kotless.dsl.model.HttpResponse
import zaenk.kotlessWeather.pages.HomePage
import zaenk.kotlessWeather.services.ForecastService
import zaenk.kotlessWeather.services.JSON
import java.io.File

@Get("/api/forecast")
fun forecast(lat: Float, long: Float, unit: TemperatureUnit = TemperatureUnit.CELSIUS): HttpResponse {
    val forecast = ForecastService.getForecast(lat, long)
    val converted = forecast.convertTo(unit)
    return json(JSON.encodeToString(converted))
}

@Get("/")
fun root() = HomePage.root()

@StaticGet("/styles.css", MimeType.CSS)
val styles = File("css/style.css")
