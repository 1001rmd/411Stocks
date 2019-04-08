name := """411Stocks"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.8"

javacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-parameters",
  "-Xlint:unchecked",
  "-Xlint:deprecation",
  "-Werror"
)

crossScalaVersions := Seq("2.11.12", "2.12.7")

libraryDependencies += guice

//Database
libraryDependencies += jdbc
libraryDependencies += "io.ebean" % "ebean" % "4.1.0"

// https://mvnrepository.com/artifact/org.hibernate/hibernate-annotations
libraryDependencies += "org.hibernate" % "hibernate-annotations" % "3.2.1.ga"

libraryDependencies += "org.json" % "json" % "20180813"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.7.2"

// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.197"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.11.1" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "3.1.3" % Test

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
