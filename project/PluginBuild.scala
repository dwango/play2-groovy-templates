import sbt._
import Keys._

object PluginBuild extends Build {

  val buildVersion = "1.6.3-SNAPSHOT"


  val delvingReleases = "Delving Releases Repository" at "http://nexus.delving.org/nexus/content/repositories/releases"
  val delvingSnapshots = "Delving Snapshot Repository" at "http://nexus.delving.org/nexus/content/repositories/snapshots"

  val dependencies = Seq(
    "com.typesafe.play"              %% "play"                         % "2.2.0",
    "com.typesafe.play"              %% "play-java"                    % "2.2.0",
    "com.typesafe.play"              %% "templates"                    % "2.2.0",
    "com.typesafe.play"              %% "play-cache"                   % "2.2.0",
    "eu.delving"                     %  "groovy-templates-engine"      % "0.7.5",
    "commons-io"                     %  "commons-io"                   % "2.0",
    "com.googlecode.htmlcompressor"  %  "htmlcompressor"               % "1.5.2",
    "com.google.javascript"          %  "closure-compiler"             % "r1043",
    "com.yahoo.platform.yui"         %  "yuicompressor"                % "2.4.6",
   ("org.reflections"                %  "reflections"                  % "0.9.8" notTransitive())
     .exclude("com.google.guava", "guava")
     .exclude("javassist", "javassist")
   )

  lazy val root = Project(
    id = "root",
    base = file(".")
  ).settings(
    publish := { },
    scalaVersion := "2.10",
    scalaBinaryVersion := CrossVersion.binaryScalaVersion("2.10")
  ).aggregate(templatesSbtPlugin, main)

  lazy val main = Project(
    id = "groovy-templates-plugin",
    base = file("src/templates-plugin")).settings(
      organization := "eu.delving",

      version := buildVersion,

      scalaVersion := "2.10",

      scalaBinaryVersion := CrossVersion.binaryScalaVersion("2.10"),

      resolvers += delvingReleases,

      resolvers += delvingSnapshots,
      
      resolvers += "Typesafe releases" at "http://repo.typesafe.com/typesafe/releases/",
      resolvers += Resolver.file("local-ivy-repo", file(Path.userHome + "/.ivy2/local"))(Resolver.ivyStylePatterns),

      libraryDependencies ++= dependencies,

      publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.jenkins/jobs/play2-groovy-templates-deploy/workspace/maven_repo"))),

      publishMavenStyle := true,

      publishArtifact in (Compile, packageDoc) := false
    )

    lazy val templatesSbtPlugin = Project(
      id="groovy-templates-sbt-plugin",
      base=file("src/sbt-plugin")
    ).settings(
      sbtPlugin := true,

      organization := "eu.delving",

      version := buildVersion,

      scalaVersion := "2.10",

      scalaBinaryVersion := CrossVersion.binaryScalaVersion("2.10"),

      publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.jenkins/jobs/play2-groovy-templates-deploy/workspace/maven_repo"))),

      publishMavenStyle := true,

      publishArtifact in (Compile, packageDoc) := false
    )



}
