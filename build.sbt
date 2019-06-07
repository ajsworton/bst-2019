name := "bst-2019"

version := "0.1"

scalaVersion := "2.12.8"

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

val monocleVersion = "1.5.0"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.5",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.scalacheck" %% "scalacheck" % "1.14.0" % "test",
)