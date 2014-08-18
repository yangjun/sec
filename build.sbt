name := """sec"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  //javaEbean,
  cache,
  //javaWs
  "org.neo4j" %  "neo4j-enterprise" % "2.0.3"
)
