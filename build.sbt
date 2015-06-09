crossScalaVersions in Global := Seq("2.11.6", "2.12.0-M1", "2.10.5")
scalaVersion in Global <<= (crossScalaVersions in Global)(_.head)

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

libraryDependencies += "org.scalatest" %% "scalatest" % (scalaBinaryVersion.value match {
  case "2.12.0-M1" => "2.2.5-M1"
  case _           => "2.2.5"
}) % Test

conflictManager := ConflictManager.latestCompatible
dependencyOverrides += "org.scala-lang" % "scala-library" % scalaVersion.value
dependencyOverrides += "org.scala-lang" % "scala-reflect" % scalaVersion.value

scalacOptions := Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-explaintypes",
  "-feature",
  "-unchecked",
  "-Xfuture",
  "-Xlint",
  "-Yclosure-elim",
  "-Ydead-code",
  "-Ymacro-debug-lite",
  "-Yno-adapted-args",
  "-Ywarn-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-nullary-override",
  "-Ywarn-nullary-unit",
  "-Ywarn-numeric-widen")
scalacOptions ++= {
  if (scalaBinaryVersion.value == "2.10") Nil
  else Seq(
    "-Xfatal-warnings",
    "-Yconst-opt",
    "-Ywarn-infer-any",
    "-Ywarn-unused",
    "-Ywarn-unused-import"
  )
}
scalacOptions in Test := (scalacOptions in Test).value filterNot (_ == "-Ywarn-dead-code")

testOptions in Test += Tests.Argument("-oF")
