import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events._
import scala.collection.JavaConverters._

object MskWriteHandler {
  def handler(
      event: APIGatewayProxyRequestEvent,
      context: Context
  ): APIGatewayProxyResponseEvent = {
    val query = event.getQueryStringParameters()

    val topic = sys.env.getOrElse(
      "KAFKA_TOPIC",
      throw new Exception("KAFKA_TOPIC is not configured")
    )

    val content = if (query.containsKey("content")) {
      query.get("content")
    } else {
      "Default message content"
    }

    val result = MskProducer.sendMessage(topic, content, content).get

    new APIGatewayProxyResponseEvent()
      .withBody(s"Message sent to topic: ${'"'}${"topic"}${'"'}:\n${content}")
      .withHeaders(Map("Content-Type" -> "text/plain").asJava)
  }
}
