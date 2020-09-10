import io.kotless.plugin.gradle.dsl.kotless
import zaenk.buildutils.withLocalProperties

withLocalProperties()

plugins {
    kotlin("jvm") version "1.3.72" apply true
    id("io.kotless") version "0.1.6" apply true
}

group = "zaenk.kotlessWeather"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.kotless", "kotless-lang", "0.1.6")
}

val env: String by extra
val awsProfile: String by extra
val awsRegion: String by extra
val kotlessBucket: String by extra

kotless {
    config {
        bucket = kotlessBucket
        prefix = env

        terraform {
            profile = awsProfile
            region = awsRegion
        }
    }
}
