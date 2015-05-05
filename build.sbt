name := """Hulinalu"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

includeFilter in (Assets, LessKeys.less) := "*.less"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "org.postgresql" % "postgresql" % "9.3-1102-jdbc4"
)

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"

