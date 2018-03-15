import pandas as pd
import matplotlib.pyplot as plt
import sys

src_dir = "stats/"
input_file="covtype_libsvm_ise_test_x_bin.1_1"
output_file=input_file+".png"
output_dir="stats/plots/"
xlabel="Models"
ylabel="Accuracy"
title="Accuracy Variation with Models"
expName="CovType"
expId="-1-"
modelType="SDMM-"

if(len(sys.argv)==2):
    input_file = sys.argv[1]
    print("Reading file : "+input_file)

if(len(sys.argv)==11):
    src_dir = sys.argv[1]
    input_file = sys.argv[2]
    output_dir = sys.argv[3]
    output_file = sys.argv[4]
    xlabel = sys.argv[5]
    ylabel = sys.argv[6]
    title = sys.argv[7]
    expName = sys.argv[8]
    expId = sys.argv[9]
    modelType = sys.argv[10]

    print("Reading file : "+input_file)

df = pd.read_csv(src_dir+input_file)
data = df.get_values()
x=[]
y=[]
count=0
for item in data:
    print(item[0])
    count = count + 1
    x.append(item[0])
    y.append(count)

print(x)
print(y)

plt.plot(y,x)
plt.xlabel(xlabel)
plt.ylabel(ylabel)
plt.title(title)
plt.savefig(output_dir+modelType+expName+expId+output_file)
plt.show()