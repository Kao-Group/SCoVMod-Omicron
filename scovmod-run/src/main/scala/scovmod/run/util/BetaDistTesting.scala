package scovmod.run.util

import java.nio.file.{Files, Paths}

import org.apache.commons.io.FileUtils
import org.apache.commons.math3.distribution.BetaDistribution
import org.apache.commons.math3.random.{MersenneTwister, RandomGenerator, SynchronizedRandomGenerator}
import sampler.r.script.RScript

object BetaDistTesting extends App {
  val wd = Paths.get("out", "betaTesting")
  if(!Files.exists(wd)) Files.createDirectories(wd)

  val numSamples = 999999

  val a = 5
  val b = 15 / 7.0 // Calculated from a mean of 0.7)

  val beta = {
    val syncRand: RandomGenerator = new SynchronizedRandomGenerator(new MersenneTwister())
    new BetaDistribution(syncRand, a, b)
  }
  import scala.collection.JavaConverters._
  val apacheSamples = (1 to numSamples).map(_ =>beta.sample()).toSeq.map(_.toString).asJava

  val densityPoints = (1 to 1000).map(_ / 1000.0).map(x => beta.density(x).toString).asJava

  FileUtils.writeLines(wd.resolve("apache.samples").toFile, apacheSamples)
  FileUtils.writeLines(wd.resolve("apache.density").toFile, densityPoints)

  val rScript =
    s"""
      |lapply(c("ggplot2", "reshape2"), require, character.only=T)
      |
      |samples = data.frame(r = rbeta($numSamples, $a, $b), read.csv("apache.samples", header=F, col.names=c("apache")))
      |density = data.frame(x = seq(0, 1, length.out=1000), r = dbeta(seq(0, 1, length.out=1000), $a, $b), read.csv("apache.density", header=F, col.names=c("apache")))
      |
      |fileName = paste(format(Sys.Date(), "%Y%m%d"), "out.pdf", sep="_")
      |pdf(fileName, width=7, height=7)
      |
      |ggplot(melt(samples)) +
      | geom_density(aes(x=value, colour=variable))
      |
      |ggplot(melt(density, id.vars=c("x")))+
      | geom_line(aes(x=x, y=value)) +
      | facet_grid(variable ~ .)
      |
      |dev.off()
      |
      |quantile(samples$$r, probs = c(0.025, 0.975))
      |quantile(samples$$apache, probs = c(0.025, 0.975))
      |
    """.stripMargin
  RScript(rScript, wd.resolve("script.r"))
}
