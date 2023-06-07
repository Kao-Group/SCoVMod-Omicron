pdf("riskAreaFit_godfray.pdf", height=8.3, width=11.7)

lapply(
  c(
    "ggplot2",
    "reshape2",
    "plyr"
  ),
  library, character.only=T
)

##simulatedWithReps = ldply(lapply(Sys.glob("countyValues_*.csv"), function(file) read.csv(file, quote="")))
## Average the reps first
##simulated = ddply(simulatedWithReps,c("year", "measurement", "county", "run_id"), summarise, value = mean(value))

simulated = ldply(lapply(Sys.glob("countyValues_*.csv"), function(file) read.csv(file, quote="")))

counties <- read.csv("counties.csv")
counties <- subset(counties, select = c(county_id, risk_area))

withRiskArea = merge(simulated, counties, by.x = "county", by.y = "county_id", all.x = T)

riskAreaSums = ddply(withRiskArea, c("year", "measurement", "risk_area", "run_id"), summarise, simulated = sum(value))

riskAreaAnnualAndEdge = ddply(riskAreaSums[riskAreaSums$risk_area %in% c('Annual','Edge'),], c("year", "measurement", "run_id"), summarise, simulated = sum(simulated))
riskAreaAnnualAndEdge$risk_area = rep('Annual_and_Edge',nrow(riskAreaAnnualAndEdge))
riskAreaWales = riskAreaSums[riskAreaSums$risk_area %in% 'Wales', ]
riskAreaLIA = riskAreaSums[riskAreaSums$risk_area %in% 'LIA', ]
riskAreaScotland = riskAreaSums[riskAreaSums$risk_area %in% 'Scotland', ]
riskAreaWalesAndScotland = rbind(riskAreaWales,riskAreaScotland)
riskAreaWalesScotlandLIA = rbind(riskAreaWalesAndScotland,riskAreaLIA)
riskAreaSums = rbind(riskAreaWalesScotlandLIA,riskAreaAnnualAndEdge)

### Add failed location tests to the data, as sum of breakdowns and failed SITs
# riskAreaSums has columns: year measurement risk_area run_id simulated
# So to get Failed_Location_Tests we filter to just breakdown and failed SIT tests and aggregate over all columns except 'measurement'
failedLocationTests = ddply(riskAreaSums[riskAreaSums$measure %in% c('BREAKDOWNS','FAILED_SIT'),], c("year", "risk_area", "run_id"), summarise, FAILED_LOCATION = sum(simulated))
melted = melt(failedLocationTests, id=c("year", "risk_area", "run_id"), value.name="simulated", variable.name="measurement")
riskAreaSums = rbind(riskAreaSums, melted)
riskAreaSums<-riskAreaSums[!(riskAreaSums$measurement=="FAILED_TESTS"),] # remove failed tests

nationalSums = ddply(riskAreaSums, c("year", "measurement", "run_id"), summarise, simulated = sum(simulated))
levels(riskAreaSums$risk_area) = c(levels(riskAreaSums$risk_area), "National")
nationalSums$risk_area = factor("National", levels = levels(riskAreaSums$risk_area))

# Now that we have a national sum - remove risk areas that are not of interest for Godfray - i.e. everything except annual_edge
riskAreaSums<-riskAreaSums[!(riskAreaSums$risk_area=="Wales"),] # remove Wales
riskAreaSums<-riskAreaSums[!(riskAreaSums$risk_area=="Scotland"),] # remove Scotland
riskAreaSums<-riskAreaSums[!(riskAreaSums$risk_area=="LIA"),] # remove LIA

totals = rbind(
    riskAreaSums[c("year", "measurement", "risk_area", "run_id", 'simulated')],
    nationalSums[c("year", "measurement", "risk_area", "run_id", 'simulated')]
)

simPercentiles = ddply(totals, c("year", "measurement", "risk_area"), function(x) quantile(x$simulated, c(0.025,0.1, 0.5, 0.9, 0.975)))
names(simPercentiles)[names(simPercentiles) == "50%"] = "sim median"

observed = read.csv("observations.csv")
observed <- setNames(observed, c("year","county","value","measurement"))
obsWithRiskArea = merge(observed, counties, by.x = "county", by.y = "county_id", all.x = T)

obsSums = ddply(
  obsWithRiskArea,
  c("year", "measurement", "risk_area"),
  summarise,
  observed = sum(value)
)

