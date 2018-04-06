#expid partitions c gamma kernel base_acc datasource
#sh run-full-ddmsvm.sh 41 40 10 3 gaussian 50 webspam
for expid in `seq 42 1 50`;
do
    for c in `seq 1 10`;
    do
	for gamma in `seq 1 0.1 2`;
	do
	    for acc in `seq 50 5 80`;
	    do	 
		 sh run-full-ddmsvm.sh 41 40 $c $gamma gaussian $acc webspam
	    done	 
	done	
    done  
done

