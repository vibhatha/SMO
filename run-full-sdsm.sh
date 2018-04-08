#expid partitions c gamma kernel base_acc datasource
#sh run-full-ddmsvm.sh 41 40 10 3 gaussian 50 webspam

expid=52
#for c in `seq 1 2 50`;
#do
#    for gamma in `seq 0.1 0.1 10`;
#    do
#	for acc in `seq 50 5 90`;
#	do
#	    sh run-full-ddmsvm.sh $expid 40 $c $gamma linear $acc webspam
#	    #echo $expid 40 $c $gamma $acc
#	    expid=$((expid+1))	    	 
#	done	
#   done  
#done

for c in `seq 1 2 50`;
do
    for gamma in `seq 0.1 0.1 0.1`;
    do
	for acc in `seq 75 5 90`;
	do
	    sh run-full-ddmsvm.sh $expid 40 $c $gamma linear $acc ijcnn1
	    #echo $expid 40 $c $gamma $acc
	    expid=$((expid+1))	    	 
	done	
    done  
done

