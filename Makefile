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


run:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main data/covtype/ covtype_libsvm_ise_train_x.1 covtype_libsvm_ise_train_y.1.bin covtype_libsvm_ise_test_x.1 covtype_libsvm_ise_test_y.1.bin

runbin:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main data/covtype/ covtype_libsvm_ise_train_x_bin.1 covtype_libsvm_ise_train_y.1.bin covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin

heart:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main data/heart/ heart_scale_train_1_x heart_scale_train_1_y heart_scale_test_1_x heart_scale_test_1_y

bulkexpbin:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.BulkTesting data/covtype/  covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin

bulkmodeltrain:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.BulkMDMMTraining data/covtype/  covtype_libsvm_ise_train_x_bin.1 covtype_libsvm_ise_train_y.1.bin covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin

sdmm:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.BulkSDMMTesting data/covtype/  covtype_libsvm_ise_test_x_bin.1 covtype_libsvm_ise_test_y.1.bin

plotacc:
	python scripts/csvplotaccuracies.py "covtype_libsvm_ise_test_x_bin.1_1"

