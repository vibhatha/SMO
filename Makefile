install:
	mvn install

package:
	mvn clean
	mvn package

setupcov:
	if [ ! -d "model" ] ; then mkdir model; fi ;
	if [ ! -d "logs" ] ; then mkdir logs; fi ;
	if [ ! -d "stats" ] ; then mkdir stats; fi ;
	python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_test_y data/covtype/covtype_libsvm_ise_test_y.bin
	python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_train_y data/covtype/covtype_libsvm_ise_train_y.bin
	python scripts/partition.py data/covtype/covtype_libsvm_ise_train_y.bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_test_y.bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_train_x 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_test_x 400


setupcovbin:
	if [ ! -d "model" ] ; then mkdir model; fi ;
	if [ ! -d "logs" ] ; then mkdir logs; fi ;
	if [ ! -d "stats" ] ; then mkdir stats; fi ;
	python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_test_y data/covtype/covtype_libsvm_ise_test_y.bin
	python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_train_y data/covtype/covtype_libsvm_ise_train_y.bin
	python scripts/partition.py data/covtype/covtype_libsvm_ise_train_y.bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_test_y.bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_train_x_bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_test_x_bin 400

setupijcnnbin:
	python scripts/partition.py data/ijcnn1/ijcnn1_isesvm_test_x_bin 40
	python scripts/partition.py data/ijcnn1/ijcnn1_isesvm_train_x_bin 40
	python scripts/partition.py data/ijcnn1/ijcnn1_isesvm_test_y.bin 40
	python scripts/partition.py data/ijcnn1/ijcnn1_isesvm_train_y.bin 40

setupmodelheart:
	python scripts/partition.py data/model_partition/heart/heart_negative_cr_isesvm_train_y.bin 1
	python scripts/partition.py data/model_partition/heart/heart_negative_cr_isesvm_test_y.bin 1
	python scripts/partition.py data/model_partition/heart/heart_negative_cr_isesvm_train_x_bin 1
	python scripts/partition.py data/model_partition/heart/heart_negative_cr_isesvm_test_x_bin 1
	python scripts/partition.py data/model_partition/heart/heart_positive_cr_isesvm_train_y.bin 1
	python scripts/partition.py data/model_partition/heart/heart_positive_cr_isesvm_test_y.bin 1
	python scripts/partition.py data/model_partition/heart/heart_positive_cr_isesvm_train_x_bin 1
	python scripts/partition.py data/model_partition/heart/heart_positive_cr_isesvm_test_x_bin 1

setupmodel-ijcnn1:
	python scripts/partition.py data/model_partition/ijcnn1/ijcnn1_negative_cr_isesvm_train_y.bin 40
	python scripts/partition.py data/model_partition/ijcnn1/ijcnn1_negative_cr_isesvm_test_y.bin 10
	python scripts/partition.py data/model_partition/ijcnn1/ijcnn1_negative_cr_isesvm_train_x_bin 40
	python scripts/partition.py data/model_partition/ijcnn1/ijcnn1_negative_cr_isesvm_test_x_bin 10
	python scripts/partition.py data/model_partition/ijcnn1/ijcnn1_positive_cr_isesvm_train_y.bin 30
	python scripts/partition.py data/model_partition/ijcnn1/ijcnn1_positive_cr_isesvm_test_y.bin 5
	python scripts/partition.py data/model_partition/ijcnn1/ijcnn1_positive_cr_isesvm_train_x_bin 30
	python scripts/partition.py data/model_partition/ijcnn1/ijcnn1_positive_cr_isesvm_test_x_bin 5

setupmodel-covtype:
	python scripts/partition.py data/model_partition/covtype/covtype_negative_cr_isesvm_train_y.bin 250
	python scripts/partition.py data/model_partition/covtype/covtype_negative_cr_isesvm_test_y.bin 100
	python scripts/partition.py data/model_partition/covtype/covtype_negative_cr_isesvm_train_x_bin 250
	python scripts/partition.py data/model_partition/covtype/covtype_negative_cr_isesvm_test_x_bin 100
	python scripts/partition.py data/model_partition/covtype/covtype_positive_cr_isesvm_train_y.bin 500
	python scripts/partition.py data/model_partition/covtype/covtype_positive_cr_isesvm_test_y.bin 100
	python scripts/partition.py data/model_partition/covtype/covtype_positive_cr_isesvm_train_x_bin 500
	python scripts/partition.py data/model_partition/covtype/covtype_positive_cr_isesvm_test_x_bin 100

