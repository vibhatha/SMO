python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_test_y data/covtype/multifiles/covtype_libsvm_ise_train_y.bin
python scripts/binaryconverter.py data/covtype/covtype_libsvm_ise_train_y data/covtype/multifiles/covtype_libsvm_ise_train_y.bin
python scripts/partition.py data/covtype/multifiles/covtype_libsvm_ise_train_y.bin 50
python scripts/partition.py data/covtype/multifiles/covtype_libsvm_ise_test_y.bin 50
python scripts/partition.py data/covtype/covtype_libsvm_ise_train_x 50
python scripts/partition.py data/covtype/covtype_libsvm_ise_test_x 50
