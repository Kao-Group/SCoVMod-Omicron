pdf("spatial.pdf", height=6.3, width=11.7)
lapply(c("ggplot2", "scales", "reshape2", "jsonlite", "plyr", "GGally", "cluster", "purrr", "dplyr", "RColorBrewer"), require, character.only=T)

badgerAbundance = read.csv("badgersAbundancePerHexCell.csv")

ggplot(badgerAbundance, aes(cellEasting, cellNorthing, colour=meanBadgerAbundance)) +
  geom_point() +
  scale_colour_gradientn(colours=rainbow(4))  +
  coord_equal() +
  labs(x = "Easting (m)", y = "Northing (m)", fill = " meanBadgerAbundance.") +
  ggtitle("Mean local hex cell badger abundance")


simulated = read.csv("simulatedPerHexCell.csv")
simulated$isSimulated = T
observed = read.csv("observationsPerHexCell.csv")
observed$isSimulated = F
simAndObsData =
    rbind(
        simulated,
        observed
    )

simAndObsData$source = factor(simAndObsData$isSimulated, labels=c("Observed", "Simulated"))

## Join Easting and Northing to cellID
final = merge(badgerAbundance, simAndObsData, by.x = "hexCellID", by.y = "cellID") # Inner-join
final = subset(final, select = -c(meanBadgerAbundance, isSimulated))

## Funky way to relabel the values of a factor
final[, "variable"] =
    revalue(
        final[, "variable"],
        c(
            "INF_FROM_WILDLIFE"="From_Wildlife",
            "INF_FROM_CATTLE"="From_Cattle"
        )
    )


## To do grey cell background
allCells = badgerAbundance[,c("cellEasting", "cellNorthing")]

pointSize = 0.1

colfunc <- colorRampPalette(rev(brewer.pal(11, "Spectral")))


plotInfectionEvents = function(data){
    ggplot(data, aes(cellEasting, cellNorthing, colour=value)) +
       geom_point(data=allCells, aes(cellEasting, cellNorthing), color='lightgrey', size=pointSize)+
       geom_point(size=pointSize) +
       facet_grid(variable ~ year, scales = "free") +
       scale_colour_gradientn(colours=colfunc(10), trans = "log", na.value = "grey50")  +
       labs(x = "Easting (m)", y = "Northing (m)") +
       theme(
        axis.text.x=element_blank(),
        axis.ticks.x=element_blank(),
        axis.text.y=element_blank(),
        axis.ticks.y=element_blank(),
        aspect.ratio=2,
        panel.grid.major=element_blank(),
        panel.grid.minor=element_blank(),
        panel.background=element_blank()
       ) +
       ggtitle("Infection Events")
}

plotSimObsComparison = function(allData,variableNames){
    data = allData[allData$variable %in% variableNames,]
    ggplot(data, aes(cellEasting, cellNorthing, colour=value)) +
       geom_point(data=allCells, aes(cellEasting, cellNorthing), color='lightgrey', size=pointSize)+
       geom_point(size=pointSize) +
       facet_grid(source ~ year, scales = "free") +
       scale_colour_gradientn(colours=colfunc(10), trans = "log", na.value = "grey50")  +
       labs(x = "Easting (m)", y = "Northing (m)") +
       theme(
        axis.text.x=element_blank(),
        axis.ticks.x=element_blank(),
        axis.text.y=element_blank(),
        axis.ticks.y=element_blank(),
        aspect.ratio=2,
        panel.grid.major=element_blank(),
        panel.grid.minor=element_blank(),
        panel.background=element_blank()
       ) +
       ggtitle(variableNames)
}


# Plot of sim only data:  From_{Wildlife/Cattle}
data = final[final$variable %in% c("From_Wildlife","From_Cattle"),]
plotInfectionEvents(data)

# Plots of MEAN_BREAKDOWN_DURATION:  year vs {sim/obs}
plotSimObsComparison(final,"MEAN_BREAKDOWN_DURATION")

# Plots of FAILED_TESTS:  year vs {sim/obs}
plotSimObsComparison(final,"FAILED_TESTS")

# Plots of REACTORS:  year vs {sim/obs}
plotSimObsComparison(final,"REACTORS")



if(F){
        colfunc <- colorRampPalette(rev(brewer.pal(11, "Spectral")))
        colourTrans_trans = function() {trans_new("colourTrans", function(x) x^0.3, function(x) x^0.3)}

        simulatedOnly = subset(final[final$source %in% "Simulated",], select = -source)
        simulatedOnly<-simulatedOnly[!(simulatedOnly$variable=="From_Cattle"),]
        simulatedOnly<-simulatedOnly[!(simulatedOnly$variable=="From_Wildlife"),]
        unmelted = dcast(simulatedOnly, hexCellID + year ~ variable, value.var="value")
        unmelted = subset(unmelted, select = -c(hexCellID, year))
        unmelted$BREAKDOWN_DURATION=as.double(unmelted$BREAKDOWN_DURATION)

        head(unmelted)

        varNames = names(unmelted)
        numVars = length(varNames)
        plotList <- list()
        counter = 0
        dp <- function(x, k) format(round(x, k), nsmall=k)

        correlation = function(cellData, decimalPlaces) {
          cor = cor(cellData)[[2]]
          dp(cor, decimalPlaces)
        }

        for (i in 1:numVars) {
          for (j in 1:numVars) {
            counter = (i - 1) * numVars + j
            print(paste(i, j))
            cellData = unmelted[,c(varNames[i],varNames[j])]

            if(i == j){
                obs = melt(cellData[,1])
                plotList[[counter]] <- ggplot(obs, aes(value)) +
                  geom_line(stat="density")
            } else if(i < j) {
                plotList[[counter]] <- ggally_text(correlation(cellData, 2))
            } else {
                plotList[[counter]] <- ggplot(
                    cellData,
                    aes_string(x=names(cellData)[2], y=names(cellData)[1])
                  ) +
                  stat_density_2d(geom = "raster", aes(fill = ..density..), contour = FALSE) +
                  scale_fill_gradientn(colours=colfunc(100), trans = "colourTrans")
            }
          }
        }


        ggmatrix(
            plotList,
            numVars,
            numVars,
            xAxisLabels = varNames,
            yAxisLabels = varNames,
            showYAxisPlotLabels = FALSE,
            showXAxisPlotLabels = FALSE
          ) +
          theme(panel.grid.major = element_blank(), panel.grid.minor = element_blank())

}


dev.off()