setupmodel-webspam:
	python scripts/partition.py data/model_partition/webspam/webspam_negative_cr_isesvm_train_y.bin 200
	python scripts/partition.py data/model_partition/webspam/webspam_negative_cr_isesvm_test_y.bin 50
	python scripts/partition.py data/model_partition/webspam/webspam_negative_cr_isesvm_train_x_bin 200
	python scripts/partition.py data/model_partition/webspam/webspam_negative_cr_isesvm_test_x_bin 50
	python scripts/partition.py data/model_partition/webspam/webspam_positive_cr_isesvm_train_y.bin 200
	python scripts/partition.py data/model_partition/webspam/webspam_positive_cr_isesvm_test_y.bin 100
	python scripts/partition.py data/model_partition/webspam/webspam_positive_cr_isesvm_train_x_bin 200
	python scripts/partition.py data/model_partition/webspam/webspam_positive_cr_isesvm_test_x_bin 100

setupmodel-a9a:
	python scripts/partition.py data/model_partition/a9a/a9a_negative_cr_isesvm_train_y.bin 50
	python scripts/partition.py data/model_partition/a9a/a9a_negative_cr_isesvm_test_y.bin 50
	python scripts/partition.py data/model_partition/a9a/a9a_negative_cr_isesvm_train_x_bin 50
	python scripts/partition.py data/model_partition/a9a/a9a_negative_cr_isesvm_test_x_bin 50
	python scripts/partition.py data/model_partition/a9a/a9a_positive_cr_isesvm_train_y.bin 1
	python scripts/partition.py data/model_partition/a9a/a9a_positive_cr_isesvm_test_y.bin 5
	python scripts/partition.py data/model_partition/a9a/a9a_positive_cr_isesvm_train_x_bin 1
	python scripts/partition.py data/model_partition/a9a/a9a_positive_cr_isesvm_test_x_bin 5

clean:
	rm -r data/covtype/covtype_libsvm_ise_train_x.*
	rm -r data/covtype/covtype_libsvm_ise_test_x.*
	rm -r data/covtype/covtype_libsvm_ise_test_y.*
	rm -r data/covtype/covtype_libsvm_ise_train_y.*

cleanbin:
	rm -r data/covtype/covtype_libsvm_ise_train_x_bin.*
	rm -r data/covtype/covtype_libsvm_ise_test_x_bin.*
	rm -r data/covtype/covtype_libsvm_ise_test_y.*
	rm -r data/covtype/covtype_libsvm_ise_train_y.*

clean-model-partition-heart:
	rm -rf data/model_partition/heart/heart_negative_cr_isesvm_test_x_bin.*
	rm -rf data/model_partition/heart/heart_negative_cr_isesvm_train_x_bin.*
	rm -rf data/model_partition/heart/heart_positive_cr_isesvm_train_x_bin.*
	rm -rf data/model_partition/heart/heart_positive_cr_isesvm_test_x_bin.*
	rm -rf data/model_partition/heart/heart_negative_cr_isesvm_test_y.*.bin.*
	rm -rf data/model_partition/heart/heart_negative_cr_isesvm_train_y.*.bin.*
	rm -rf data/model_partition/heart/heart_positive_cr_isesvm_train_y.*.bin.*
	rm -rf data/model_partition/heart/heart_positive_cr_isesvm_test_y.*.bin.*

clean-model-partition-ijcnn1:
	rm -rf data/model_partition/ijcnn1/ijcnn_negative_cr_isesvm_test_x_bin.*
	rm -rf data/model_partition/ijcnn1/ijcnn_negative_cr_isesvm_train_x_bin.*
	rm -rf data/model_partition/ijcnn1/ijcnn_positive_cr_isesvm_train_x_bin.*
	rm -rf data/model_partition/ijcnn1/ijcnn_positive_cr_isesvm_test_x_bin.*
	rm -rf data/model_partition/ijcnn1/ijcnn_negative_cr_isesvm_test_y.*.bin.*
	rm -rf data/model_partition/ijcnn1/ijcnn_negative_cr_isesvm_train_y.*.bin.*
	rm -rf data/model_partition/ijcnn1/ijcnn_positive_cr_isesvm_train_y.*.bin.*
	rm -rf data/model_partition/ijcnn1/ijcnn_positive_cr_isesvm_test_y.*.bin.*

run:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main data/covtype/ covtype_libsvm_ise_train_x.1 covtype_libsvm_ise_train_y.1.bin covtype_libsvm_ise_test_x.1 covtype_libsvm_ise_test_y.1.bin

runbin:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main data/covtype/ covtype_libsvm_ise_train_x_bin.1 covtype_libsvm_ise_train_y.1.bin covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin

heart:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main data/heart/ heart_scale_train_1_x heart_scale_train_1_y heart_scale_test_1_x heart_scale_test_1_y

bulkexpbin:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.BulkTesting data/covtype/  covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin

mdmmtraincov:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.BulkMDMMTraining data/covtype/  covtype_libsvm_ise_train_x_bin.1 covtype_libsvm_ise_train_y.1.bin covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin model/covtype/1

sdmmtestcov:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMTesting data/covtype/  covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin

sdmmpredictcov:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMPrediction data/covtype/  covtype_libsvm_ise_test_x_bin.2 covtype_libsvm_ise_test_y.2.bin

# SINGLE MODEL MULTIPLE DATA EXPERIMENTS

## HEART
s-mdmmtrain-hrt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.BulkMDMMTraining data/heart/ heart_scale_train_x_bin.1 heart_scale_train_y.1.bin heart_scale_test_x_bin.1 heart_scale_test_y.1.bin model/single/heart/2 1

s-sdmmtest-hrt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMTesting data/heart/ heart_scale_test_x_bin.1 heart_scale_test_y.1.bin model/single/heart/2

s-sdmmpredict-hrt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMPrediction data/heart/ heart_scale_test_x_bin.1 heart_scale_test_y.1.bin model/single/heart/2 1 weighted_acc_heart_scale_test_x_bin.1_2

## IJCNN1

s-mdmmtrain-ijcnn1:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.BulkMDMMTraining data/ijcnn1/ ijcnn1_isesvm_train_x_bin.1 ijcnn1_isesvm_train_y.1.bin ijcnn1_isesvm_test_x_bin.1 ijcnn1_isesvm_test_y.1.bin model/single/ijcnn1/1 1

s-sdmmtest-ijcnn1:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMTesting data/ijcnn1/ ijcnn1_isesvm_test_x_bin.1 ijcnn1_isesvm_test_y.1.bin model/single/ijcnn1/1

s-sdmmpredict-ijcnn1:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMPrediction data/ijcnn1/ ijcnn1_isesvm_test_x_bin.2 ijcnn1_isesvm_test_y.2.bin model/single/ijcnn1/1 40 weighted_acc_ijcnn1_isesvm_test_x_bin.1_1

## COVTYPE

s-mdmmtrain-covtype-deprecated:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.BulkMDMMTraining data/covtype/ covtype_libsvm_ise_train_x_bin.1 covtype_libsvm_ise_train_y.1.bin covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin model/single/covtype/5 5

s-sdmmtest-covtype-deprecated:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMTesting data/covtype/ covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin model/single/covtype/5

s-sdmmpredict-covtype-deprecated:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMPrediction data/ijcnn1/ covtype_libsvm_ise_test_x_bin.2 covtype_libsvm_ise_test_y.2.bin model/single/covtype/5 40 weighted_acc_covtype_libsvm_ise_test_x_bin.1_5


## WEBSPAM

s-mdmmtrain-webspam:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.BulkMDMMTraining /home/vibhatha/data/libsvm/webspam/ webspam_isesvm_train_x_bin.1 webspam_isesvm_train_y.1.bin webspam_isesvm_test_x_bin.1 webspam_isesvm_test_y.1.bin model/single/webspam/1 1

s-sdmmtest-webspam:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMTesting /home/vibhatha/data/libsvm/webspam/ webspam_isesvm_test_x_bin.1 webspam_isesvm_test_y.1.bin model/single/webspam/1

