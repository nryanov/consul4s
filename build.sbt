name := "consul4s"

lazy val refinedVersion = "0.9.14"
lazy val sttpClientVersion = "2.1.1"
lazy val kindProjectorVersion = "0.11.0"
lazy val circeVersion = "0.13.0"
lazy val json4sVersion = "3.6.8"
lazy val enumeratumVersion = "1.6.0"
lazy val slf4jApiVersion = "1.7.25"

lazy val scalaTestVersion = "3.1.1"
lazy val testContainersVersion = "0.36.0"
lazy val logbackVersion = "1.2.3"

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "org.slf4j" % "slf4j-api" % slf4jApiVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  ),
  scalacOptions := Seq(
    "-encoding",
    "utf8",
    "-Xfatal-warnings",
    "-Xlog-implicits",
    "-deprecation",
    "-unchecked",
    "-language:implicitConversions",
    "-language:higherKinds",
    "-language:existentials",
    "-language:postfixOps"
  ),
  scalaVersion := "2.13.2",
  addCompilerPlugin(("org.typelevel" %% "kind-projector" % kindProjectorVersion).cross(CrossVersion.full)),
  Test / parallelExecution := false
)

lazy val consul4s = project
  .in(file("."))
  .settings(commonSettings)
  .aggregate(
    core,
    circe,
    json4s,
    sprayJson
  )

lazy val core = project
  .in(file("modules/core"))
  .settings(commonSettings)
  .settings(
    name := "consul4s-core",
    libraryDependencies ++= Seq(
      "eu.timepit" %% "refined" % refinedVersion,
      "com.softwaremill.sttp.client" %% "core" % sttpClientVersion,
      "com.beachape" %% "enumeratum" % enumeratumVersion,
      "com.dimafeng" %% "testcontainers-scala" % testContainersVersion % Test,
      "ch.qos.logback" % "logback-classic" % logbackVersion % Test
    )
  )

lazy val circe = project
  .in(file("modules/circe"))
  .dependsOn(core % "compile->compile;test->test")
  .settings(commonSettings)
  .settings(
    name := "consul4s-circe",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client" %% "circe" % sttpClientVersion
    )
  )

lazy val json4s = project
  .in(file("modules/json4s"))
  .dependsOn(core % "compile->compile;test->test")
  .settings(commonSettings)
  .settings(
    name := "consul4s-json4s",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client" %% "json4s" % sttpClientVersion,
      "org.json4s" %% "json4s-native" % json4sVersion
    )
  )

lazy val sprayJson = project
  .in(file("modules/spray-json"))
  .dependsOn(core % "compile->compile;test->test")
  .settings(commonSettings)
  .settings(
    name := "consul4s-spray-json",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client" %% "spray-json" % sttpClientVersion
    )
  )
