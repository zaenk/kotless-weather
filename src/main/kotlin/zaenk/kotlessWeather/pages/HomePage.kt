package zaenk.kotlessWeather.pages

import kotlinx.html.*

object HomePage {
    fun root() = html {
        head {
            title {
                +"Kotless Serverless Framework"
            }
            link("/styles.css", rel = "stylesheet")
        }
        body {
            div("content") {
                h1 {
                    +"a landing page"
                }
            }
        }
    }
}
