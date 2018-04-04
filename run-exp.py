import numpy as np
import subprocess
import os
import sys

def method0(datasource, C, gamma):
    C = str(C)
    gamma = str(gamma)
    os.system("java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.BulkMDMMTraining data/"+datasource+"/ "+datasource+"_isesvm_train_x_bin.1 "+datasource+"_isesvm_train_y.1.bin "+datasource+"_isesvm_test_x_bin.1 "+datasource+"_isesvm_test_y.1.bin model/single/"+datasource+"/1 1 "+C+" "+gamma+"")
    os.system("java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.SDMMTesting data/"+datasource+"/ "+datasource+"_isesvm_test_x_bin.1 "+datasource+"_isesvm_test_y.1.bin model/single/"+datasource+"/1")
    os.system("java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.experiments.dynamic.single.SDMMPrediction data/"+datasource+"/ "+datasource+"_isesvm_test_x_bin.2 "+datasource+"_isesvm_test_y.2.bin model/single/"+datasource+"/1 40 weighted_acc_"+datasource+"_isesvm_test_x_bin.1_1 "+C+" "+gamma+"")


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
gamma_range = np.arange(0.000, 10.000, 0.5)
c_range = np.arange(8, 10, 1)


method2(datasource=datasource, c_range=c_range, gamma_range=gamma_range)
