package infrastructure

import software.amazon.awscdk.Stack
import software.amazon.awscdk
import awscdk.CfnOutput
import awscdk.services.ec2.Vpc
import awscdk.services.msk.{CfnServerlessCluster}
import awscdk.services.apigatewayv2.alpha._
import software.amazon.awscdk.services.apigatewayv2.integrations.alpha._
import scala.collection.JavaConverters._
import awscdk.services.iam

class MskSlsStack(parent: awscdk.App, id: String, props: awscdk.StackProps)
    extends Stack(parent, id, props) {
  val msk = new Msk.MskConstruct(this, "msk-sls")
  val ssm = new SsmConfig.SsmConstruct(this, "ssm")
  val mskLambdas = new Msk.MskLambdasConstruct(
    this,
    "msk-lambdas",
    new Msk.MskLambdasConstructProps(msk)
  )

  val api = new Api.MskApiConstruct(
    this,
    "msk-api",
    new Api.MskApiConstructProps(mskLambdas)
  )

  CfnOutput.Builder
    .create(this, "msk-api-url")
    .value(api.apiGateway.getUrl)
    .build
}
