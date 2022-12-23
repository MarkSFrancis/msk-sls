package infrastructure.Msk

import software.constructs.Construct
import software.amazon.awscdk.{Stack}
import scala.collection.JavaConverters._
import software.amazon.awscdk.services.iam
import infrastructure.Utils.LambdaFactory

case class MskLambdasConstructProps(msk: MskConstruct);

class MskLambdasConstruct(
    parent: Stack,
    id: String,
    props: MskLambdasConstructProps
) extends Construct(parent, id) {
  val writeRole = new iam.Role(
    this,
    s"${id}-write-role",
    iam.RoleProps.builder
      .description("Grants write access to the msk instance")
      .assumedBy(new iam.ServicePrincipal("lambda.amazonaws.com"))
      .build
  )

  writeRole.addToPolicy(props.msk.iamWritePolicy)

  val write = LambdaFactory
    .createWithDefaults(
      this,
      s"${id}-write-handler",
      "MskWriteHandler",
      "MskWriteHandler",
      "handler"
    )
    .environment(
      Map(
        "KAFKA_ENDPOINT" -> "http://example.com",
        "KAFKA_TOPIC" -> "test-topic"
      ).asJava
    )
    .vpc(props.msk.vpc)
    .role(writeRole)
    .build

  val readRole = new iam.Role(
    this,
    s"${id}-read-role",
    iam.RoleProps.builder
      .description("Grants read access to the msk instance")
      .assumedBy(new iam.ServicePrincipal("lambda.amazonaws.com"))
      .build
  )

  readRole.addToPolicy(props.msk.iamReadPolicy)

  val read = LambdaFactory
    .createWithDefaults(
      this,
      s"${id}-read-handler",
      "MskReadHandler",
      "MskReadHandler",
      "handler"
    )
    .vpc(props.msk.vpc)
    .role(readRole)
    .build
}
