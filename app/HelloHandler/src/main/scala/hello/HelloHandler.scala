import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events._
import scala.collection.JavaConverters._

object HelloHandler {
  def handler(
      event: APIGatewayProxyRequestEvent,
      context: Context
  ): APIGatewayProxyResponseEvent = {
    val query = event.getQueryStringParameters()

    val body = if (query.get("name") != null) {
      s"Hello ${query.get("name")}!"
    } else {
      "Hello world!"
    }

    new APIGatewayProxyResponseEvent()
      .withBody(body)
      .withHeaders(Map("Content-Type" -> "text/plain").asJava)
  }
}
