##Usage
# Read From the Original Libsvm2CSV converted csv file and run this process
# The source file to this process is the original LibSVM2CSV converted file.

import numpy as np
from scipy.stats import pearsonr
import csv
import sys
from numpy import genfromtxt
import matplotlib.pyplot as plt

def get_dataset(filepath):
    my_data = genfromtxt(filepath, delimiter=',')
    return my_data

def calc_test_cor():
    x1 = np.array([10, 3, 2, 1, 0])
    x2 = np.array([10.2, 3.5, 2.1, 1.5, 0.1])
    x3 = np.array([12, 4, 5, 3, 1])

    cor12 = np.corrcoef(x1, x2)
    cor13 = np.corrcoef(x1, x3)
    cor23 = np.corrcoef(x2, x3)

    # print("Corelation")
    # print(cor12)
    # print(cor13)
    # print(cor23)

    print("Corelation Value (r) and Pearson's Correlation Coefficient")

    r1, p1 = pearsonr(x1, x2)
    r2, p2 = pearsonr(x1, x3)
    r3, p3 = pearsonr(x2, x3)

    print(r1, p1)
    print(r2, p2)
    print(r3, p3)


def gen_corcoef(data):
    size = len(data)
    all_r=[]
    all_p=[]
    positive_cr_data = []
    negative_cr_data = []
    zero_cr_data = []
    for i in range(0,size-1):
        r1, p1 = pearsonr(data[0], data[i+1])
        if(r1>0):
            positive_cr_data.append(data[i+1])
        if(r1<0):
            negative_cr_data.append(data[i+1])
        if(r1==0):
            zero_cr_data.append(data[i+1])

        all_r.append(r1)
        all_p.append(p1)
    return all_r,all_p, negative_cr_data, zero_cr_data, positive_cr_data


def plot_cor_sample(r, p, r_coff_savefile, p_coff_savefile):
    x1 = range(1,len(r)+1)
    y1 = r
    y2 = p
    xlabel = 'Data Sample'
    ylabel1 = 'Corelation Coefficient'
    ylabel2 = 'Pearson Coefficient'
    title1 = 'Corelation Coefficient Vs Data Sample'
    title2 = 'Pearson Coefficient Vs Data Sample'
    plt.xlabel(xlabel)
    plt.ylabel(ylabel1)
    plt.title(title1)
    plt.plot(x1,y1)
    plt.savefig(r_coff_savefile)

    plt.clf()

    plt.xlabel(xlabel)
    plt.ylabel(ylabel2)
    plt.title(title2)
    plt.plot(x1, y2)
    plt.savefig(p_coff_savefile)


def write_csv_from_list(data, filepath):
    with open(filepath, 'wb') as myfile:
        for item in data:
            count = 0
            size = len(item)
            for word in item:
                if(count == size-1):
                    myfile.write(str(word))
                else:
                    myfile.write(str(word) + ",")
                count = count + 1
            myfile.write("\n")



def partion_data(data, r_coff_savefile, p_coff_savefile, model_name, model_path):
    r1, p1, negative_cr_data, zero_cr_data, positive_cr_data = gen_corcoef(data)
    plot_cor_sample(r1, p1, r_coff_savefile, p_coff_savefile)
    print("Negative : " + str(len(negative_cr_data)))
    print("Zero : " + str(len(zero_cr_data)))
    print("Positive : " + str(len(positive_cr_data)))
    write_csv_from_list(negative_cr_data, model_path+model_name+"/negative/"+model_name+"_negative_cr_isesvm")
    write_csv_from_list(zero_cr_data, model_path + model_name +"/zero/"+model_name+"_zero_cr_isesvm")
    write_csv_from_list(positive_cr_data, model_path + model_name +"/positive/"+model_name+ "_positive_cr_isesvm")


# calc_test_cor()
input = sys.argv[1]
r_coff_savefile = sys.argv[2]
p_coff_savefile = sys.argv[3]
model_name = sys.argv[4]
model_path = sys.argv[5]
data = get_dataset(input)

partion_data(data, r_coff_savefile, p_coff_savefile, model_name, model_path)

