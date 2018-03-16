install:
	mvn install

package:
	mvn clean
	mvn package

setup:
	if [ ! -d "model" ] ; then mkdir model; fi ;
	if [ ! -d "logs" ] ; then mkdir logs; fi ;
	if [ ! -d "stats" ] ; then mkdir stats; fi ;
	python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_test_y data/covtype/covtype_libsvm_ise_test_y.bin
	python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_train_y data/covtype/covtype_libsvm_ise_train_y.bin
	python scripts/partition.py data/covtype/covtype_libsvm_ise_train_y.bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_test_y.bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_train_x 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_test_x 400


setupbin:
	if [ ! -d "model" ] ; then mkdir model; fi ;
	if [ ! -d "logs" ] ; then mkdir logs; fi ;
	if [ ! -d "stats" ] ; then mkdir stats; fi ;
	python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_test_y data/covtype/covtype_libsvm_ise_test_y.bin
	python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_train_y data/covtype/covtype_libsvm_ise_train_y.bin
	python scripts/partition.py data/covtype/covtype_libsvm_ise_train_y.bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_test_y.bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_train_x_bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_test_x_bin 400

setupmodelheart:
	python scripts/partition.py data/model_partition/heart/heart_negative_cr_isesvm_train_y.bin 1
	python scripts/partition.py data/model_partition/heart/heart_negative_cr_isesvm_test_y.bin 1
	python scripts/partition.py data/model_partition/heart/heart_negative_cr_isesvm_train_x_bin 1
	python scripts/partition.py data/model_partition/heart/heart_negative_cr_isesvm_test_x_bin 1
	python scripts/partition.py data/model_partition/heart/heart_positive_cr_isesvm_train_y.bin 1
	python scripts/partition.py data/model_partition/heart/heart_positive_cr_isesvm_test_y.bin 1
	python scripts/partition.py data/model_partition/heart/heart_positive_cr_isesvm_train_x_bin 1
	python scripts/partition.py data/model_partition/heart/heart_positive_cr_isesvm_test_x_bin 1

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


run:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main data/covtype/ covtype_libsvm_ise_train_x.1 covtype_libsvm_ise_train_y.1.bin covtype_libsvm_ise_test_x.1 covtype_libsvm_ise_test_y.1.bin

runbin:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main data/covtype/ covtype_libsvm_ise_train_x_bin.1 covtype_libsvm_ise_train_y.1.bin covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin

heart:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main data/heart/ heart_scale_train_1_x heart_scale_train_1_y heart_scale_test_1_x heart_scale_test_1_y

bulkexpbin:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.BulkTesting data/covtype/  covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin

mdmmtraincov:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.BulkMDMMTraining data/covtype/  covtype_libsvm_ise_train_x_bin.1 covtype_libsvm_ise_train_y.1.bin covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin model/covtype/1

sdmmtestcov:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.SDMMTesting data/covtype/  covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin

sdmmpredictcov:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.SDMMPrediction data/covtype/  covtype_libsvm_ise_test_x_bin.2 covtype_libsvm_ise_test_y.2.bin

mdmmtrainhrt-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.BulkMDMMTraining data/model_partition/heart/ heart_positive_cr_isesvm_train_x_bin.1 heart_positive_cr_isesvm_train_y.1.bin heart_positive_cr_isesvm_test_x_bin.1 heart_positive_cr_isesvm_test_y.1.bin model/heart/2/positive

sdmmtesthrt-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.SDMMTesting data/model_partition/heart/ heart_positive_cr_isesvm_test_x_bin.1 heart_positive_cr_isesvm_test_y.1.bin model/heart/2/positive

sdmmpredicthrt-pst:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.SDMMPrediction data/model_partition/heart/  heart_positive_cr_isesvm_test_x_bin.2 heart_positive_cr_isesvm_test_y.2.bin model/heart/2/positive weighted_acc_heart_positive_cr_isesvm_test_x_bin.1

mdmmtrainhrt-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.BulkMDMMTraining data/model_partition/heart/ heart_negative_cr_isesvm_train_x_bin.1 heart_negative_cr_isesvm_train_y.1.bin heart_negative_cr_isesvm_test_x_bin.1 heart_negative_cr_isesvm_test_y.1.bin model/heart/2/negative 

sdmmtesthrt-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.SDMMTesting data/model_partition/heart/ heart_negative_cr_isesvm_test_x_bin.1 heart_negative_cr_isesvm_test_y.1.bin model/heart/2/negative

sdmmpredicthrt-ngt:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.SDMMPrediction data/model_partition/heart/  heart_negative_cr_isesvm_test_x_bin.2 heart_negative_cr_isesvm_test_y.2.bin model/heart/2/negative weighted_acc_heart_negative_cr_isesvm_test_x_bin.1


plotacc:
	python scripts/csvplotaccuracies.py "covtype_libsvm_ise_test_x_bin.1_1"

plotsdmmacc-cov:
	python scripts/csvplotaccuracies.py "stats/accuracyPerDataSet/covtype/"  "accuracy_covtype_libsvm_ise_test_x_bin.2_1" "stats/plots/accuracyPerDataSet/" "covtype-model-50-predictions.png" "Data Set ID" "Accuracy of DataSet" "Accuracy Distribution Per DataSet for Single Data Multi Model Approach" "CovType" "-1-" "SDMM-"

plotsdmmacc-hrt-pst:
	python scripts/csvplotaccuracies.py "stats/accuracyPerDataSet/heart/2/positive/"  "accuracy_heart_positive_cr_isesvm_test_x_bin.2_2" "stats/plots/accuracyPerDataSet/heart/2/" "heart-model-positive-2-predictions.png" "Data Set ID" "Accuracy of DataSet" "Accuracy Distribution Per DataSet for Single Data Multi Model Approach" "Heart-Positive-Cor" "-2-" "SDMM-"

plotsdmmacc-hrt-ngt:
	python scripts/csvplotaccuracies.py "stats/accuracyPerDataSet/heart/2/negative/"  "accuracy_heart_negative_cr_isesvm_test_x_bin.2_2" "stats/plots/accuracyPerDataSet/heart/2/" "heart-model-negative-2-predictions.png" "Data Set ID" "Accuracy of DataSet" "Accuracy Distribution Per DataSet for Single Data Multi Model Approach" "Heart-Negative-Cor" "-2-" "SDMM-"
