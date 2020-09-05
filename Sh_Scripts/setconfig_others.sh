#! /bin/bash
# made by Heemoon Yoon in 2019
# UNIVERSITY OF TASMANIA
# Script for setting config files of tensorflow

# Parameter check
if [ -z "$3" ]; then
	echo "parameter error"
	exit
fi

# Delete existing lines in config file
`sed -i '1,$d' ~/tensorflowGUI/$1/models/research/object_detection/training/$2`

# Rewrite config file
echo "$3" >> ~/tensorflowGUI/$1/models/research/object_detection/training/$2

