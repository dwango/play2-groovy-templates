resolvers ++= Seq(
    DefaultMavenRepository,
    Resolver.file("local-ivy-repo", file(Path.userHome + "/.ivy2/local"))(Resolver.ivyStylePatterns),
    Resolver.url("Play", url("http://download.playframework.org/ivy-releases/"))(Resolver.ivyStylePatterns),
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.0")
