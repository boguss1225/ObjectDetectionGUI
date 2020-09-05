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

conda activate $1
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
        if [[ $line == "# Path to video" ]]; then
                read line
		temp="$i"
                `sed -i "/$line/cPATH_TO_IMAGE = os.path.join(CWD_PATH,IMAGE_NAME,'test','$temp') ###" ~/tensorflowGUI/$1/models/research/object_detection/Object_detection_video_modified.py`
        fi

	# edit num of classes
	if [[ $line == "# Number of classes the object detector can identify" ]]; then
                read line
		temp=$2
                `sed -i "/$line/cNUM_CLASSES = $temp ##" ~/tensorflowGUI/$1/models/research/object_detection/Object_detection_video_modified.py`
        fi

	# edit util setting
        if [[ $line == *use_normalized_coordinates=True, ]]; then
		read line
                `sed -i "/$line/c\            line_thickness=$3, ##" ~/tensorflowGUI/$1/models/research/object_detection/Object_detection_video_modified.py`
		read line
		`sed -i "/$line/c\            min_score_thresh=$4) ##" ~/tensorflowGUI/$1/models/research/object_detection/Object_detection_video_modified.py`
		break
        fi

done < ~/tensorflowGUI/$1/models/research/object_detection/Object_detection_video_modified.py

# Run Training
cd ~/tensorflowGUI/$1/models/research/object_detection/
python Object_detection_video_modified.py

done



