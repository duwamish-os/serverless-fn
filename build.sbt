name := "inventory-api"

version := "1.0"

scalaVersion := "2.12.7"

scalacOptions ++= Seq(
  "-Ypartial-unification"
)

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-core" % "1.2.0",
  "org.tpolecat" %% "doobie-core"      % "0.6.0",
  "mysql" % "mysql-connector-java" % "8.0.13"

)

resolvers ++= Seq(
  "maven2" at "http://central.maven.org/maven2"
)
