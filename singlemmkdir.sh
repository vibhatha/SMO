datatype=$1
mkdir model/single/${datatype}
mkdir -p model/single/${datatype}/{1..10}
mkdir -p logs/${datatype}/{1..10}
mkdir -p stats/accuracyPerDataSet/single/${datatype}/{1..10}
mkdir -p stats/predictionResults/single/${datatype}/{1..10}
mkdir -p stats/SDMMTestAccuracies/single/${datatype}/{1..10}
mkdir -p stats/weightedmodels/single/${datatype}/{1..10}
