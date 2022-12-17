package infrastructure

import software.amazon.awscdk.services.lambda._

object LambdaUtils {
  def getCode(projectName: String, scalaVersion: String): AssetCode = {
    val asset =
      s"app/${projectName}/target/scala-${scalaVersion}/${projectName}-assembly-1.0.jar"

    Code.fromAsset(asset)
  }
}
