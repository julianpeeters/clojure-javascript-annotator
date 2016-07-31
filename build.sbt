name := "clojure-javascript-annotator"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(play.PlayScala).settings()

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.webjars" %% "webjars-play" % "2.3.0-3",
  "org.webjars" % "bootstrap" % "2.3.0",
  "org.webjars" % "font-awesome" % "4.3.0-1"
)

// needed for large schemas
javaOptions in run += "-Dhttp.netty.maxInitialLineLength=8192"