riskAreaAnnualAndEdgeObs = ddply(obsSums[obsSums$risk_area %in% c('Annual','Edge'),], c("year", "measurement"), summarise, observed = sum(observed))
riskAreaAnnualAndEdgeObs$risk_area = rep('Annual_and_Edge',nrow(riskAreaAnnualAndEdgeObs))
riskAreaWalesObs = obsSums[obsSums$risk_area %in% 'Wales', ]
riskAreaLIAObs = obsSums[obsSums$risk_area %in% 'LIA', ]
riskAreaScotlandObs = obsSums[obsSums$risk_area %in% 'Scotland', ]
riskAreaWalesAndScotlandObs = rbind(riskAreaWalesObs,riskAreaScotlandObs)
riskAreaWalesScotlandLIAObs = rbind(riskAreaWalesAndScotlandObs,riskAreaLIAObs)
obsSums = rbind(riskAreaWalesScotlandLIAObs,riskAreaAnnualAndEdgeObs)

obsFailedLocationTests = ddply(obsSums[obsSums$measure %in% c('BREAKDOWNS','FAILED_SIT'),], c("year", "risk_area"), summarise, FAILED_LOCATION = sum(observed))
meltedObs = melt(obsFailedLocationTests, id=c("year", "risk_area"), value.name="observed", variable.name="measurement")
obsSums = rbind(obsSums, meltedObs)
obsSums<-obsSums[!(obsSums$measurement=="FAILED_TESTS"),] # remove failed tests

obsNationalSums = ddply(obsSums, c("year", "measurement"), summarise, observed = sum(observed))
levels(obsSums$risk_area) = c(levels(obsSums$risk_area), "National")
obsNationalSums$risk_area = factor("National", levels = levels(obsSums$risk_area))

# Now that we have an observed national sum - remove risk areas that are not of interest for Godfray - i.e. everything except annual_edge
obsSums<-obsSums[!(obsSums$risk_area=="Wales"),] # remove Wales
obsSums<-obsSums[!(obsSums$risk_area=="Scotland"),] # remove Scotland
obsSums<-obsSums[!(obsSums$risk_area=="LIA"),] # remove LIA

observed = rbind(
    obsSums[c("year", "measurement", "risk_area", 'observed')],
    obsNationalSums[c("year", "measurement", "risk_area", 'observed')]
)
final = merge(simPercentiles, observed, by = c("year", "measurement", "risk_area"), all = T)

fewSimulations = simulated[simulated$run_id %in% sample(unique(simulated$run_id),2), ]

keyStats = c("BREAKDOWNS", "REACTORS", "FAILED_LOCATION", "REACTORS_GAMMA")
data = final[!(final$measurement %in% keyStats), ]

plotKeyStuffGrid = function(data){
 ggplot(melt(data, id = c("year", "measurement", "risk_area", "2.5%", "10%", "90%", "97.5%") ), aes(x = year)) +
 geom_ribbon(aes(ymin = `2.5%`, ymax = `97.5%`, fill = "95%")) +
   geom_ribbon(aes(ymin = `10%`, ymax = `90%`, fill = "80%")) +
   facet_grid(measurement ~ risk_area, scales = "free") +
   geom_line(aes(y = value, linetype = variable), size = 1) +
   scale_linetype_manual("", values = c("sim median" = 3, "observed" = 1)) +
   scale_fill_manual("Sim variation", values = c("95%" = "lightblue2", "80%" = "lightblue3")) +
   scale_x_continuous(labels = function(x) {paste("'", substring(sapply(x, toString), 3, 4), sep = "")})
 }

 plotKeyStuffGrid(final[final$measurement %in% keyStats, ])

plotKeyStuff = function(data){
 ggplot(melt(data, id = c("year", "measurement", "risk_area", "2.5%", "10%", "90%", "97.5%") ), aes(x = year)) +
 geom_ribbon(aes(ymin = `2.5%`, ymax = `97.5%`, fill = "95%")) +
   geom_ribbon(aes(ymin = `10%`, ymax = `90%`, fill = "80%")) +
   facet_wrap(measurement ~ risk_area, scales = "free") +
   geom_line(aes(y = value, linetype = variable), size = 1) +
   scale_linetype_manual("", values = c("sim median" = 3, "observed" = 1)) +
   scale_fill_manual("Sim variation", values = c("95%" = "lightblue2", "80%" = "lightblue3")) +
   scale_x_continuous(labels = function(x) {paste("'", substring(sapply(x, toString), 3, 4), sep = "")})
 }

 plotKeyStuff(final[final$measurement %in% keyStats, ])

 keyStatsNewTests = c("NEW_ROUTINE_SICCT", "NEW_FOLLOWUP_SICCT", "NEW_SICCT_SIT", "NEW_PARA_GAMMA",  "NEW_SERIAL_GAMMA", "NEW_PREMOVEMENT", "NEW_POSTMOVEMENT")
  plotKeyStuffGrid(final[final$measurement %in% keyStatsNewTests, ])

  keyStatsFalsePositives = c("FPOS_BREAKDOWN", "FPOS_SICCT_ANI", "FPOS_PARA_GAMMA_ANI", "FPOS_BOVINEONLY_ANI", "FPOS_SICCT_HERD", "FPOS_PARA_GAMMA_HERD", "FPOS_BOVINEONLY_HERD")
  plotKeyStuffGrid(final[final$measurement %in% keyStatsFalsePositives, ])

