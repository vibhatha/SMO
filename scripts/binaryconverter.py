import csv
import sys

input = sys.argv[1]
output = sys.argv[2]

with open(input, 'r') as input_file, open(output, 'w') as output_file:
    for line in input_file:
        if line.strip() == '2':
            output_file.write('-1\n')
        else:
            output_file.write(line)
