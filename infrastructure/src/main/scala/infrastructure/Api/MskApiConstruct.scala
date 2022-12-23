package infrastructure.Api

import software.constructs.Construct
import software.amazon.awscdk.{Stack}
import scala.collection.JavaConverters._
import software.amazon.awscdk.services.iam
import infrastructure.Utils.LambdaFactory
import infrastructure.Msk.MskLambdasConstruct
import software.amazon.awscdk.services.apigatewayv2.integrations.alpha._
import software.amazon.awscdk.services.apigatewayv2.alpha._

case class MskApiConstructProps(mskLambdas: MskLambdasConstruct);

class MskApiConstruct(
    parent: Stack,
    id: String,
    props: MskApiConstructProps
) extends Construct(parent, id) {
  val apiGateway = HttpApi.Builder
    .create(this, "msk-api")
    .build

  private val helloWorldLambda = LambdaFactory
    .createWithDefaults(
      this,
      "hello-handler",
      "HelloHandler",
      "HelloHandler",
      "handler"
    )
    .build

  private val helloIntegration =
    new HttpLambdaIntegration(
      "HelloLambdaIntegration",
      helloWorldLambda
    )

  apiGateway.addRoutes(
    AddRoutesOptions.builder
      .path(
        "/"
      )
      .methods(List(HttpMethod.GET).asJava)
      .integration(helloIntegration)
      .build
  )

  apiGateway.addRoutes(
    AddRoutesOptions.builder
      .path("/events")
      .methods(List(HttpMethod.POST).asJava)
      .integration(
        new HttpLambdaIntegration(
          "MskWriteLambdaIntegration",
          props.mskLambdas.write
        )
      )
      .build
  )
}
