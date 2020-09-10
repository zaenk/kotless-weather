# kotless-weather

Demonstrates a kotless framework with a simple weather API.

## Getting started

* Create `local.properties`
  ```properties
  env=dev
  awsRegion=eu-west-2
  awsProfile=local
  kotlessBucket=my-kotless-bucket
  ```
* Run `./gradlew local`
* Run the test http requests from `requests/test.http` with the `local` env

With LocalStack, check the dynamo db content:

```sh
# docker ps - find out the mapped port for 4569
aws --endpoint-url http://localhost:4569/ dynamodb scan --table-name weather-stations
```

## Place for improvements

* Windows *support*
* Tagging resources
* More control over naming of applications
* Naming resources with permissions, at least with prefix
* Update AWS Java SDK v2
* Update Kotlin 1.4
* Update Kotlinx serialization 1.0
