expid=$1
partitions=$2
Cval=$3
gamma=$4
kernel=$5
baseacc=$6
datasource=$7
minibatch_times=$8
#echo "java -Xms30072m -cp target/svm-ddmsvm-0.1.jar edu.ise.svm.experiments.dynamic.checkacc.BulkMDMMTraining /home/vibhatha/data/libsvm/webspam/ webspam_isesvm_train_x_bin.1 webspam_isesvm_train_y.1.bin webspam_isesvm_test_x_bin.1 webspam_isesvm_test_y.1.bin model/single/webspam/${expid} ${partitions} ${Cval} ${gamma} ${kernel}"
java -Xmx2G -Xms2G -cp target/ddmsvm-ise-0.4.jar edu.ise.svm.smo.newton.sdsm.minibatch.SVM ~/data/libsvm/${datasource}/ ${datasource}_isesvm_train_x_bin.1 ${datasource}_isesvm_train_y.1.bin ${datasource}_isesvm_test_x_bin.1 ${datasource}_isesvm_test_y.1.bin model/single/${datasource}/${expid} ${partitions} ${Cval} ${gamma} ${kernel} ${baseacc} ${datasource} ${minibatch_times}