###
# National level plots with some simulated trajectories
###

fewSimulations = totals[totals$run_id %in% unique(sample(unique(simulated$run_id), 5, replace = T)), ]
fewSimulations = fewSimulations[fewSimulations$risk_area %in% 'National', ]
fewSimulations = subset(fewSimulations, select = - risk_area)

nationalOnlyData = final[final$risk_area %in% 'National', ]
nationalOnlyData = subset(nationalOnlyData, select = - risk_area)

ggplot(melt(nationalOnlyData, id = c("year", "measurement", "2.5%", "10%", "90%", "97.5%") ), aes(x = year)) +
  geom_ribbon(aes(ymin = `2.5%`, ymax = `97.5%`, fill = "95%")) +
  geom_ribbon(aes(ymin = `10%`, ymax = `90%`, fill = "80%")) +
  annotate("rect", xmin=c(2008, 2014), xmax=c(2011, 2016), ymin=0, ymax=Inf, alpha=0.3, fill="grey") +
  facet_wrap( ~ measurement, scales = "free", ncol = 5) +
  geom_line(aes(y = value, linetype = variable), size = 1, color="black") +
  scale_linetype_manual("", values = c("sim median" = 3, "observed" = 1)) +
  scale_fill_manual("Sim variation", values = c("95%" = "lightblue2", "80%" = "lightblue3")) +
  scale_color_manual(values = c("red", "blue", "darkgreen","red4","navy")) +
  scale_x_continuous(labels = function(x) {paste("'", substring(sapply(x, toString), 3, 4), sep = "")}) +
  geom_line(data = fewSimulations, aes(x = year, y = simulated, group=run_id,col=as.factor(run_id)), alpha = 0.6, size = 0.6) +
  theme_bw() +
  ggtitle("National level statistics with randomly selected simulated trajectories")

  fewSimulations = simulated[simulated$run_id %in% sample(unique(simulated$run_id),3), ]

  ###
  # Risk area level plots with some simulated trajectories - Annual and Edge
  ###

  fewSimulations = totals[totals$run_id %in% unique(sample(unique(simulated$run_id), 5, replace = T)), ]
  fewSimulationsAnnual = fewSimulations[fewSimulations$risk_area %in% 'Annual_and_Edge', ]
  fewSimulationsAnnual = subset(fewSimulationsAnnual, select = - risk_area)

  annualOnlyData = final[final$risk_area %in% 'Annual_and_Edge', ]
  annualOnlyData = subset(annualOnlyData, select = - risk_area)

  ggplot(melt(annualOnlyData, id = c("year", "measurement", "2.5%", "10%", "90%", "97.5%") ), aes(x = year)) +
    geom_ribbon(aes(ymin = `2.5%`, ymax = `97.5%`, fill = "95%")) +
    geom_ribbon(aes(ymin = `10%`, ymax = `90%`, fill = "80%")) +
    annotate("rect", xmin=c(2008, 2014), xmax=c(2011, 2016), ymin=0, ymax=Inf, alpha=0.3, fill="grey") +
    facet_wrap( ~ measurement, scales = "free", ncol = 5) +
    geom_line(aes(y = value, linetype = variable), size = 1, color="black") +
    scale_linetype_manual("", values = c("sim median" = 3, "observed" = 1)) +
    scale_fill_manual("Sim variation", values = c("95%" = "lightblue2", "80%" = "lightblue3")) +
    scale_color_manual(values = c("red", "blue", "darkgreen","red4","navy")) +
    scale_x_continuous(labels = function(x) {paste("'", substring(sapply(x, toString), 3, 4), sep = "")}) +
    geom_line(data = fewSimulationsAnnual, aes(x = year, y = simulated, group=run_id,col=as.factor(run_id)), alpha = 0.6, size = 0.6) +
    theme_bw() +
    ggtitle("Annual and Edge Area statistics with randomly selected simulated trajectories")

