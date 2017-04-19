
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.17",
  "com.typesafe.akka" %% "akka-stream" % "2.4.17",
  "com.typesafe.akka" %% "akka-http" % "10.0.5",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.5",

  "com.typesafe.slick" %% "slick" % "3.2.0",

  "org.xerial" % "sqlite-jdbc" % "3.16.1"


)

