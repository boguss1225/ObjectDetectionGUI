#! /bin/bash
# made by Heemoon Yoon in 2019
# UNIVERSITY OF TASMANIA
# Script for setting class files -> generate_tfrecord.py && labelmap.pbtxt

# Parameter check
if [ -z $2 ]; then
	echo "no class to set"
	exit
fi

# Store parameter in array *first parameter is always envName!
PARAMETERS=("$@")
count=S#

# Delete existing classes in generate_tfrecord.py file
flag=0
while read line
do
	# last line of class setting
        if [[ $line == "else: return 0" ]]; then
                flag=0
		echo "abcd"
                break
        fi

	# Delete existing classes
        if [ $flag == 1 ]; then
                `sed -i "/$line/d" ~/tensorflowGUI/$1/models/research/object_detection/generate_tfrecord.py`
        fi

	# first line of class setting
	if [[ $line == "def class_text_to_int(row_label):" ]]; then
		flag=1
	fi

done < ~/tensorflowGUI/$1/models/research/object_detection/generate_tfrecord.py

# Create new classes in generate_tfrecord.py file
while read line
do
	# first line of class setting
       	if [[ $line == "else: return 0" ]]; then
                flag=1
        fi

        # Add new class setting
	ITER=1
        if [ $flag == 1 ]; then
                for i in "${PARAMETERS[@]:1}"; do
			temp="$i"
			`sed -i "/$line/i \    if row_label == '$temp': return $ITER" ~/tensorflowGUI/$1/models/research/object_detection/generate_tfrecord.py`
			ITER=$(expr $ITER + 1)
		done
		break
        fi
done < ~/tensorflowGUI/$1/models/research/object_detection/generate_tfrecord.py

# Delete existing classes in labelmap.pbtxt file
`sed -i '1,$d' ~/tensorflowGUI/$1/models/research/object_detection/training/labelmap.pbtxt`

# Create new classes in labelmap.pbtxt file
ITER=1
for i in "${PARAMETERS[@]:1}"; do
	temp="$i"
	echo "item {" >> ~/tensorflowGUI/$1/models/research/object_detection/training/labelmap.pbtxt
	echo "id: $ITER" >> ~/tensorflowGUI/$1/models/research/object_detection/training/labelmap.pbtxt
	echo "name: '$temp'" >> ~/tensorflowGUI/$1/models/research/object_detection/training/labelmap.pbtxt
	echo "}" >> ~/tensorflowGUI/$1/models/research/object_detection/training/labelmap.pbtxt
	echo " " >> ~/tensorflowGUI/$1/models/research/object_detection/training/labelmap.pbtxt
	ITER=$(expr $ITER + 1)
done


