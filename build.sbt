import ReleaseTransformations._

lazy val refinedVersion = "0.9.22"
lazy val sttpClientVersion = "3.2.0"
lazy val kindProjectorVersion = "0.11.3"
lazy val circeVersion = "0.13.0"
lazy val json4sVersion = "3.6.11"
lazy val enumeratumVersion = "1.6.1"
lazy val slf4jApiVersion = "1.7.30"

lazy val scalaTestVersion = "3.2.6"
lazy val testContainersVersion = "0.39.3"
lazy val logbackVersion = "1.2.3"

lazy val buildSettings = Seq(
  organization := "com.nryanov.consul4s",
  scalaVersion := "2.13.5",
  crossScalaVersions := Seq("2.12.13", "2.13.5")
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
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      releaseStepCommandAndRemaining("+publishSigned"),
      releaseStepCommand("sonatypeBundleRelease"),
      setNextVersion,
      commitNextVersion
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
    "org.slf4j" % "slf4j-api" % slf4jApiVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  ),
  scalacOptions ++= compilerOptions(scalaVersion.value),
  addCompilerPlugin(("org.typelevel" %% "kind-projector" % kindProjectorVersion).cross(CrossVersion.full)),
  Test / parallelExecution := false
)

lazy val allSettings = commonSettings ++ buildSettings ++ publishSettings

lazy val docSettings = allSettings ++ Seq(
  micrositeName := "consul4s",
  micrositeDescription := "A native Scala client for interacting with Consul built on top of sttp-client",
  micrositeAuthor := "Nikita Ryanov",
  mdocIn := baseDirectory.value / "mdoc",
  micrositeHighlightTheme := "atom-one-light",
  micrositeHomepage := "https://nryanov.github.io/consul4s/",
  micrositeDocumentationUrl := "api",
  micrositeGithubOwner := "nryanov",
  micrositeGithubRepo := "consul4s",
  micrositeGitterChannel := false,
  micrositeBaseUrl := "consul4s",
  ghpagesNoJekyll := false,
  git.remoteRepo := "git@github.com:nryanov/consul4s.git",
  micrositePushSiteWith := GHPagesPlugin,
  micrositeGithubLinks := true,
  mdocIn := sourceDirectory.value / "main" / "mdoc",
  includeFilter in makeSite := "*.html" | "*.css" | "*.png" | "*.jpg" | "*.gif" | "*.svg" | "*.js" | "*.swf" | "*.yml" | "*.md"
)

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
  .dependsOn(core % "compile->compile;test->test")

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
  .dependsOn(core % "compile->compile;test->test")

lazy val sprayJson = project
  .in(file("modules/spray-json"))
  .settings(moduleName := "consul4s-spray-json")
  .settings(allSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "spray-json" % sttpClientVersion
    )
  )
  .dependsOn(core % "compile->compile;test->test")

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

lazy val docs = project
  .settings(docSettings)
  .settings(noPublish)
  .settings(moduleName := "consul4s-docs")
  .settings(
    libraryDependencies ++= Seq()
  )
  .enablePlugins(MicrositesPlugin, ScalaUnidocPlugin)
  .dependsOn(core, circe)
