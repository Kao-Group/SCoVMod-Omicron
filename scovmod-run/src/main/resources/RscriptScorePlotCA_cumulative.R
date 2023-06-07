# Deaths per LA

lapply(
  c(
    "readr",
    "ggplot2",
    "tidyverse", 
    "dplyr"
  ),
  library, character.only=T
)

###################
# directory where results are taken from
###################
path <- '/home/tomdoherty/scovmod_20200902_v0094/'
origin_dir <- '/fitAssessment_14_30thMarch/'


simulated = plyr::ldply(lapply(Sys.glob(paste0(path,  origin_dir, "/communityValues_*.csv")), function(file) read_csv(file, quote="")))
head(simulated)
# nb of simulations
Nb.of.run <- max(simulated$run_id)
# correspondance between LA codes and LA names
OA_perZone <- read_csv(paste0(path, '/OA_info_for_movement_model.csv'))
LA_name_Corres <- OA_perZone %>% filter(!duplicated(local_authority)) %>% select(local_authority, local_authority_name)

# reconstructing LA name 
 simulated[nchar(simulated$area)==1,]$area <- paste0('S1200000', simulated[nchar(simulated$area)==1,]$area)
 simulated[nchar(simulated$area)==2,]$area <- paste0('S120000', simulated[nchar(simulated$area)==2,]$area)

# filtering for dead only, ad joining with local auhtority name
Death_simulated <- simulated[simulated$measurement == 'DEAD',]
DZ_simulated <- simulated[simulated$measurement == 'NUMBER_DZ_AREAS_WITH_DEAD',]

simulated_wLA_ID_Dead <- merge(Death_simulated, LA_name_Corres, by.x = 'area', by.y= 'local_authority', all.x=T)
simulated_wLA_ID_DZ <- merge(DZ_simulated, LA_name_Corres, by.x = 'area', by.y= 'local_authority', all.x=T)

# observed data
Observed_LA_all_cum <- read_csv(paste0(path, '/observations_cumsum.csv'))

Observed_LA_all_cum_dead <- Observed_LA_all_cum[Observed_LA_all_cum$measurement == 'DEAD',]
Observed_LA_all_cum_dead$maxDead <- max(Observed_LA_all_cum_dead$cumsumDeath, na.rm = TRUE)
Observed_LA_all_cum_dz <- Observed_LA_all_cum[Observed_LA_all_cum$measurement == 'NUMBER_DZ_AREAS_WITH_DEAD',]
Observed_LA_all_cum_dz$maxDz <- max(Observed_LA_all_cum_dead$cumsumDeath, na.rm = TRUE)

finalDead = merge(simulated_wLA_ID_Dead, Observed_LA_all_cum_dead, by = c("day", "local_authority_name"), all = T)
finalDead <- finalDead[complete.cases(finalDead), ]
finalDead$scoreDead <- ((finalDead$value.x - finalDead$cumsumDeath))^2

finalDeadScore <- finalDead  %>%
	dplyr::group_by(run_id) %>%
        dplyr::mutate(totalDeadScore = sum(scoreDead))

finalDZ = merge(simulated_wLA_ID_DZ, Observed_LA_all_cum_dz, by = c("day", "local_authority_name"), all = T)
finalDZ <- finalDZ[complete.cases(finalDZ), ]
finalDZ$scoreDZ <- ((finalDZ$value.x - finalDZ$cumsumDeath))^2

finalDZScore <- finalDZ  %>%
	 dplyr::group_by(run_id) %>%
	 dplyr::mutate(totalDZScore = sum(scoreDZ))

finalScore = merge(finalDeadScore, finalDZScore, by = c("run_id","local_authority_name","day"), all = T)
finalScore <- finalScore[complete.cases(finalScore), ]
finalScore$scoreTotal <- (finalScore$totalDeadScore + finalScore$totalDZScore)

#calculate median and quantiles value over runs
Nb.of.run <- max(simulated$run_id)
#scoreQuantiles <- plyr::ddply(finalScore, c("local_authority_name"), function(x) quantile(c(x$scoreTotal, rep(1, 50)), c(0.025, 0.1, 0.5, 0.9, 0.975),na.rm = TRUE))
scoreQuantiles <- quantile(finalScore$scoreTotal,c(0.025, 0.1, 0.5, 0.9, 0.975))
head(scoreQuantiles)

#mean_score_run = summarise(finalScore, Average = mean(scoreTotal, na.rm = T))
#mean_score_overall <- summarise(mean_score_run, Average = mean(Average, na.rm = T))
#meanScore = mean_score_overall$Average[1]
#head(meanScore)

#pdf(file=paste0(path, 'fitAssessment_18_28thMarch.pdf'),
#    height=10, width=15)

#ggplot(finalScore, aes(x=scoreTotal)) + geom_density()  + xlim(range(0,100000000)) + geom_vline(xintercept=meanScore, size=1.5, color="red")

dev.off()


rm(list = ls())
