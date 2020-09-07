package zaenk.kotlessWeather

import io.kotless.dsl.lang.http.Get

@Get("/")
fun hello() = "Hello world!"
