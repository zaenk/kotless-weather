package zaenk.kotlessWeather.store

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import io.kotless.AwsResource
import io.kotless.PermissionLevel
import io.kotless.dsl.lang.DynamoDBTable
import io.kotless.dsl.lang.LambdaWarming
import io.kotless.dsl.lang.withKotlessLocal
import org.slf4j.LoggerFactory
import zaenk.kotlessWeather.Station

private const val tableName = "weather-stations"
private const val tableHashKey = "coordinates"

@DynamoDBTable(tableName, PermissionLevel.ReadWrite)
object Stations : LambdaWarming {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val dynamoClient = AmazonDynamoDBClientBuilder.standard().withKotlessLocal(AwsResource.DynamoDB).build()
    private var dynamoDb = DynamoDB(dynamoClient)

    override fun warmup() {
        logger.info("dynamodb warmup")
        val tableStatus = dynamoDb.getTable(tableName).describe()
        logger.info("dynamodb warmup done, table status: ${tableStatus.tableStatus}")
    }

    fun getByCoordinates(lat: Float, long: Float): Station? {
        val table = dynamoDb.getTable(tableName)
        val maybeItem = table.getItem(tableHashKey, "$lat,$long", Station.projection(), null)
        return maybeItem?.let { item ->
            Station(
                gridId = item.getString(Station::gridId.name),
                gridX = item.getInt(Station::gridX.name),
                gridY = item.getInt(Station::gridY.name)
            )
        }
    }

    fun putForCoordinates(lat: Float, long: Float, station: Station) {
        val table = dynamoDb.getTable(tableName)
        val item = Item()
            .withPrimaryKey(tableHashKey, "$lat,$long")
            .withString(Station::gridId.name, station.gridId)
            .withInt(Station::gridX.name, station.gridX)
            .withInt(Station::gridY.name, station.gridY)
        table.putItem(item)
    }

    private fun Station.Companion.projection() = "${Station::gridId.name}, ${Station::gridX.name}, ${Station::gridY.name}"
}
