#! /bin/bash
# made by Heemoon Yoon in 2019
# UNIVERSITY OF TASMANIA
# Script for getting class list by echoing class lines

flag=0
while read line
do
        # last line of class setting
        if [[ $line == "else: return 0" ]]; then
                flag=0
                break
        fi

        # Delete existing classes
        if [ $flag == 1 ]; then
                echo $line
        fi

        # first line of class setting
        if [[ $line == "def class_text_to_int(row_label):" ]]; then
                flag=1
        fi

done < ~/tensorflowGUI/$1/models/research/object_detection/generate_tfrecord.py

