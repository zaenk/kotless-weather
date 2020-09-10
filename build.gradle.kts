import io.kotless.plugin.gradle.dsl.kotless
import zaenk.buildutils.withLocalProperties

withLocalProperties()

plugins {
    kotlin("jvm") version "1.3.72" apply true
    id("io.kotless") version "0.1.6" apply true
    kotlin("plugin.serialization") version "1.3.72"
}

group = "zaenk.kotlessWeather"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.kotless", "kotless-lang", "0.1.6")
    implementation("org.jetbrains.kotlinx", "kotlinx-html-jvm", "0.6.11")
    implementation("com.amazonaws", "aws-java-sdk-dynamodb", "1.11.858")
}

tasks {
    withType<org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf(
                "-Xopt-in=kotlin.RequiresOptIn"
            )
        }
    }
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
    extensions {
        local {
            useAWSEmulation = true
        }
        terraform {
            files {
                add(file("src/terraform/dynamodb.tf"))
            }
        }
    }
}
