name := "amz-serverless-InventoryResp"

version := "1.0"

scalaVersion := "2.12.7"

scalacOptions ++= Seq(
  "-Ypartial-unification"
)

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-core" % "1.2.0"
)

resolvers ++= Seq(
  "maven2" at "http://central.maven.org/maven2"
)
