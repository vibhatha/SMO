from csv import reader
import sys
from sklearn import preprocessing
import numpy as np

# Load a CSV file
def load_csv(filename):
	file = open(filename, "rb")
	lines = reader(file)
	dataset = list(lines)
	return dataset

# Convert string column to float
def str_column_to_float(dataset, column):
	for row in dataset:
		row[column] = float(row[column].strip())

# Find the min and max values for each column
def dataset_minmax(dataset):
	minmax = list()
	for i in range(len(dataset[0])):
		col_values = [row[i] for row in dataset]
		value_min = min(col_values)
		value_max = max(col_values)
		minmax.append([value_min, value_max])
	return minmax

def write_normalize(dataset, file_path):
    file = open(file_path, 'w')
    for i in range(len(dataset[0])):
        str_column_to_float(dataset, i)
    minmax = dataset_minmax(dataset)
    normalize_dataset(dataset, minmax)
    for items in dataset:
        count = 0
        for item in items:
            if(count==len(items)-1):
                file.write("%s" % item)
            else:
                file.write("%s," % item)
            count = count + 1
        file.write("\n")


def write_normalze_sklrn(dataset, file_path):
    print(file_path)
    file = open(file_path, 'w')
    np.asarray(dataset)
    print(dataset)
    dataset_scaled = preprocessing.scale(dataset)
    print(dataset_scaled)
    dataset_list = dataset_scaled.tolist()
    for items in dataset_list:
        count = 0
        for item in items:
            if(count==len(items)-1):
                file.write("%s" % item)
            else:
                file.write("%s," % item)
            count = count + 1
        file.write("\n")


# Rescale dataset columns to the range 0-1
def normalize_dataset(dataset, minmax):
	for row in dataset:
		for i in range(len(row)):
			row[i] = (row[i] - minmax[i][0]) / (minmax[i][1] - minmax[i][0])


def parse_args(argv):
    print(argv)
    source_file_name = ""
    dest_file_name = ""
    if(len(argv)==2):
        source_file_name = argv[1]
        dest_file_name = source_file_name + "_bin"
    elif(len(argv)==3):
        source_file_name = argv[1]
        dest_file_name = argv[2]
    else:
        print("Usage :")
        print("python normalize.py <source_file> <dest_file>")

    return source_file_name, dest_file_name

source_file, dest_file = parse_args(sys.argv)
dataset = load_csv(source_file)
# write_normalize(dataset, dest_file)
write_normalze_sklrn(dataset, dest_file)