s-sdmmpredict-webspam:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMPrediction /home/vibhatha/data/libsvm/webspam/ webspam_isesvm_test_x_bin.2 webspam_isesvm_test_y.2.bin model/single/webspam/1 40 weighted_acc_webspam_isesvm_test_x_bin.1_1

##COVTYPE SCALED

s-mdmmtrain-covtype:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.BulkMDMMTraining /home/vibhatha/data/libsvm/covtype/ covtype_isesvm_train_x_bin.1 covtype_isesvm_train_y.1.bin covtype_isesvm_test_x_bin.1 covtype_isesvm_test_y.1.bin model/single/covtype/1 1

s-sdmmtest-covtype:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMTesting /home/vibhatha/data/libsvm/covtype/ covtype_isesvm_test_x_bin.1 covtype_isesvm_test_y.1.bin model/single/covtype/1

s-sdmmpredict-covtype:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMPrediction /home/vibhatha/data/libsvm/covtype/ covtype_isesvm_test_x_bin.2 covtype_isesvm_test_y.2.bin model/single/covtype/1 40 weighted_acc_covtype_isesvm_test_x_bin.1_1

##a9a

s-mdmmtrain-a9a:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.BulkMDMMTraining /home/vibhatha/data/libsvm/a9a/ a9a_isesvm_train_x_bin.1 a9a_isesvm_train_y.1.bin a9a_isesvm_test_x_bin.1 a9a_isesvm_test_y.1.bin model/single/a9a/1 1

s-sdmmtest-a9a:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMTesting /home/vibhatha/data/libsvm/a9a/ a9a_isesvm_test_x_bin.1 a9a_isesvm_test_y.1.bin model/single/a9a/1

s-sdmmpredict-a9a:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMPrediction /home/vibhatha/data/libsvm/a9a/ a9a_isesvm_test_x_bin.2 a9a_isesvm_test_y.2.bin model/single/a9a/1 40 weighted_acc_a9a_isesvm_test_x_bin.1_1

##########################################################################################
#DYNAMIC EXPERIMENTS

# SINGLE MODEL MULTIPLE DATA EXPERIMENTS

## HEART
sd-mdmmtrain-hrt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.BulkMDMMTraining data/heart/ heart_scale_train_x_bin.1 heart_scale_train_y.1.bin heart_scale_test_x_bin.1 heart_scale_test_y.1.bin model/single/heart/2 1 1 4

sd-sdmmtest-hrt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.SDMMTesting data/heart/ heart_scale_test_x_bin.1 heart_scale_test_y.1.bin model/single/heart/2

sd-sdmmpredict-hrt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.SDMMPrediction data/heart/ heart_scale_test_x_bin.1 heart_scale_test_y.1.bin model/single/heart/2 1 weighted_acc_heart_scale_test_x_bin.1_2

## IJCNN1

sd-mdmmtrain-ijcnn1:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.BulkMDMMTraining data/ijcnn1/ ijcnn1_isesvm_train_x_bin.1 ijcnn1_isesvm_train_y.1.bin ijcnn1_isesvm_test_x_bin.1 ijcnn1_isesvm_test_y.1.bin model/single/ijcnn1/1 1 1 4

sd-sdmmtest-ijcnn1:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.SDMMTesting data/ijcnn1/ ijcnn1_isesvm_test_x_bin.1 ijcnn1_isesvm_test_y.1.bin model/single/ijcnn1/1

sd-sdmmpredict-ijcnn1:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.single.SDMMPrediction data/ijcnn1/ ijcnn1_isesvm_test_x_bin.2 ijcnn1_isesvm_test_y.2.bin model/single/ijcnn1/1 40 weighted_acc_ijcnn1_isesvm_test_x_bin.1_1

## COVTYPE

sd-mdmmtrain-covtype-deprecated:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.BulkMDMMTraining data/covtype/ covtype_libsvm_ise_train_x_bin.1 covtype_libsvm_ise_train_y.1.bin covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin model/single/covtype/5 5

sd-sdmmtest-covtype-deprecated:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.SDMMTesting data/covtype/ covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin model/single/covtype/5

sd-sdmmpredict-covtype-deprecated:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.SDMMPrediction data/ijcnn1/ covtype_libsvm_ise_test_x_bin.2 covtype_libsvm_ise_test_y.2.bin model/single/covtype/5 40 weighted_acc_covtype_libsvm_ise_test_x_bin.1_5


