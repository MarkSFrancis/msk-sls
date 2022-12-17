package infrastructure

import software.amazon.awscdk.Stack
import software.amazon.awscdk
import awscdk.CfnOutput
import awscdk.services.lambda._
import awscdk.services.apigateway._
import scala.collection.JavaConverters._

class MskSlsStack(parent: awscdk.App, id: String, props: awscdk.StackProps)
    extends Stack(parent, id, props) {

  val scalaVersion = "2.12"

  val helloLambda = Function.Builder
    .create(this, "HelloHandler")
    .runtime(Runtime.JAVA_11)
    .memorySize(512)
    .code(
      LambdaUtils.getCode("HelloHandler", scalaVersion)
    )
    .environment(
      Map(
        "JAVA_TOOL_OPTIONS" ->
          "-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
      ).asJava
    )
    .architecture(Architecture.ARM_64)
    .handler("HelloHandler::handler")
    .timeout(awscdk.Duration.seconds(30))
    .build

  val gateway = LambdaRestApi.Builder
    .create(this, "hello-api")
    .handler(helloLambda)
    .build

  CfnOutput.Builder.create(this, "api-url").value(gateway.getUrl).build
}
