package infrastructure.Msk

import software.constructs.Construct
import software.amazon.awscdk.{Stack}
import software.amazon.awscdk.services.msk.CfnServerlessCluster
import software.amazon.awscdk.services.ec2.Vpc
import scala.collection.JavaConverters._
import software.amazon.awscdk.services.msk.CfnServerlessCluster.ClientAuthenticationProperty
import software.amazon.awscdk.services.msk.CfnServerlessCluster.SaslProperty
import software.amazon.awscdk.services.msk.CfnServerlessCluster.IamProperty
import software.amazon.awscdk.services.iam._

class MskConstruct(parent: Stack, id: String) extends Construct(parent, id) {
  val vpc = Vpc.Builder
    .create(
      this,
      s"${id}-vpc"
    )
    .build

  private val iamAuth = ClientAuthenticationProperty.builder
    .sasl(
      SaslProperty.builder
        .iam(IamProperty.builder.enabled(true).build)
        .build
    )
    .build

  val cluster = CfnServerlessCluster.Builder
    .create(
      this,
      s"${id}-events"
    )
    .clusterName(s"${id}-events")
    .clientAuthentication(iamAuth)
    .vpcConfigs(
      List(
        CfnServerlessCluster.VpcConfigProperty.builder
          .subnetIds(
            vpc.getPrivateSubnets.asScala.map(s => s.getSubnetId).asJava
          )
          .build
      ).asJava
    )
    .build

  val iamReadPolicy = PolicyStatement.Builder.create
    .actions(
      List[String](
        "kafka-cluster:DescribeTopicDynamicConfiguration",
        "kafka-cluster:DescribeCluster",
        "kafka-cluster:ReadData",
        "kafka-cluster:DescribeTopic",
        "kafka-cluster:DescribeTransactionalId",
        "kafka-cluster:DescribeGroup",
        "kafka-cluster:DescribeClusterDynamicConfiguration"
      ).asJava
    )
    .resources(List(cluster.getAttrArn).asJava)
    .build

  val iamWritePolicy = new PolicyStatement(
    PolicyStatementProps.builder
      .actions(List("kafka-cluster:*").asJava)
      .resources(List(cluster.getAttrArn).asJava)
      .build
  )
}
