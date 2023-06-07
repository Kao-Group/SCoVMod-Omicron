lapply(
  c(
    "readr",
    "ggplot2",
    "tidyverse",
    "dplyr",
    "reshape2",
    "plyr"
  ),
  library, character.only=T
)

###################
# directory where results are taken from
###################
path <- '/home/tomdoherty/scovmod_20200911_v00104/'
origin_dir <- '/fitAssessment021/'

simulatedRaw = plyr::ldply(lapply(Sys.glob(paste0(path,  origin_dir, "/communityValues_*.csv")), function(file) read_csv(file, quote="")))
# correspondance between LA codes and LA names
OA_perZone <- read_csv(paste0(path, '/OA_info_for_movement_model.csv'))
LA_name_Corres <- OA_perZone %>% filter(!duplicated(local_authority)) %>% select(local_authority, local_authority_name)

# reconstructing LA name
simulatedRaw[nchar(simulatedRaw$area)==1,]$area <- paste0('S1200000', simulatedRaw[nchar(simulatedRaw$area)==1,]$area)
simulatedRaw[nchar(simulatedRaw$area)==2,]$area <- paste0('S120000', simulatedRaw[nchar(simulatedRaw$area)==2,]$area)
# filtering for dead only, ad joining with local auhtority name
Death_simulated <- simulatedRaw[simulatedRaw$measurement == 'DEAD',]
DZ_simulated <- simulatedRaw[simulatedRaw$measurement == 'NUMBER_DZ_AREAS_WITH_DEAD',]

simulated_wLA_ID_Dead <- merge(Death_simulated, LA_name_Corres, by.x = 'area', by.y= 'local_authority', all.x=T)
simulated_wLA_ID_DZ <- merge(DZ_simulated, LA_name_Corres, by.x = 'area', by.y= 'local_authority', all.x=T)
simulated = rbind(
    simulated_wLA_ID_Dead[c("area", "run_id", "day", "measurement", "value", "local_authority_name")],
    simulated_wLA_ID_DZ[c("area", "run_id", "day", "measurement", "value", "local_authority_name")]
)
simulated <- simulated[complete.cases(simulated), ]

total = ddply(simulated, c("day", "measurement", "local_authority_name", "run_id"), summarise, simulated = sum(value))
simPercentiles = ddply(total, c("day", "local_authority_name", "measurement"), function(x) quantile(x$simulated, c(0.025,0.1, 0.5, 0.9, 0.975)))
names(simPercentiles)[names(simPercentiles) == "50%"] = "median"
names(simPercentiles)[names(simPercentiles) == "2.5%"] = "lower"
names(simPercentiles)[names(simPercentiles) == "97.5%"] = "upper"

# observed data
Observed_LA_all_cum <- read_csv(paste0(path, '/observations_cumsum.csv'))
Observed_LA_all_cum_dead <- Observed_LA_all_cum[Observed_LA_all_cum$measurement == 'DEAD',]
obsCASumsDead = ddply(Observed_LA_all_cum_dead, c("day", "measurement","local_authority_name"), summarise, observed = sum(value))
Observed_LA_all_cum_dz <- Observed_LA_all_cum[Observed_LA_all_cum$measurement == 'NUMBER_DZ_AREAS_WITH_DEAD',]
obsCASumsDz = ddply(Observed_LA_all_cum_dz, c("day", "measurement", "local_authority_name"), summarise, observed = sum(value))
observed = rbind(
    obsCASumsDead[c("day", "measurement","observed","local_authority_name")],
    obsCASumsDz[c("day", "measurement","observed","local_authority_name")]
)

final = merge(simPercentiles, observed, by = c("day", "measurement", "local_authority_name"), all = T)
final <- final[complete.cases(final), ]
deathsCA = final[final$measurement %in% "DEAD", ]
dzsCA = final[final$measurement %in% "NUMBER_DZ_AREAS_WITH_DEAD", ]
merged = merge(deathsCA, dzsCA, by = c("day","local_authority_name"), all = T)
merged = merged[!(merged$local_authority_name=="Shetland Islands"),]

pdf(file=paste0(path, 'crossPlot_fittingPeriod.pdf'),
    height=10, width=15)

ggplot(data=merged) + # dummy dataset
   facet_wrap(~ local_authority_name, scales = "free") +
  geom_errorbarh(data=merged, # horizontal line
                 aes(x=median.x,
                     y=median.y,
                     colour=day,
                     xmin = lower.x,
                     xmax = upper.x,
                     height = 0), alpha = 0.4, size=0.1) +
 		   #  scale_shape_identity() +
  geom_pointrange(data=merged, # vertical line
                  aes(x=median.x,
                      y=median.y,
                      colour=day,
                      ymin = lower.y,
                      ymax = upper.y), alpha = 0.4, size=0.1) +
 		     # scale_shape_identity() +
  geom_point(data=merged,
                aes(x=observed.x,
                    y=observed.y,
                    colour=day), alpha = 0.4, size=1, shape=2) +
  xlab('DEATHS') + ylab('NO DZS WITH DEATHS') + ggtitle('Deaths Vs No DZs with Deaths per CA')

dev.off()