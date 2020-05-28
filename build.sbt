import ReleaseTransformations._

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

lazy val buildSettings = Seq(
  organization := "com.nryanov",
  scalaVersion := "2.13.2",
  crossScalaVersions := Seq("2.12.10", "2.13.2")
)

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false
)

lazy val publishSettings = Seq(
  publishMavenStyle := true,
  publishArtifact := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots".at(nexus + "content/repositories/snapshots"))
    else
      Some("releases".at(nexus + "service/local/staging/deploy/maven2"))
  },
  publishArtifact in Test := false,
//  pgpSecretRing := file("local.secring.gpg"),
//  pgpPublicRing := file("local.pubring.gpg"),
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  releaseIgnoreUntrackedFiles := true,
  licenses := Seq("Apache 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  homepage := Some(url("https://github.com/nryanov/consul4s")),
  autoAPIMappings := true,
  apiURL := Some(url("https://github.com/nryanov/consul4s")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/nryanov/consul4s"),
      "scm:git:git@github.com:nryanov/consul4s.git"
    )
  ),
  releaseVersionBump := sbtrelease.Version.Bump.Minor,
  releaseCrossBuild := true,
  releaseProcess := {
    Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
//      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      releaseStepCommandAndRemaining("+publishSigned"),
      releaseStepCommand("sonatypeBundleRelease"),
      setNextVersion
//      commitNextVersion,
//      pushChanges
    )
  },
  pomExtra :=
    <developers>
      <developer>
        <id>nryanov</id>
        <name>Nikita Ryanov</name>
      </developer>
    </developers>
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
  "-language:postfixOps",
  "-Xfatal-warnings"
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
    "org.slf4j" % "slf4j-api" % slf4jApiVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  ),
  scalacOptions ++= compilerOptions(scalaVersion.value),
  addCompilerPlugin(("org.typelevel" %% "kind-projector" % kindProjectorVersion).cross(CrossVersion.full)),
  Test / parallelExecution := false
)

lazy val allSettings = commonSettings ++ buildSettings ++ publishSettings

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
  .settings(
    name := "consul4s-core",
    libraryDependencies ++= Seq(
      "eu.timepit" %% "refined" % refinedVersion,
      "com.softwaremill.sttp.client" %% "core" % sttpClientVersion,
      "com.softwaremill.sttp.client" %% "slf4j-backend" % sttpClientVersion,
      "com.beachape" %% "enumeratum" % enumeratumVersion,
      "com.dimafeng" %% "testcontainers-scala" % testContainersVersion % Test,
      "ch.qos.logback" % "logback-classic" % logbackVersion % Test
    )
  )

lazy val circe = project
  .in(file("modules/circe"))
  .dependsOn(core % "compile->compile;test->test")
  .settings(allSettings)
  .settings(
    name := "consul4s-circe",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client" %% "circe" % sttpClientVersion
    )
  )

lazy val json4s = project
  .in(file("modules/json4s"))
  .dependsOn(core % "compile->compile;test->test")
  .settings(allSettings)
  .settings(
    name := "consul4s-json4s",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client" %% "json4s" % sttpClientVersion,
      "org.json4s" %% "json4s-jackson" % json4sVersion
    )
  )

lazy val sprayJson = project
  .in(file("modules/spray-json"))
  .dependsOn(core % "compile->compile;test->test")
  .settings(allSettings)
  .settings(
    name := "consul4s-spray-json",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client" %% "spray-json" % sttpClientVersion
    )
  )
