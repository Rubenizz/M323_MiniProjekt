ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.16"

lazy val root = (project in file("."))
  .settings(
    name := "MiniProject_Modul323"
  )

libraryDependencies += "com.lihaoyi" %% "upickle" % "3.1.0"
