package infrastructure.Utils

import software.amazon.awscdk.services.lambda._
import scala.collection.JavaConverters._
import software.constructs.Construct
import software.amazon.awscdk.Duration

object LambdaFactory {
  val scalaVersion = "2.12"

  private def getCode(projectName: String, scalaVersion: String): AssetCode = {
    val asset =
      s"app/${projectName}/target/scala-${scalaVersion}/${projectName}-assembly-1.0.jar"

    Code.fromAsset(asset)
  }

  def createWithDefaults(
      scope: Construct,
      lambdaId: String,
      projectName: String,
      handlerObjectName: String,
      handlerFuncName: String
  ): Function.Builder = {
    Function.Builder
      .create(scope, lambdaId)
      .runtime(Runtime.JAVA_11)
      .memorySize(512)
      .code(
        this.getCode(projectName, scalaVersion)
      )
      .environment(
        Map(
          "JAVA_TOOL_OPTIONS" ->
            "-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
        ).asJava
      )
      .tracing(Tracing.ACTIVE)
      .architecture(Architecture.ARM_64)
      .handler(s"${handlerObjectName}::${handlerFuncName}")
      .timeout(Duration.seconds(30))
  }
}