## WEBSPAM

sd-mdmmtrain-webspam:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.BulkMDMMTraining /home/vibhatha/data/libsvm/webspam/ webspam_isesvm_train_x_bin.1 webspam_isesvm_train_y.1.bin webspam_isesvm_test_x_bin.1 webspam_isesvm_test_y.1.bin model/single/webspam/1 1 1 4

sd-sdmmtest-webspam:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.SDMMTesting /home/vibhatha/data/libsvm/webspam/ webspam_isesvm_test_x_bin.1 webspam_isesvm_test_y.1.bin model/single/webspam/1

sd-sdmmpredict-webspam:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.SDMMPrediction /home/vibhatha/data/libsvm/webspam/ webspam_isesvm_test_x_bin.2 webspam_isesvm_test_y.2.bin model/single/webspam/1 40 weighted_acc_webspam_isesvm_test_x_bin.1_1

##COVTYPE SCALED

sd-mdmmtrain-covtype:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.BulkMDMMTraining /home/vibhatha/data/libsvm/covtype/ covtype_isesvm_train_x_bin.1 covtype_isesvm_train_y.1.bin covtype_isesvm_test_x_bin.1 covtype_isesvm_test_y.1.bin model/single/covtype/1 1

sd-sdmmtest-covtype:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.SDMMTesting /home/vibhatha/data/libsvm/covtype/ covtype_isesvm_test_x_bin.1 covtype_isesvm_test_y.1.bin model/single/covtype/1

sd-sdmmpredict-covtype:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.SDMMPrediction /home/vibhatha/data/libsvm/covtype/ covtype_isesvm_test_x_bin.2 covtype_isesvm_test_y.2.bin model/single/covtype/1 40 weighted_acc_covtype_isesvm_test_x_bin.1_1

##a9a

sd-mdmmtrain-a9a:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.BulkMDMMTraining /home/vibhatha/data/libsvm/a9a/ a9a_isesvm_train_x_bin.1 a9a_isesvm_train_y.1.bin a9a_isesvm_test_x_bin.1 a9a_isesvm_test_y.1.bin model/single/a9a/1 1 1 4

sd-sdmmtest-a9a:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.SDMMTesting /home/vibhatha/data/libsvm/a9a/ a9a_isesvm_test_x_bin.1 a9a_isesvm_test_y.1.bin model/single/a9a/1

sd-sdmmpredict-a9a:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.SDMMPrediction /home/vibhatha/data/libsvm/a9a/ a9a_isesvm_test_x_bin.2 a9a_isesvm_test_y.2.bin model/single/a9a/1 40 weighted_acc_a9a_isesvm_test_x_bin.1_1

####################################################################


# PLOTTING

plotacc:
	python scripts/csvplotaccuracies.py "covtype_libsvm_ise_test_x_bin.1_1"

plotsdmmacc-cov:
	python scripts/csvplotaccuracies.py "stats/accuracyPerDataSet/covtype/"  "accuracy_covtype_libsvm_ise_test_x_bin.2_1" "stats/plots/accuracyPerDataSet/" "covtype-model-50-predictions.png" "Data Set ID" "Accuracy of DataSet" "Accuracy Distribution Per DataSet for Single Data Multi Model Approach" "CovType" "-1-" "SDMM-"

plot-cor-sdmmacc-hrt-pst:
	python scripts/csvplotaccuracies.py "stats/accuracyPerDataSet/heart/2/positive/"  "accuracy_heart_positive_cr_isesvm_test_x_bin.2_2" "stats/plots/accuracyPerDataSet/heart/2/" "heart-model-positive-2-predictions.png" "Data Set ID" "Accuracy of DataSet" "Accuracy Distribution Per DataSet for Single Data Multi Model Approach" "Heart-Positive-Cor" "-2-" "SDMM-"

plot-cor-sdmmacc-hrt-ngt:
	python scripts/csvplotaccuracies.py "stats/accuracyPerDataSet/heart/2/negative/"  "accuracy_heart_negative_cr_isesvm_test_x_bin.2_2" "stats/plots/accuracyPerDataSet/heart/2/" "heart-model-negative-2-predictions.png" "Data Set ID" "Accuracy of DataSet" "Accuracy Distribution Per DataSet for Single Data Multi Model Approach" "Heart-Negative-Cor" "-2-" "SDMM-"

