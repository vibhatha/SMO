import numpy
import sys
import csv


input_file = sys.argv[1]
output_file = sys.argv[2]

reader = csv.reader( open( input_file), delimiter=",")
writer_x = csv.writer(open(output_file+"_x", 'wb'))
writer_y = csv.writer(open(output_file+"_y", 'wb'))

for line in reader:
    label = line.pop(0).split(',')
    features = line
    writer_x.writerow(line)
    writer_y.writerow(label)
