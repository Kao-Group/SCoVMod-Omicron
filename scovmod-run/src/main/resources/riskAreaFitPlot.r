pdf("healthBoardAreaFit.pdf", height=8.3, width=11.7)

lapply(
  c(
    "ggplot2",
    "reshape2",
    "plyr"
  ),
  library, character.only=T
)

simulated = ldply(lapply(Sys.glob("communityValues_*.csv"), function(file) read.csv(file, quote="")))
total = ddply(simulated, c("day", "measurement", "area", "run_id"), summarise, simulated = sum(value))
simPercentiles = ddply(total, c("day", "measurement", "area"), function(x) quantile(x$simulated, c(0.025,0.1, 0.5, 0.9, 0.975)))
names(simPercentiles)[names(simPercentiles) == "50%"] = "sim median"

areaSums = ddply(simulated, c("day", "measurement", "area", "run_id"), summarise, simulated = sum(value))


 nationalSums = ddply(simulated, c("day", "measurement", "run_id"), summarise, simulated = sum(simulated))
# # levels(simulated$area) = c(levels(simulated$area), "National")
# nationalSums$area = factor("National", levels = levels(simulated$area))

observed = read.csv("observations.csv")
observed$run_id <- NULL
observed <- setNames(observed, c("day","area","measurement","observed"))

final = merge(simPercentiles, observed, by = c("day", "measurement", "area"), all = T)
final[is.na(final)] <- 0
fewSimulations = simulated[simulated$run_id %in% sample(unique(simulated$run_id),3), ]
keyStats = c("DEAD", "NUMBER_IZ_AREAS_WITH_DEAD")
data = final[(final$measurement %in% keyStats), ]

plotKeyStuffGrid = function(data){
 ggplot(melt(data, id = c("day", "measurement", "area", "2.5%", "10%", "90%", "97.5%") ), aes(x = day)) +
 geom_ribbon(aes(ymin = `2.5%`, ymax = `97.5%`, fill = "95%")) +
   geom_ribbon(aes(ymin = `10%`, ymax = `90%`, fill = "80%")) +
   facet_grid( ~ measurement, scales = "free") +
   geom_line(aes(y = value, linetype = variable), size = 1)
#     scale_linetype_manual("", values = c("sim median" = 2, "observed" = 1)) +
#     scale_fill_manual("Sim variation", values = c("95%" = "lightblue2", "80%" = "lightblue3"))
#     scale_x_continuous(labels = function(x) {paste("'", substring(sapply(x, toString), 3, 4), sep = "")})
 }

 plotKeyStuffGrid(final[final$measurement %in% keyStats, ])

plotKeyStuff = function(data){
 ggplot(melt(data, id = c("day", "measurement", "area", "2.5%", "10%", "90%", "97.5%") ), aes(x = day)) +
 geom_ribbon(aes(ymin = `2.5%`, ymax = `97.5%`, fill = "95%")) +
   geom_ribbon(aes(ymin = `10%`, ymax = `90%`, fill = "80%")) +
   facet_wrap(measurement ~ risk_area, scales = "free") +
   geom_line(aes(y = value, linetype = variable), size = 1) +
   scale_linetype_manual("", values = c("sim median" = 3, "observed" = 1)) +
   scale_fill_manual("Sim variation", values = c("95%" = "lightblue2", "80%" = "lightblue3")) +
   scale_x_continuous(labels = function(x) {paste("'", substring(sapply(x, toString), 3, 4), sep = "")})
 }

 plotKeyStuff(final[final$measurement %in% keyStats, ])

# ###
# # National level plots with some simulated trajectories
# ###
#
# fewSimulations = totals[totals$run_id %in% unique(sample(unique(simulated$run_id), 5, replace = T)), ]
# fewSimulations = fewSimulations[fewSimulations$risk_area %in% 'National', ]
# fewSimulations = subset(fewSimulations, select = - risk_area)
#
# nationalOnlyData = final[final$risk_area %in% 'National', ]
# nationalOnlyData = subset(nationalOnlyData, select = - risk_area)
#
# ggplot(melt(nationalOnlyData, id = c("year", "measurement", "2.5%", "10%", "90%", "97.5%") ), aes(x = year)) +
#   geom_ribbon(aes(ymin = `2.5%`, ymax = `97.5%`, fill = "95%")) +
#   geom_ribbon(aes(ymin = `10%`, ymax = `90%`, fill = "80%")) +
#   annotate("rect", xmin=c(2008, 2014), xmax=c(2011, 2016), ymin=0, ymax=Inf, alpha=0.3, fill="grey") +
#   facet_wrap( ~ measurement, scales = "free", ncol = 5) +
#   geom_line(aes(y = value, linetype = variable), size = 1, color="black") +
#   scale_linetype_manual("", values = c("sim median" = 3, "observed" = 1)) +
#   scale_fill_manual("Sim variation", values = c("95%" = "lightblue2", "80%" = "lightblue3")) +
#   scale_color_manual(values = c("red", "blue", "darkgreen","red4","navy")) +
#   scale_x_continuous(labels = function(x) {paste("'", substring(sapply(x, toString), 3, 4), sep = "")}) +
#   geom_line(data = fewSimulations, aes(x = year, y = simulated, group=run_id,col=as.factor(run_id)), alpha = 0.6, size = 0.6) +
#   theme_bw() +
#   ggtitle("National level statistics with randomly selected simulated trajectories")
#
#   fewSimulations = simulated[simulated$run_id %in% sample(unique(simulated$run_id),3), ]


dev.off()
