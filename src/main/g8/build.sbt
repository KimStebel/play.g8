import DockerPackaging._
import ReleaseProcess._
import BuildInfo._
import TestingInfo._

val originalJvmOptions = sys.process.javaVmArguments.filter(
  a => Seq("-Xmx", "-Xms", "-XX").exists(a.startsWith)
)

val baseSettings = Seq(
  scalaVersion := "$scala_version$",
  resolvers += "Sonatype Nexus Repository Manager" at "http://nexus.ovotech.org.uk:8081/nexus/content/repositories/releases",
  organization := "com.ovoenergy",
  parallelExecution in Test := false,
  fork in Test := true,
  scalacOptions ++= (
    "-deprecation" ::
    "-unchecked" ::
    "-Xlint" ::
    "-language:existentials" ::
    "-language:higherKinds" ::
    "-language:implicitConversions" ::
    Nil
  ),
  scalacOptions in Test ++= Seq(
    "-Ywarn-unused-import",
    "-Xfatal-warnings"
  ),
  watchSources ~= { _.filterNot(f => f.getName.endsWith(".swp") || f.getName.endsWith(".swo") || f.isDirectory) },
  javaOptions ++= originalJvmOptions,
  ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) },
  shellPrompt := { state =>
    val branch = if(file(".git").exists){
      "git branch".lines_!.find{_.head == '*'}.map{_.drop(1)}.getOrElse("")
    }else ""
    Project.extract(state).currentRef.project + branch + " > "
  },
  fullResolvers ~= {_.filterNot(_.name == "jcenter")},
  resolvers ++= Seq(Opts.resolver.sonatypeReleases)
)

lazy val root = Project(
  "$name$", file(".")
).enablePlugins(PlayScala).settings(
  baseSettings: _*
).settings(
  routesGenerator := StaticRoutesGenerator,
  libraryDependencies ++= Seq(
    "com.typesafe" % "config" % "1.3.1"    
  )
).enablePlugins(GatlingPlugin)
 .withDocker
 .withReleasePipeline
 .settings(Revolver.settings)
 .settings(Lint.settings)
 .withBuildInfo
 .withTestingInfo

addCommandAlias("ci", ";clean;lint:compile;test;itLocal:test;itDocker:test")
addCommandAlias("ciWithCoverage", ";clean;coverage;lint:compile;test;itLocal:test;itDocker:test;coverageReport")


