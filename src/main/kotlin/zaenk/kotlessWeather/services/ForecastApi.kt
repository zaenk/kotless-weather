package zaenk.kotlessWeather.services

import io.kotless.dsl.lang.LambdaInit
import io.kotless.dsl.lang.LambdaWarming
import kotlinx.serialization.json.JsonObject
import org.slf4j.LoggerFactory
import zaenk.kotlessWeather.Forecast
import zaenk.kotlessWeather.Station
import zaenk.kotlessWeather.TemperatureUnit
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object ForecastApi : LambdaInit, LambdaWarming {
    private val logger = LoggerFactory.getLogger(javaClass)
    private const val apiUrl = "https://api.weather.gov"
    private lateinit var httpClient: HttpClient

    override fun init() {
        logger.info("lambda init")
        httpClient = HttpClient.newHttpClient()
        logger.info("lambda init done")
    }

    override fun warmup() {
        logger.info("lambda warmup")
        val status = httpClient.head(apiUrl)
        logger.info("lambda warmup done, response status: $status")
    }

    fun getStations(lat: Float, long: Float): Station {
        val response = httpClient.getJson("$apiUrl/points/${lat},${long}")
        val properties = response.getObject("properties")
        return Station(
            gridId = properties.getPrimitive("gridId").content,
            gridX = properties.getPrimitive("gridX").int,
            gridY = properties.getPrimitive("gridY").int
        )
    }

    fun getForecast(station: Station): Forecast {
        val response =
            httpClient.getJson("$apiUrl/gridpoints/${station.gridId}/${station.gridX},${station.gridY}/forecast")
        val properties = response.getObject("properties")
        val nextPeriod = properties.getArray("periods").getObject(0)
        return Forecast(
            name = nextPeriod.getPrimitive("name").content,
            temperature = nextPeriod.getPrimitive("temperature").int,
            temperatureUnit = TemperatureUnit.fromString(nextPeriod.getPrimitive("temperatureUnit").content)
        )
    }

    private fun HttpClient.getJson(uri: String): JsonObject {
        val request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(uri))
            .build()
        val response = send(request, HttpResponse.BodyHandlers.ofString())
        return if (response.statusCode() == 200) {
            JSON.decodeFromString(response.body())
        } else {
            error("Failed to execute request [$uri]: ${response.statusCode()}")
        }
    }

    private fun HttpClient.head(uri: String): Int {
        val request = HttpRequest.newBuilder()
            .method("HEAD", HttpRequest.BodyPublishers.noBody())
            .uri(URI.create(uri))
            .build()
        val response = send(request, HttpResponse.BodyHandlers.discarding())
        return response.statusCode()
    }
}
