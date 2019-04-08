name := "translate"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.jsoup" % "jsoup" % "1.11.3",
  "commons-io" % "commons-io" % "2.6",
  "com.atilika.kuromoji" % "kuromoji-ipadic" % "0.9.0",
  "com.atilika.kuromoji" % "kuromoji-naist-jdic" % "0.9.0",
  "org.scalikejdbc" %% "scalikejdbc"       % "3.3.2",
  "com.h2database"  %  "h2"                % "1.4.197",
  "ch.qos.logback"  %  "logback-classic"   % "1.2.3"
)
