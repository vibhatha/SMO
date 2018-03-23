python libsvmformatter/corelationmapper.py data/hrt_csv_trn.csv plot/r_coff_1.png plot/p_coff_1.png heart data/model_partition/
python libsvmformatter/libsvm2Isesvm.py data/model_partition/heart/negative/heart_negative_cr_isesvm data/model_partition/heart/negative/heart_negative_cr_isesvm True 0.6
python libsvmformatter/libsvm2Isesvm.py data/model_partition/heart/positive/heart_positive_cr_isesvm data/model_partition/heart/positive/heart_positive_cr_isesvm True 0.6

