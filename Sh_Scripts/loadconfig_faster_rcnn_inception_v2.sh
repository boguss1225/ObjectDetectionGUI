#! /bin/bash
# made by Heemoon Yoon in 2019
# UNIVERSITY OF TASMANIA
# Script for loading config files of tensorflow

# Parameter check
if [ -z "$1" ]; then
	echo "parameter error"
	exit
fi

# load config settings as a string
sed -n '/###/,/#@#/p' ~/tensorflowGUI/$1/models/research/object_detection/training/faster_rcnn_inception_v2.config

