import sbt.Keys.{name, publishArtifact, testOptions, _}
import sbt.Resolver

val projectName           = "lambda-samples"
val projectOrg            = "com.virtuslab"
val projectVersion        = "1.0-SNAPSHOT"

name := projectName


lazy val commonSettings = Seq(
  organization := projectOrg,
  version := projectVersion,
  scalaVersion := "2.12.2",
  retrieveManaged := true,
  libraryDependencies ++= Seq(

      // AWS api
      "com.amazonaws" % "aws-lambda-java-core" % "1.1.0",
      "com.amazonaws" % "aws-lambda-java-events" % "1.3.0",
      "org.json4s" %% "json4s-jackson" % "3.5.1",

      "org.scalatest" %% "scalatest" % "3.0.0" % "test",
      // Lambda client libs
      "com.amazonaws" % "aws-java-sdk-lambda" % "1.11.123"
  ),

  scalacOptions := Seq("-Xfatal-warnings", "-feature", "-deprecation"),
  fork in (Test, run) := true,

  //do not generate scaladoc in dist task
  sources in (Compile,doc) := Seq.empty,
  publishArtifact in (Compile, packageDoc) := false,

  resolvers ++= Seq(
    Resolver.sonatypeRepo("snapshots")
  ),

  testOptions in Test += Tests.Argument("-oDF")
)


lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := projectName,
    organization := projectOrg,
    version := projectVersion
  ).
  aggregate(
    bareHello
  )

lazy val bareHello = (project in file("bare-hello")).
  settings(commonSettings: _*).
  settings(
    name := "bare-hello",
    libraryDependencies ++= Seq(
    )
  )

lazy val httpHello = (project in file("http-hello")).
  settings(commonSettings: _*).
  settings(
    name := "http-hello",
    libraryDependencies ++= Seq(

    )
  )


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}

scalastyleConfig := baseDirectory.value / "plugins-conf" / "scalastyle-config.xml"

wartremoverErrors ++= Seq(
//  Wart.Any,
  Wart.Any2StringAdd,
  Wart.AsInstanceOf,
  Wart.EitherProjectionPartial,
  Wart.IsInstanceOf,
  Wart.ListOps,
//  Wart.NonUnitStatements,
  Wart.Null,
//  Wart.OptionPartial,
//  Wart.Product,
  Wart.Return,
//  Wart.Serializable,
//  Wart.Throw,
  Wart.TryPartial,
  Wart.While,
  Wart.Var,
  Wart.JavaConversions
)
