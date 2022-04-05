lazy val refinedVersion = "0.9.28"
lazy val sttpClientVersion = "3.5.1"
lazy val kindProjectorVersion = "0.13.2"
lazy val circeVersion = "0.13.0"
lazy val json4sVersion = "4.0.5"
lazy val enumeratumVersion = "1.7.0"

lazy val scalaTestVersion = "3.2.11"
lazy val testContainersVersion = "0.40.5"
lazy val logbackVersion = "1.2.11"

val scala2_12 = "2.12.14"
val scala2_13 = "2.13.6"

val compileAndTest = "compile->compile;test->test"

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
  scalaVersion := scala2_13,
  crossScalaVersions := Seq(scala2_12, scala2_13)
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
})

lazy val scala212CompilerOptions = Seq(
  "-Yno-adapted-args",
  "-Ywarn-unused-import",
  "-Xfuture"
)

lazy val scala213CompilerOptions = Seq(
  "-Wunused:imports"
)

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  ),
  scalacOptions ++= compilerOptions(scalaVersion.value),
  addCompilerPlugin(("org.typelevel" %% "kind-projector" % kindProjectorVersion).cross(CrossVersion.full)),
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
    circe,
    json4s,
    sprayJson
  )

lazy val core = project
  .in(file("modules/core"))
  .settings(allSettings)
  .settings(moduleName := "consul4s-core")
  .settings(
    libraryDependencies ++= Seq(
      "eu.timepit" %% "refined" % refinedVersion,
      "com.softwaremill.sttp.client3" %% "core" % sttpClientVersion,
      "com.beachape" %% "enumeratum" % enumeratumVersion,
      "com.softwaremill.sttp.client3" %% "slf4j-backend" % sttpClientVersion % Test,
      "com.dimafeng" %% "testcontainers-scala" % testContainersVersion % Test,
      "ch.qos.logback" % "logback-classic" % logbackVersion % Test
    )
  )

lazy val circe = project
  .in(file("modules/circe"))
  .settings(moduleName := "consul4s-circe")
  .settings(allSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "circe" % sttpClientVersion
    )
  )
  .dependsOn(core % compileAndTest)

lazy val json4s = project
  .in(file("modules/json4s"))
  .settings(moduleName := "consul4s-json4s")
  .settings(allSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "json4s" % sttpClientVersion,
      "org.json4s" %% "json4s-jackson" % json4sVersion
    )
  )
  .dependsOn(core % compileAndTest)

lazy val sprayJson = project
  .in(file("modules/spray-json"))
  .settings(moduleName := "consul4s-spray-json")
  .settings(allSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "spray-json" % sttpClientVersion
    )
  )
  .dependsOn(core % compileAndTest)

lazy val examples = project
  .in(file("examples"))
  .settings(moduleName := "examples")
  .settings(allSettings)
  .settings(noPublish)
  .settings(
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-cats" % sttpClientVersion
    )
  )
  .dependsOn(core, circe)
