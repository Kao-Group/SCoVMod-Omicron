pdf("scatterPlot.pdf", height=11.7, width=16.5)

lapply(
  c(
    "ggplot2",
    "reshape2",
    "plyr"
  ),
  library, character.only=T
)

simulated = ldply(lapply(Sys.glob("countyValues_*.csv"), function(file) read.csv(file, quote="")))
observed = read.csv("observations.csv")
observed <- setNames(observed, c("year","county","value","measurement"))
observed$run_id = -1

labelParams = function(df, params) {
	cbind(df, parameters = params)
}

merged = rbind(
	labelParams(observed, "Observed"),
	labelParams(simulated, "Simulated")
)
finalYear = max(merged$year)
selectedYear = 2016 #finalYear
subsetData = merged[merged$year == selectedYear, ]

unmelted = dcast(subsetData, year + county + parameters + run_id ~ measurement, value.var="value")
unmelted = unmelted[order(unmelted$parameters, decreasing=T),]
names(unmelted)[names(unmelted) == 'parameters'] <- 'Source'


ggplot(unmelted, aes(x=FAILED_TESTS / 100, y=REACTORS / 1000, colour=Source)) +
    xlab("Failed Tests (hundreds)") +
    ylab("Reactors (thousands)") +
	geom_point(size = 1) +
	facet_wrap(~ county) +
    ggtitle(paste("Failed Tests vs. Reactors in ", selectedYear, sep = ""))

ggplot(unmelted, aes(x=FAILED_TESTS / 100, y=REACTORS / 1000, colour=Source)) +
    xlab("Failed Tests (free scales)") +
    ylab("Reactors (free scales)") +
	geom_point() +
	facet_wrap(~ county, scales="free") +
	theme(axis.line=element_blank(),
          axis.text.x=element_blank(),
          axis.text.y=element_blank(),
          axis.ticks=element_blank(),
#          axis.title.x=element_blank(),
#          axis.title.y=element_blank(),
          legend.position="none",
#          panel.background=element_blank(),
#          panel.border=element_blank(),
#          panel.grid.major=element_blank(),
#          panel.grid.minor=element_blank(),
#          plot.background=element_blank()
     ) +
    ggtitle(paste("Failed Tests vs. Reactors in ", selectedYear, sep = ""))

dev.off()
