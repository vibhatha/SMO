# SDMMTestAccuracies

In this section, the Single Data Multiple Model Test Accuracies are being 
calculated. There is a hierarchy in saving the test accuracies. 

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
    
Thd SDMMTestAccuracies depends on the selected model for testing. In this single model
approach only a single test data set is used to calculate the weights. 

