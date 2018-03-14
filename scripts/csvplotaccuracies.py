import pandas as pd
import matplotlib.pyplot as plt
import sys

src_dir = "stats/"
input_file="covtype_libsvm_ise_test_x_bin.1_1"

if(len(sys.argv)==2):
    input_file = sys.argv[1]
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
plt.xlabel("Models")
plt.ylabel("Accuracy")
plt.title("Accuracy Variation with Models")
plt.savefig("stats/plots/"+input_file+".png")
plt.show()