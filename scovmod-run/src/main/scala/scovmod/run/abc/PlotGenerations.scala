package scovmod.run.abc

import sampler.io.Melter
import java.nio.file.Paths
import java.io.BufferedWriter
import java.nio.file.Files
import java.nio.charset.Charset
import java.nio.file.StandardOpenOption

import sampler.r.script.RScript

object PlotGenerations extends App{
  //val dir = Paths.get("results/20151220_FirstAttempt")
  val dir = Paths.get("results/Attempt4")
  val melted = Melter.dir(dir.resolve("raw"), "glob:**/Gen*.csv", "Generation")
  import scala.collection.JavaConversions._
  val meltedFile = dir.resolve("melted.csv")
  val writer = Files.write(meltedFile, melted.map(_.mkString(",")), Charset.defaultCharset, StandardOpenOption.CREATE)
  
  val rScript =
s"""
require("ggplot2")
require("reshape")
pdf("Posterior.pdf", width=11.7, height=8.3)
ggplot(read.csv("${meltedFile.toAbsolutePath}"), aes(x=value, colour=Generation)) +
  geom_density(adjust=100) +
  scale_colour_hue(h=c(-300, 0)) +
  facet_wrap(~ variable, scales="free")  
dev.off()
"""
  RScript(rScript, dir.resolve("script.r"))
}