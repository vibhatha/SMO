install:
	mvn install

package:
	mvn clean
	mvn package

setup:
	if [ ! -d "model" ] ; then mkdir model; fi ;
	python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_test_y data/covtype/multifiles/covtype_libsvm_ise_test_y.bin
	python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_train_y data/covtype/multifiles/covtype_libsvm_ise_train_y.bin
	python scripts/partition.py data/covtype/multifiles/covtype_libsvm_ise_train_y.bin 200
	python scripts/partition.py data/covtype/multifiles/covtype_libsvm_ise_test_y.bin 200
	python scripts/partition.py data/covtype/covtype_libsvm_ise_train_x 200
	python scripts/partition.py data/covtype/covtype_libsvm_ise_test_x 200

clean:
	if [ -d "model" ] ; then rm -r model ; fi ;	
	rm -r data/covtype/covtype_libsvm_ise_train_x.*
	rm -r data/covtype/covtype_libsvm_ise_test_x.*
	rm -r data/covtype/multifiles/covtype_libsvm_ise_test_y.*
	rm -r data/covtype/multifiles/covtype_libsvm_ise_train_y.*

run:
	java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main