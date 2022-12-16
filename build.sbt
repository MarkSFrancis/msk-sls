javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

lazy val helloHandler = (project in file("app/HelloHandler")).settings(
  name := "HelloHandler",
  version := "1.0",
  scalaVersion := "2.12.17",
  retrieveManaged := true,
  libraryDependencies += "com.amazonaws" % "aws-lambda-java-core" % "1.2.2",
  libraryDependencies += "com.amazonaws" % "aws-lambda-java-events" % "3.11.0"
)

lazy val infrastructure = (project in file("infrastructure"))
  .settings(
    name := "Infrastructure",
    version := "1.0",
    scalaVersion := "2.12.17",
    retrieveManaged := true,
    libraryDependencies += "software.amazon.awscdk" % "aws-cdk-lib" % "2.52.0"
  )

ThisBuild / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.first
}
