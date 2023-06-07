name := "SCoVMod-run"

version := "0.0.231"

scalaVersion := "2.12.8"

resolvers ++= Seq(
        Resolver.mavenLocal,
        Resolver.bintrayRepo("tearne", "maven")
)

val samplerVersion = "0.3.17-abc-hack"
val modelVersion = "0.0.80"

scalacOptions ++= Seq("-feature", "-deprecation")

libraryDependencies ++= Seq(
        "org.tearne" %% "sampler-core" % samplerVersion,
        "org.tearne" %% "sampler-abc" % samplerVersion,
        "SCoVMod" % "SCoVMod" % modelVersion,
        "software.amazon.awssdk" % "aws-sdk-java" % "2.10.49",
        "org.apache.poi" % "poi-ooxml" % "3.15",
        "org.apache.poi" % "ooxml-schemas" % "1.3", // see http://poi.apache.org/faq.html#faq-N10025
        "com.typesafe.play" %% "play-json" % "2.9.1",
        "com.github.scopt" %% "scopt" % "3.5.0"
)

updateConfiguration in updateSbtClassifiers := (updateConfiguration in updateSbtClassifiers).value.withMissingOk(true)

enablePlugins(JavaAppPackaging) //For univeral native plugin stage/build
