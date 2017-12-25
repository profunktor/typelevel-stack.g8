lazy val Http4sVersion     = "0.18.0-M7"
lazy val CirceVersion      = "0.9.0-M3"
lazy val DoobieVersion     = "0.5.0-M11"
lazy val H2Version         = "1.4.196"
lazy val LogbackVersion    = "1.2.3"
lazy val ScalaTestVersion  = "3.0.3"
lazy val ScalaCheckVersion = "1.13.4"

lazy val root = (project in file("."))
  .settings(
    organization := "$organization$",
    name := "$name$",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "$scala_version$",
    scalacOptions := Seq(
      "-deprecation",
      "-encoding",
      "UTF-8",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-Ypartial-unification"
    ),
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
      "org.tpolecat"    %% "doobie-scalatest"    % DoobieVersion,

      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,

      "org.scalatest"   %% "scalatest"           % ScalaTestVersion  % Test,
      "org.scalacheck"  %% "scalacheck"          % ScalaCheckVersion % Test
    )
  )

