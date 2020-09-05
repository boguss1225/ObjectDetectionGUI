#! /bin/bash
# Made by Heemoon Yoon in 2019
# Heemoon.yoon@utas.edu.au
# UNIVERSITY OF TASMANIA
# This shell script is for detecting object in image files
# This script is part of TF-GUI project

# Store parameter in array *first parameter is always envName!
PARAMETERS=("$@")

if [[ ${#PARAMETERS[@]} -lt 5 ]]; then
        echo "not enough PARAMETERS: $PARAMETERS"
        exit
fi

# activate tensorflow env
export PATH="~/anaconda3/bin:$PATH"
CONDA_BASE=$(conda info --base)
source $CONDA_BASE/etc/profile.d/conda.sh

conda activate tensorflowCPU
conda info|egrep "conda version|active environment"

# Path Setting
cd ~/tensorflowGUI/$1/models/research
export PYTHONPATH=$PYTHONPATH:`pwd`:`pwd`/slim
export PATH=$PATH:PYTHONPATH

# repeat as many as num of files
for i in "${PARAMETERS[@]:4}"; do

#Edit python code file
while read line
do
        # edit image name
        if [[ $line == "# IMAGE_NAME" ]]; then
                read line
		temp="$i"
                `sed -i "/$line/cIMAGE_NAME = '$i' ###" ~/tensorflowGUI/$1/models/research/object_detection/Object_detection_image.py`
        fi

	# edit image save name
        if [[ $line == "# IMAGE_SAVE_NAME" ]]; then
                read line
                temp="$i"
                temp="save_${temp}"
                `sed -i "/$line/cIMAGE_SAVE_NAME = '$temp' ###" ~/tensorflowGUI/$1/models/research/object_detection/Object_detection_image.py`
        fi


	# edit num of classes
	if [[ $line == "# Number of classes the object detector can identify" ]]; then
                read line
                `sed -i "/$line/cNUM_CLASSES = $2 ###" ~/tensorflowGUI/$1/models/research/object_detection/Object_detection_image.py`
        fi

	# edit util setting
        if [[ $line == "# Edit_Settings" ]]; then
		read line
                `sed -i "/$line/c\    line_thickness=$3, ###" ~/tensorflowGUI/$1/models/research/object_detection/Object_detection_image.py`
		read line
		`sed -i "/$line/c\    min_score_thresh=$4) ###" ~/tensorflowGUI/$1/models/research/object_detection/Object_detection_image.py`
		break
        fi
done < ~/tensorflowGUI/$1/models/research/object_detection/Object_detection_image.py

# Run Training
cd ~/tensorflowGUI/$1/models/research/object_detection/
python Object_detection_image.py

done



