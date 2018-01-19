import Dependencies._

resolvers +=
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
classpathTypes += "maven-plugin"

lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      organization := "be.botkop",
      scalaVersion := "2.12.4",
      version := "0.1.0-SNAPSHOT"
    )),
  name := "scorch",
  libraryDependencies += numsca,
  libraryDependencies += "org.nd4j" % "nd4j-native" % "0.9.2-SNAPSHOT",
  // libraryDependencies += "org.nd4j" % "nd4j-native-platform" % "0.9.2-SNAPSHOT",
  libraryDependencies += scalaTest % Test
)

crossScalaVersions := Seq("2.11.12", "2.12.4")

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

pomIncludeRepository := { _ =>
  false
}

licenses := Seq(
  "BSD-style" -> url("http://www.opensource.org/licenses/bsd-license.php"))

homepage := Some(url("https://github.com/botkop"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/botkop/scorch"),
    "scm:git@github.com:botkop/scorch.git"
  )
)

developers := List(
  Developer(
    id = "botkop",
    name = "Koen Dejonghe",
    email = "koen@botkop.be",
    url = url("https://github.com/botkop")
  )
)

publishMavenStyle := true
publishArtifact in Test := false
// skip in publish := true
