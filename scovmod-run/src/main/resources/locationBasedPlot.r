pdf("statsByLocation.pdf", height=6.3, width=11.7)

lapply(c("ggplot2", "scales", "reshape2", "jsonlite", "plyr", "GGally", "cluster", "purrr", "dplyr", "RColorBrewer"), require, character.only=T)

simulated = read.csv("simulatedPerLocation.csv")
simulated$isSimulated = T
observed = read.csv("observedByLocation.csv")

observed$isSimulated = F
simAndObsData =
    rbind(
        simulated,
        observed
    )

simAndObsData$source = factor(simAndObsData$isSimulated, labels=c("Observed", "Simulated"))
final = subset(simAndObsData, select = -c(isSimulated))

plotSimObsComparisonBreakdownDuration = function(){
    ggplot(final, aes(x=proportion,y=..ncount..)) +
        geom_histogram(data=subset(final,source == 'Simulated'),fill = "red", alpha = 0.2) +
        geom_histogram(data=subset(final,source == 'Observed'),fill = "blue", alpha = 0.2) +
    ggtitle("Proportion of time spent in breakdown from 2013 to 2018")
}

plotSimObsComparisonBreakdownDurationFacet = function(){
        ggplot(final) +
        geom_histogram(aes(x=proportion,y=..ncount..)) +
        facet_grid(source ~ ., scales="free") +
         ggtitle("Proportion of time spent in breakdown from 2013 to 2018")
}

plotSimObsComparisonBreakdownDuration()
plotSimObsComparisonBreakdownDurationFacet()

dev.off()
