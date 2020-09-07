import io.kotless.plugin.gradle.dsl.kotless

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

kotless {
    config {
        bucket = "kotless.s3.example.com"

        terraform {
            profile = "example"
            region = "us-east-1"
        }
    }
}
