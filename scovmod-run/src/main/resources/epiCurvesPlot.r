pdf("epiCurves.pdf", height=6.3, width=11.7)

library(ggplot2)
library(plyr)

simulated = ldply(lapply(Sys.glob("countyValues_*.csv"), function(file) read.csv(file, quote="")))
nationalSum = ddply(simulated, c("year", "measurement", "run_id"), summarise, simulated = sum(value))
nationalSumReactors = subset(nationalSum, measurement=="REACTORS")
nationalSumFailedTests = subset(nationalSum, measurement=="FAILED_TESTS")

ggplot(data=nationalSumReactors, aes(x=year, y=simulated, group=run_id, color=factor(run_id))) +
  geom_line() +
  theme(legend.position="none") +
  ggtitle("Epidemic Reactor Curves")

ggplot(data=nationalSumFailedTests, aes(x=year, y=simulated, group=run_id, color=factor(run_id))) +
  geom_line() +
  theme(legend.position="none") +
  ggtitle("Epidemic Failed Test Curves")

dev.off()
