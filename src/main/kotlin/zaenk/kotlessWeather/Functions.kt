package zaenk.kotlessWeather

import io.kotless.dsl.lang.http.Get
import io.kotless.dsl.lang.http.json
import io.kotless.dsl.model.HttpResponse
import zaenk.kotlessWeather.services.ForecastService
import zaenk.kotlessWeather.services.JSON

@Get("/api/forecast")
fun forecast(lat: Float, long: Float): HttpResponse {
    val forecast = ForecastService.getForecast(lat, long)
    return json(JSON.encodeToString(forecast))
}
