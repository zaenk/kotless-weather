package zaenk.kotlessWeather.pages

import kotlinx.html.*
import kotlinx.html.stream.createHTML

fun html(body: TagConsumer<String>.() -> Unit): String {
    return createHTML().apply(body).finalize()
}
