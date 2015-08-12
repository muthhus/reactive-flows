val reactiveFlows = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning, JavaAppPackaging, DockerPlugin)

organization := "de.heikoseeberger"
name         := "reactive-flows"
licenses     += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

scalaVersion   := "2.11.7"
scalacOptions ++= List(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.8",
  "-encoding", "UTF-8"
)

unmanagedSourceDirectories.in(Compile) := List(scalaSource.in(Compile).value)
unmanagedSourceDirectories.in(Test)    := List(scalaSource.in(Test).value)

val akkaVersion       = "2.4.2-RC1"
libraryDependencies ++= List(
  "com.typesafe.akka"        %% "akka-actor"   % akkaVersion,
  "de.heikoseeberger"        %% "akka-log4j"   % "1.1.0",
  "org.apache.logging.log4j" %  "log4j-core"   % "2.5",
  "com.typesafe.akka"        %% "akka-testkit" % akkaVersion % "test",
  "org.scalatest"            %% "scalatest"    % "2.2.6"     % "test"
)

initialCommands := """|import de.heikoseeberger.reactiveflows._""".stripMargin

git.useGitDescribe := true

import scalariform.formatter.preferences._
scalariformPreferences := scalariformPreferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)

headers := Map("scala" -> de.heikoseeberger.sbtheader.license.Apache2_0("2015", "Heiko Seeberger"))

test.in(Test)         := { scalastyle.in(Compile).toTask("").value; test.in(Test).value }
scalastyleFailOnError := true

coverageMinimum       := 100
coverageFailOnMinimum := true

maintainer in Docker := "Heiko Seeberger"
daemonUser in Docker := "root"
dockerBaseImage      := "java:8"
dockerRepository     := Some("hseeberger")
