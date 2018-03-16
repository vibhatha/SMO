# Accuracy Per Data Set

In this folder, the final prediction accuracy per each model based on the data set type is
saved. 

The hierarchy is as follows:

#### DATA SOURCE / MODEL VERSION / MODEL TYPE 

#### ex : heart/1/negative/
#### ex : heart/1/positive/
#### ex : heart/1/zero


## Terminologies

1. Data Source : The name of the dataset
2. Model Version : The model version depends on the number of paritions selected for training
3. Model Type : 
    1. Positive - Sub data set from Data Source with Positive Corelation Coefficient
    2. Negative - Sub data set from Data Source with Negative Corelation Coefficient
    3. Zero - Sub data set from Data Source with Zero Corelation Coefficient