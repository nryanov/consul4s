lazy val refinedVersion = "0.11.0"
lazy val sttpClientVersion = "3.9.5"
lazy val kindProjectorVersion = "0.13.2"
lazy val circeVersion = "0.14.6"
lazy val json4sVersion = "4.0.7"
lazy val enumeratumVersion = "1.7.3"

lazy val scalaTestVersion = "3.2.17"
lazy val testContainersVersion = "0.41.3"
lazy val logbackVersion = "1.5.3"

val scala2_12 = "2.12.14"
val scala2_13 = "2.13.12"
val scala3 = "3.4.1"

val compileAndTest = "compile->compile;test->test"
val crossScala2Versions = Seq(scala2_12, scala2_13)
val crossScalaAllVersions = Seq(scala2_12, scala2_13, scala3)

lazy val buildSettings = Seq(
  sonatypeProfileName := "com.nryanov",
  organization := "com.nryanov.consul4s",
  homepage := Some(url("https://github.com/nryanov/consul4s")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "nryanov",
      "Nikita Ryanov",
      "ryanov.nikita@gmail.com",
      url("https://nryanov.com")
    )
  ),
  scalaVersion := scala2_13
)

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false
)

def compilerOptions(scalaVersion: String) = Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Xlint",
  "-language:existentials",
  "-language:postfixOps"
) ++ (CrossVersion.partialVersion(scalaVersion) match {
  case Some((2, scalaMajor)) if scalaMajor == 12 => scala212CompilerOptions
  case Some((2, scalaMajor)) if scalaMajor == 13 => scala213CompilerOptions
  case Some((3, _))                              => scala3CompilerOptions
})

lazy val scala212CompilerOptions = Seq(
  "-Yno-adapted-args",
  "-Ywarn-unused-import",
  "-Xfuture"
)

lazy val scala213CompilerOptions = Seq(
  "-Wunused:imports"
)

lazy val scala3CompilerOptions = Seq(
)

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  ),
  scalacOptions ++= compilerOptions(scalaVersion.value),
  Test / parallelExecution := false
)

lazy val allSettings = commonSettings ++ buildSettings

lazy val consul4s = project
  .in(file("."))
  .settings(moduleName := "consul4s")
  .settings(allSettings)
  .settings(noPublish)
  .aggregate(
    core,
    examples,
    circe,
    json4s,
    sprayJson
  )

lazy val core = project
  .in(file("modules/core"))
  .settings(allSettings)
  .settings(
    moduleName := "consul4s-core",
    crossScalaVersions := crossScalaAllVersions,
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "core" % sttpClientVersion,
      "com.softwaremill.sttp.client3" %% "slf4j-backend" % sttpClientVersion % Test,
      "com.dimafeng" %% "testcontainers-scala" % testContainersVersion % Test,
      "ch.qos.logback" % "logback-classic" % logbackVersion % Test
    )
  )

lazy val circe = project
  .in(file("modules/circe"))
  .settings(allSettings)
  .settings(
    moduleName := "consul4s-circe",
    crossScalaVersions := crossScalaAllVersions,
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "circe" % sttpClientVersion
    )
  )
  .dependsOn(core % compileAndTest)

lazy val json4s = project
  .in(file("modules/json4s"))
  .settings(allSettings)
  .settings(
    moduleName := "consul4s-json4s",
    crossScalaVersions := crossScala2Versions,
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "json4s" % sttpClientVersion,
      "org.json4s" %% "json4s-jackson" % json4sVersion
    )
  )
  .dependsOn(core % compileAndTest)

lazy val sprayJson = project
  .in(file("modules/spray-json"))
  .settings(allSettings)
  .settings(
    moduleName := "consul4s-spray-json",
    crossScalaVersions := crossScala2Versions,
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "spray-json" % sttpClientVersion
    )
  )
  .dependsOn(core % compileAndTest)

lazy val examples = project
  .in(file("examples"))
  .settings(noPublish)
  .settings(commonSettings)
  .settings(
    moduleName := "examples",
    scalaVersion := scala2_13,
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.5.4",
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-cats" % sttpClientVersion,
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-zio" % sttpClientVersion
    )
  )
  .dependsOn(core, circe)
