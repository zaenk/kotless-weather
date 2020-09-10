package zaenk.buildutils

import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import java.util.*

fun Project.withLocalProperties() {
    val localProps = Properties()
    localProps.load(file("local.properties").inputStream())
    localProps.forEach { (key, value) ->
        this.extra.set(key as String, value)
    }
}