###
# Cross plot National
###

simPercentiles = ddply(totals, c("year", "measurement", "risk_area"), function(x) quantile(x$simulated, c(0.025,0.1, 0.5, 0.9, 0.975)))
names(simPercentiles)[names(simPercentiles) == "50%"] = "median"
names(simPercentiles)[names(simPercentiles) == "2.5%"] = "lower"
names(simPercentiles)[names(simPercentiles) == "97.5%"] = "upper"

final = merge(simPercentiles, observed, by = c("year", "measurement", "risk_area"), all = T)

reactorsPerRiskArea = final[final$measurement %in% "REACTORS", ]
failedTestsPerRiskArea = final[final$measurement %in% "FAILED_LOCATION", ]
reactorsNational = reactorsPerRiskArea[reactorsPerRiskArea$risk_area %in% "National", ]
failedTestsNational = failedTestsPerRiskArea[failedTestsPerRiskArea$risk_area %in% "National", ]

merged = merge(reactorsNational, failedTestsNational, by = c("year"), all = T)
merged = merged[!(merged$year=="2006"),] # remove burn in year
merged<-merged[!(merged$year=="2007"),] # remove burn in year
merged = merged[!(merged$year=="2008"),] # remove burn in year
merged<-merged[!(merged$year=="2009"),] # remove burn in year
merged$year = factor(merged$year)

ggplot(data=merged) + # dummy dataset
  geom_errorbarh(data=merged, # horizontal line
                 aes(x=median.x,
                     y=median.y,
                     colour=year,
                     xmin = lower.x,
                     xmax = upper.x,
                     height = 0), size=0.5) +
 		   #  scale_shape_identity() +
  geom_pointrange(data=merged, # vertical line
                  aes(x=median.x,
                      y=median.y,
                      colour=year,
                      ymin = lower.y,
                      ymax = upper.y), size=0.5) +
 		     # scale_shape_identity() +
  geom_point(data=merged,
                aes(x=observed.x,
                    y=observed.y,
                    colour=year), size=3, shape=17) +
  xlab('REACTORS') + ylab('FAILED TESTS') + ggtitle('Reactors Vs Failed Location Tests - National')

###
# Cross plot Annual
###

simPercentiles = ddply(totals, c("year", "measurement", "risk_area"), function(x) quantile(x$simulated, c(0.025,0.1, 0.5, 0.9, 0.975)))
names(simPercentiles)[names(simPercentiles) == "50%"] = "median"
names(simPercentiles)[names(simPercentiles) == "2.5%"] = "lower"
names(simPercentiles)[names(simPercentiles) == "97.5%"] = "upper"

final = merge(simPercentiles, observed, by = c("year", "measurement", "risk_area"), all = T)

reactorsPerRiskArea = final[final$measurement %in% "REACTORS", ]
failedTestsPerRiskArea = final[final$measurement %in% "FAILED_LOCATION", ]
reactorsAnnual = reactorsPerRiskArea[reactorsPerRiskArea$risk_area %in% "Annual_and_Edge", ]
failedTestsAnnual = failedTestsPerRiskArea[failedTestsPerRiskArea$risk_area %in% "Annual_and_Edge", ]

merged = merge(reactorsAnnual, failedTestsAnnual, by = c("year"), all = T)
merged = merged[!(merged$year=="2006"),] # remove burn in year
merged<-merged[!(merged$year=="2007"),] # remove burn in year
merged = merged[!(merged$year=="2008"),] # remove burn in year
merged<-merged[!(merged$year=="2009"),] # remove burn in year
merged$year = factor(merged$year)

ggplot(data=merged) + # dummy dataset
  geom_errorbarh(data=merged, # horizontal line
                 aes(x=median.x,
                     y=median.y,
                     colour=year,
                     xmin = lower.x,
                     xmax = upper.x,
                     height = 0), size=0.5) +
 		   #  scale_shape_identity() +
  geom_pointrange(data=merged, # vertical line
                  aes(x=median.x,
                      y=median.y,
                      colour=year,
                      ymin = lower.y,
                      ymax = upper.y), size=0.5) +
 		     # scale_shape_identity() +
  geom_point(data=merged,
                aes(x=observed.x,
                    y=observed.y,
                    colour=year), size=3, shape=17) +
  xlab('REACTORS') + ylab('FAILED TESTS') + ggtitle('Reactors Vs Failed Location Tests - Annual and Edge Area')

dev.off()
