install:
	mvn install

package:
	mvn clean
	mvn package

setup:
	if [ ! -d "model" ] ; then mkdir model; fi ;
	python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_test_y data/covtype/covtype_libsvm_ise_test_y.bin
	python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_train_y data/covtype/covtype_libsvm_ise_train_y.bin
	python scripts/partition.py data/covtype/covtype_libsvm_ise_train_y.bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_test_y.bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_train_x 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_test_x 400


setupbin:
	if [ ! -d "model" ] ; then mkdir model; fi ;
	python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_test_y data/covtype/covtype_libsvm_ise_test_y.bin
	python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_train_y data/covtype/covtype_libsvm_ise_train_y.bin
	python scripts/partition.py data/covtype/covtype_libsvm_ise_train_y.bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_test_y.bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_train_x_bin 400
	python scripts/partition.py data/covtype/covtype_libsvm_ise_test_x_bin 400


clean:
	if [ -d "model" ] ; then rm -r model ; fi ;	
	rm -r data/covtype/covtype_libsvm_ise_train_x.*
	rm -r data/covtype/covtype_libsvm_ise_test_x.*
	rm -r data/covtype/covtype_libsvm_ise_test_y.*
	rm -r data/covtype/covtype_libsvm_ise_train_y.*

cleanbin:
	if [ -d "model" ] ; then rm -r model ; fi ;	
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
