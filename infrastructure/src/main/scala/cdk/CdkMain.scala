package infrastructure

import software.amazon.awscdk.{App, Environment, StackProps}

object CdkMain extends scala.App {
  val app = new App()

  new MskSlsStack(
    app,
    "msk-sls",
    StackProps.builder
      .env(
        Environment.builder
          .account("526856128901")
          .region("us-east-1")
          .build
      )
      .build
  );
}
