import numpy as np
import subprocess
import os
import sys

def method0(datasource, C, gamma):
    C = str(C)
    gamma = str(gamma)
    cm_ps_tr = "java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.modelbased.BulkMDMMTraining data/model_partition/"+datasource+"/ "+datasource+"_positive_cr_isesvm_train_x_bin.1 "+datasource+"_positive_cr_isesvm_train_y.1.bin "+datasource+"_positive_cr_isesvm_test_x_bin.1 "+datasource+"_positive_cr_isesvm_test_y.1.bin model/"+datasource+"/1/positive 1 "+C+" "+gamma+""
    cm_ps_ts = "java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.modelbased.SDMMTesting data/model_partition/"+datasource+"/ "+datasource+"_positive_cr_isesvm_test_x_bin.1 "+datasource+"_positive_cr_isesvm_test_y.1.bin model/"+datasource+"/1/positive"
    cm_ps_pd = "java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.modelbased.SDMMPrediction data/model_partition/"+datasource+"/ "+datasource+"_positive_cr_isesvm_test_x_bin.2 "+datasource+"_positive_cr_isesvm_test_y.2.bin model/"+datasource+"/1/positive weighted_acc_"+datasource+"_positive_cr_isesvm_test_x_bin.1 4 "+C+" "+gamma+""

    cm_ng_tr = "java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.modelbased.BulkMDMMTraining data/model_partition/"+datasource+"/ "+datasource+"_negative_cr_isesvm_train_x_bin.1 "+datasource+"_negative_cr_isesvm_train_y.1.bin "+datasource+"_negative_cr_isesvm_test_x_bin.1 "+datasource+"_negative_cr_isesvm_test_y.1.bin model/"+datasource+"/1/negative 1 "+C+" "+gamma+""
    cm_ng_ts = "java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.modelbased.SDMMTesting data/model_partition/"+datasource+"/ "+datasource+"_negative_cr_isesvm_test_x_bin.1 "+datasource+"_negative_cr_isesvm_test_y.1.bin model/"+datasource+"/1/negative"
    cm_ng_pd = "java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.modelbased.SDMMPrediction data/model_partition/"+datasource+"/ "+datasource+"_negative_cr_isesvm_test_x_bin.2 "+datasource+"_negative_cr_isesvm_test_y.2.bin model/"+datasource+"/1/negative weighted_acc_"+datasource+"_negative_cr_isesvm_test_x_bin.1 4 "+C+" "+gamma+""

    os.system(cm_ps_tr)
    os.system(cm_ps_ts)
    os.system(cm_ps_pd)

    os.system(cm_ng_tr)
    os.system(cm_ng_ts)
    os.system(cm_ng_pd)



def method1(datasource='a9a', C=10, gamma=0.01):
    params = datasource+" "+str(C)+" "+str(gamma)
    script = "/home/vibhatha/github/smo/SMO/run-dynamic.sh "+params
    print(script)
    subprocess.call(script, shell=True)


def method2(datasource, c_range, gamma_range):
    size1 = len(gamma_range)
    size2 = len(c_range)
    total_sum = size1 * size2
    count = 0
    for c in c_range:
        for gamma in gamma_range:
            print("Exp Config: ",datasource, c, gamma)
            method0(datasource=datasource, C=c, gamma=gamma)
            count = count + 1
            if((count / float(total_sum) * 100)==100):
                print("Completed: " + str(count / float(total_sum) * 100) + "%")
            else:
                print("In Progress: " + str(count / float(total_sum) * 100) + "%")





datasource=sys.argv[1]#'webspam'
gamma_range = np.arange(0.000, 1.000, 0.5)
c_range = np.arange(1, 10, 1)


method2(datasource=datasource, c_range=c_range, gamma_range=gamma_range)