# DIR SET UP

setup-dir-ijcnn1-s:
	mkdir -p logs/ijcnn1/{1..10}
	mkdir -p model/single/ijcnn1/{1..10}
	mkdir -p stats/accuracyPerDataSet/single/ijcnn1/{1..10}
	mkdir -p stats/SDMMTestAccuracies/single/ijcnn1/{1..10}
	mkdir -p stats/weightedmodels/single/ijcnn1/{1..10}

#IJCNN DATA SET EXPERIMENTS

## POSITIVE CORELATION PARTITION
cor-mdmmtrain-ijcnn-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.BulkMDMMTraining data/model_partition/ijcnn1/ ijcnn1_positive_cr_isesvm_train_x_bin.1 ijcnn1_positive_cr_isesvm_train_y.1.bin ijcnn1_positive_cr_isesvm_test_x_bin.1 ijcnn1_positive_cr_isesvm_test_y.1.bin  model/ijcnn1/1/positive

cor-sdmmtest-ijcnn-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMTesting data/model_partition/ijcnn1/ ijcnn1_positive_cr_isesvm_test_x_bin.4 ijcnn1_positive_cr_isesvm_test_y.4.bin model/ijcnn1/1/positive

cor-sdmmpredict-ijcnn-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMPrediction data/model_partition/ijcnn1/  ijcnn1_positive_cr_isesvm_test_x_bin.3 ijcnn1_positive_cr_isesvm_test_y.3.bin model/ijcnn1/1/positive weighted_acc_ijcnn1_positive_cr_isesvm_test_x_bin.4 4
## NEGATIVE CORELATION PARTITIONS
cor-mdmmtrain-ijcnn-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.BulkMDMMTraining data/model_partition/ijcnn1/ ijcnn1_negative_cr_isesvm_train_x_bin.1 ijcnn1_negative_cr_isesvm_train_y.1.bin ijcnn1_negative_cr_isesvm_test_x_bin.1 ijcnn1_negative_cr_isesvm_test_y.1.bin  model/ijcnn1/1/negative

cor-sdmmtest-ijcnn-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMTesting data/model_partition/ijcnn1/ ijcnn1_negative_cr_isesvm_test_x_bin.2 ijcnn1_negative_cr_isesvm_test_y.2.bin model/ijcnn1/1/negative

cor-sdmmpredict-ijcnn-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMPrediction data/model_partition/ijcnn1/  ijcnn1_negative_cr_isesvm_test_x_bin.3 ijcnn1_negative_cr_isesvm_test_y.3.bin model/ijcnn1/1/negative weighted_acc_ijcnn1_negative_cr_isesvm_test_x_bin.2 4

# HEART MODULAR SECTION

## POSITIVE 
cor-mdmmtrainhrt-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.BulkMDMMTraining data/model_partition/heart/ heart_positive_cr_isesvm_train_x_bin.1 heart_positive_cr_isesvm_train_y.1.bin heart_positive_cr_isesvm_test_x_bin.1 heart_positive_cr_isesvm_test_y.1.bin model/heart/2/positive

cor-sdmmtesthrt-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMTesting data/model_partition/heart/ heart_positive_cr_isesvm_test_x_bin.1 heart_positive_cr_isesvm_test_y.1.bin model/heart/2/positive

cor-sdmmpredicthrt-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMPrediction data/model_partition/heart/  heart_positive_cr_isesvm_test_x_bin.1 heart_positive_cr_isesvm_test_y.1.bin model/heart/2/positive weighted_acc_heart_positive_cr_isesvm_test_x_bin.1 1

## NEGATIVE
cor-mdmmtrainhrt-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.BulkMDMMTraining data/model_partition/heart/ heart_negative_cr_isesvm_train_x_bin.1 heart_negative_cr_isesvm_train_y.1.bin heart_negative_cr_isesvm_test_x_bin.1 heart_negative_cr_isesvm_test_y.1.bin model/heart/2/negative 

cor-sdmmtesthrt-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMTesting data/model_partition/heart/ heart_negative_cr_isesvm_test_x_bin.1 heart_negative_cr_isesvm_test_y.1.bin model/heart/2/negative

