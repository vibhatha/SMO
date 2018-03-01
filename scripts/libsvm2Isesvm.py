import numpy
import sys
import csv

def file_len(file_name):
    with open(file_name) as f:
        for i, l in enumerate(f):
            pass
    return i + 1


ratio=0.6
input_file = sys.argv[1]
output_file = sys.argv[2]
splitter = False
if(sys.argv[3]=='True'):
    splitter = True
    ratio=sys.argv[4]
    print(splitter)


reader = csv.reader( open( input_file), delimiter=",")

if (splitter==False):
    writer_x = csv.writer(open(output_file + "_x", 'wb'))
    writer_y = csv.writer(open(output_file + "_y", 'wb'))
    for line in reader:
        label = line.pop(0).split(',')
        features = line
        writer_x.writerow(line)
        writer_y.writerow(label)

if (splitter==True):
    count =0
    writer_x_train = csv.writer(open(output_file + "_train_x", 'wb'))
    writer_y_train = csv.writer(open(output_file + "_train_y", 'wb'))
    writer_x_test = csv.writer(open(output_file + "_test_x", 'wb'))
    writer_y_test = csv.writer(open(output_file + "_test_y", 'wb'))
    size = file_len(input_file)
    for line in reader:
        label = line.pop(0).split(',')
        features = line
        if(count  <=  size * float(ratio)):
            writer_x_train.writerow(line)
            writer_y_train.writerow(label)
        else:
            writer_x_test.writerow(line)
            writer_y_test.writerow(label)
        count = count + 1
