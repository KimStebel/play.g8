scalacOptions ++= (
  "-deprecation" ::
  "-unchecked" ::
  "-Xlint" ::
  "-language:existentials" ::
  "-language:higherKinds" ::
  "-language:implicitConversions" ::
  "-Yno-adapted-args" ::
  Nil
)

fullResolvers ~= {_.filterNot(_.name == "jcenter")}
  
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "$play_version$")
logLevel := Level.Warn

resolvers += Resolver.typesafeRepo("releases")
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "1.1.1")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.6.1")
addSbtPlugin("io.gatling" % "gatling-sbt" % "2.2.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.4")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.3")
addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.2")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")