cor-sdmmpredicthrt-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMPrediction data/model_partition/heart/  heart_negative_cr_isesvm_test_x_bin.1 heart_negative_cr_isesvm_test_y.1.bin model/heart/2/negative weighted_acc_heart_negative_cr_isesvm_test_x_bin.1 1

#Covtype DATA SET EXPERIMENTS

## POSITIVE CORELATION PARTITION

cor-mdmmtrain-covtype-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.BulkMDMMTraining data/model_partition/covtype/ covtype_positive_cr_isesvm_train_x_bin.1 covtype_positive_cr_isesvm_train_y.1.bin covtype_positive_cr_isesvm_test_x_bin.1 covtype_positive_cr_isesvm_test_y.1.bin  model/covtype/1/positive

cor-sdmmtest-covtype-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMTesting data/model_partition/covtype/ covtype_positive_cr_isesvm_test_x_bin.2 covtype_positive_cr_isesvm_test_y.2.bin model/covtype/1/positive

cor-sdmmpredict-covtype-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMPrediction data/model_partition/covtype/  covtype_positive_cr_isesvm_test_x_bin.2 covtype_positive_cr_isesvm_test_y.2.bin model/covtype/1/positive weighted_acc_covtype_positive_cr_isesvm_test_x_bin.4 100

## NEGATIVE CORELATION PARTITIONS

cor-mdmmtrain-covtype-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.BulkMDMMTraining data/model_partition/covtype/ covtype_negative_cr_isesvm_train_x_bin.1 covtype_negative_cr_isesvm_train_y.1.bin covtype_negative_cr_isesvm_test_x_bin.1 covtype_negative_cr_isesvm_test_y.1.bin  model/covtype/1/negative

cor-sdmmtest-covtype-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMTesting data/model_partition/covtype/ covtype_negative_cr_isesvm_test_x_bin.2 covtype_negative_cr_isesvm_test_y.2.bin model/covtype/1/negative

cor-sdmmpredict-covtype-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMPrediction data/model_partition/covtype/  covtype_negative_cr_isesvm_test_x_bin.3 covtype_negative_cr_isesvm_test_y.3.bin model/covtype/1/negative weighted_acc_covtype_negative_cr_isesvm_test_x_bin.2 100


#Webspam DATA SET EXPERIMENTS

## POSITIVE CORELATION PARTITION

cor-mdmmtrain-webspam-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.BulkMDMMTraining data/model_partition/webspam/ webspam_positive_cr_isesvm_train_x_bin.1 webspam_positive_cr_isesvm_train_y.1.bin webspam_positive_cr_isesvm_test_x_bin.1 webspam_positive_cr_isesvm_test_y.1.bin  model/webspam/1/positive

cor-sdmmtest-webspam-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMTesting data/model_partition/webspam/ webspam_positive_cr_isesvm_test_x_bin.4 webspam_positive_cr_isesvm_test_y.4.bin model/webspam/1/positive

cor-sdmmpredict-webspam-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMPrediction data/model_partition/webspam/  webspam_positive_cr_isesvm_test_x_bin.3 webspam_positive_cr_isesvm_test_y.3.bin model/webspam/1/positive weighted_acc_webspam_positive_cr_isesvm_test_x_bin.4 40

## NEGATIVE CORELATION PARTITIONS

cor-mdmmtrain-webspam-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.BulkMDMMTraining data/model_partition/webspam/ webspam_negative_cr_isesvm_train_x_bin.1 webspam_negative_cr_isesvm_train_y.1.bin webspam_negative_cr_isesvm_test_x_bin.1 webspam_negative_cr_isesvm_test_y.1.bin  model/webspam/1/negative

cor-sdmmtest-webspam-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMTesting data/model_partition/webspam/ webspam_negative_cr_isesvm_test_x_bin.1 webspam_negative_cr_isesvm_test_y.1.bin model/webspam/1/negative

cor-sdmmpredict-webspam-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMPrediction data/model_partition/webspam/  webspam_negative_cr_isesvm_test_x_bin.2 webspam_negative_cr_isesvm_test_y.3.bin model/webspam/1/negative weighted_acc_webspam_negative_cr_isesvm_test_x_bin.1 40


#A9a DATA SET EXPERIMENTS

## POSITIVE CORELATION PARTITION

