import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events._
import scala.collection.JavaConverters._

object HelloHandler {
  def handler(
      event: APIGatewayV2HTTPEvent,
      context: Context
  ): APIGatewayV2HTTPResponse = {
    val query = event.getQueryStringParameters()

    val body = if (query != null && query.containsKey("name")) {
      s"Hello ${query.get("name")}!"
    } else {
      "Hello world!"
    }

    APIGatewayV2HTTPResponse
      .builder()
      .withStatusCode(200)
      .withBody(body)
      .withHeaders(Map("Content-Type" -> "text/plain").asJava)
      .build()
  }
}
