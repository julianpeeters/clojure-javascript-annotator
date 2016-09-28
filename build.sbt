name := "clojure-javascript-annotator"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala).settings()

libraryDependencies ++= Seq(
  jdbc,
  cache,
  "com.typesafe.play" %% "anorm" % "2.4.0",
  "org.webjars" %% "webjars-play" % "2.5.0",
  "org.webjars" % "bootstrap" % "2.3.0",
  "org.webjars" % "font-awesome" % "4.3.0-1"
)

// needed for large schemas
javaOptions in run += "-Dhttp.netty.maxInitialLineLength=8192"
