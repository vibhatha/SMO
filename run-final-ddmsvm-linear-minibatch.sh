#expid partitions c gamma kernel base_acc datasource
#sh run-full-ddmsvm.sh 41 40 10 3 gaussian 50 webspam
datasource=$1
expid=$2
minibatch_times=$3
#for c in `seq 1 2 50`;
#do
#    for gamma in `seq 0.1 0.1 10`;
#    do
#       for acc in `seq 50 5 90`;
#       do
#           sh run-full-ddmsvm.sh $expid 40 $c $gamma linear $acc webspam
#           #echo $expid 40 $c $gamma $acc
#           expid=$((expid+1))           
#       done
#   done  
#done

for c in `seq 1 2 10`;
do
    for acc in `seq 90 5 95`;
    do
        sh run-ddmsvm-minibatch-dynamic.sh $expid 40 $c 0.1 linear $acc $datasource $minibatch_times
        #echo $expid 40 $c $gamma $acc
        expid=$((expid+1))           
    done      
    echo "Iteration C:${c} Linearn  ${datasource} done" | mail -s "Job notice Linear ${datasource} " vibhatha@gmail.com
done
echo "Job DDMSVM Linear Minibatch: ${datasource} Completed" | mail -s "Job Completion Linear ${datasource}" vibhatha@gmail.com

