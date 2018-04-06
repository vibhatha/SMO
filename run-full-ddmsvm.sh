expid=$1
partitions=$2
Cval=$3
gamma=$4
kernel=$5
baseacc=$6
datasource=$7
#echo "java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.checkacc.BulkMDMMTraining /home/vibhatha/data/libsvm/webspam/ webspam_isesvm_train_x_bin.1 webspam_isesvm_train_y.1.bin webspam_isesvm_test_x_bin.1 webspam_isesvm_test_y.1.bin model/single/webspam/${expid} ${partitions} ${Cval} ${gamma} ${kernel}"
java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.smo.sdsm.SVM /home/vibhatha/data/libsvm/${datasource}/ ${datasource}_isesvm_train_x_bin.1 ${datasource}_isesvm_train_y.1.bin ${datasource}_isesvm_test_x_bin.1 ${datasource}_isesvm_test_y.1.bin model/single/${datasource}/${expid} ${partitions} ${Cval} ${gamma} ${kernel} ${baseacc}
