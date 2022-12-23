import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events._
import scala.collection.JavaConverters._
import scala.concurrent.Await

object MskReadHandler {
  def handler(
      event: KafkaEvent,
      context: Context
  ): Unit = {
    val records = event.getRecords

    records.forEach((topic, values) => {
      println(topic)

      values.forEach(v => {
        val key = v.getKey
        val value = v.getValue

        println(s"${key}: ${value}")
      })

      println("")
    })
  }
}
