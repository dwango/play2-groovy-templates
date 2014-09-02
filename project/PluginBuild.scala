import sbt._
import Keys._

object PluginBuild extends Build {

  val buildVersion = "1.6.4-SNAPSHOT-DWANGO"


  val delvingReleases = "Dwango Repository" at "http://dwango.github.io/"

  val dependencies = Seq(
    "org.scala-lang.modules"         %% "scala-xml"                    % "1.0.2",
    "com.typesafe.play"              %% "play"                         % play.core.PlayVersion.current,
    "com.typesafe.play"              %% "play-java"                    % play.core.PlayVersion.current,
    "com.typesafe.play"              %% "play-cache"                   % play.core.PlayVersion.current,
    "eu.delving"                     %  "groovy-templates-engine"      % "0.7.5",
    "com.github.scala-incubator.io"  %% "scala-io-file"                % "0.4.3",
    "com.googlecode.htmlcompressor"  %  "htmlcompressor"               % "1.5.2",
    "com.google.javascript"          %  "closure-compiler"             % "r1043",
    "com.yahoo.platform.yui"         %  "yuicompressor"                % "2.4.6",
    "commons-io"                     %  "commons-io"                   % "2.0",
   ("org.reflections"                %  "reflections"                  % "0.9.8" notTransitive())
     .exclude("com.google.guava", "guava")
     .exclude("javassist", "javassist")
   )

  lazy val root = Project(
    id = "root",
    base = file(".")
  ).settings(
    publish := { },
    scalaVersion := "2.11.2"
  ).aggregate(templatesSbtPlugin, main)

  lazy val main = Project(
    id = "groovy-templates-plugin",
    base = file("src/templates-plugin")).settings(
      organization := "eu.delving",

      version := buildVersion,

      scalaVersion := "2.11.2",

      resolvers += delvingReleases,
      
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

      scalaVersion := "2.10.0",

      publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.jenkins/jobs/play2-groovy-templates-deploy/workspace/maven_repo"))),

      publishMavenStyle := true,

      publishArtifact in (Compile, packageDoc) := false
    )
  
}
