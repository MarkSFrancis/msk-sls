package infrastructure

import software.amazon.awscdk.Stack
import software.amazon.awscdk
import awscdk.CfnOutput
import awscdk.services.lambda._
import awscdk.services.apigateway._

class MskSlsStack(parent: awscdk.App, id: String, props: awscdk.StackProps)
    extends Stack(parent, id, props) {

  val scalaVersion = "2.12"

  val helloLambda = Function.Builder
    .create(this, "HelloHandler")
    .runtime(Runtime.JAVA_11)
    .architecture(Architecture.ARM_64)
    .code(
      Code.fromAsset(
        s"app/HelloHandler/target/scala-${scalaVersion}/HelloHandler-assembly-1.0.jar"
      )
    )
    .handler("HelloHandler::handler")
    .build

  val gateway = LambdaRestApi.Builder
    .create(this, "hello-api")
    .handler(helloLambda)
    .build

  CfnOutput.Builder.create(this, "api-url").value(gateway.getUrl).build
}
