resource "aws_dynamodb_table" "station-table" {
  name = "weather-stations"
  billing_mode = "PAY_PER_REQUEST"
  hash_key = "coordinates"

  attribute {
    name = "coordinates"
    type = "S"
  }
}