cor-mdmmtrain-a9a-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.BulkMDMMTraining data/model_partition/a9a/ a9a_positive_cr_isesvm_train_x_bin.1 a9a_positive_cr_isesvm_train_y.1.bin a9a_positive_cr_isesvm_test_x_bin.1 a9a_positive_cr_isesvm_test_y.1.bin  model/a9a/1/positive

cor-sdmmtest-a9a-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMTesting data/model_partition/a9a/ a9a_positive_cr_isesvm_test_x_bin.1 a9a_positive_cr_isesvm_test_y.1.bin model/a9a/1/positive

cor-sdmmpredict-a9a-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMPrediction data/model_partition/a9a/  a9a_positive_cr_isesvm_test_x_bin.2 a9a_positive_cr_isesvm_test_y.2.bin model/a9a/1/positive weighted_acc_a9a_positive_cr_isesvm_test_x_bin.4 4

## NEGATIVE CORELATION PARTITIONS

cor-mdmmtrain-a9a-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.BulkMDMMTraining data/model_partition/a9a/ a9a_negative_cr_isesvm_train_x_bin.2 a9a_negative_cr_isesvm_train_y.2.bin a9a_negative_cr_isesvm_test_x_bin.1 a9a_negative_cr_isesvm_test_y.1.bin  model/a9a/1/negative

cor-sdmmtest-a9a-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMTesting data/model_partition/a9a/ a9a_negative_cr_isesvm_test_x_bin.1 a9a_negative_cr_isesvm_test_y.1.bin model/a9a/1/negative

cor-sdmmpredict-a9a-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.modelbased.SDMMPrediction data/model_partition/a9a/  a9a_negative_cr_isesvm_test_x_bin.2 a9a_negative_cr_isesvm_test_y.2.bin model/a9a/1/negative weighted_acc_a9a_negative_cr_isesvm_test_x_bin.1 40


#DYNAMIC COR

#IJCNN DATA SET EXPERIMENTS

## POSITIVE CORELATION PARTITION
cord-mdmmtrain-ijcnn-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.modelbased.BulkMDMMTraining data/model_partition/ijcnn1/ ijcnn1_positive_cr_isesvm_train_x_bin.1 ijcnn1_positive_cr_isesvm_train_y.1.bin ijcnn1_positive_cr_isesvm_test_x_bin.1 ijcnn1_positive_cr_isesvm_test_y.1.bin  model/ijcnn1/1/positive 1 1 0.1

cord-sdmmtest-ijcnn-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.modelbased.SDMMTesting data/model_partition/ijcnn1/ ijcnn1_positive_cr_isesvm_test_x_bin.4 ijcnn1_positive_cr_isesvm_test_y.4.bin model/ijcnn1/1/positive

cord-sdmmpredict-ijcnn-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.modelbased.SDMMPrediction data/model_partition/ijcnn1/  ijcnn1_positive_cr_isesvm_test_x_bin.3 ijcnn1_positive_cr_isesvm_test_y.3.bin model/ijcnn1/1/positive weighted_acc_ijcnn1_positive_cr_isesvm_test_x_bin.4 4 1 0.1
## NEGATIVE CORELATION PARTITIONS
cord-mdmmtrain-ijcnn-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.modelbased.BulkMDMMTraining data/model_partition/ijcnn1/ ijcnn1_negative_cr_isesvm_train_x_bin.1 ijcnn1_negative_cr_isesvm_train_y.1.bin ijcnn1_negative_cr_isesvm_test_x_bin.1 ijcnn1_negative_cr_isesvm_test_y.1.bin  model/ijcnn1/1/negative 1 1 0.1

cord-sdmmtest-ijcnn-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.modelbased.SDMMTesting data/model_partition/ijcnn1/ ijcnn1_negative_cr_isesvm_test_x_bin.2 ijcnn1_negative_cr_isesvm_test_y.2.bin model/ijcnn1/1/negative

cord-sdmmpredict-ijcnn-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.modelbased.SDMMPrediction data/model_partition/ijcnn1/  ijcnn1_negative_cr_isesvm_test_x_bin.3 ijcnn1_negative_cr_isesvm_test_y.3.bin model/ijcnn1/1/negative weighted_acc_ijcnn1_negative_cr_isesvm_test_x_bin.2 4 1 0.1
