
scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.11",
  "com.typesafe.akka" %% "akka-stream" % "2.5.26",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.11",

  "io.getquill" %% "quill-async-postgres" % "3.5.0",

  "org.xerial" % "sqlite-jdbc" % "3.27.2"


)

