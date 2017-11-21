lazy val Http4sVersion     = "$http4s_version$"
lazy val CirceVersion      = "$circe_version$"
lazy val DoobieVersion     = "$doobie_version$"
lazy val H2Version         = "$h2_version$"
lazy val LogbackVersion    = "$logback_version$"
lazy val ScalaTestVersion  = "$scalatest_version$"
lazy val ScalaCheckVersion = "$scalacheck_version$"

lazy val root = (project in file("."))
  .settings(
    organization := "$organization$",
    name := "$name$",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "$scala_version$",
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "io.circe"        %% "circe-core"          % CirceVersion,
      "io.circe"        %% "circe-generic"       % CirceVersion,
      
      "com.h2database"  %  "h2"                  % H2Version,
      "org.tpolecat"    %% "doobie-core"         % DoobieVersion,
      "org.tpolecat"    %% "doobie-postgres"     % DoobieVersion,
      "org.tpolecat"    %% "doobie-h2"           % DoobieVersion,

      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,

      "org.scalatest"   %% "scalatest"           % ScalaTestVersion  % Test,
      "org.scalacheck"  %% "scalacheck"          % ScalaCheckVersion % Test
    )
  )

