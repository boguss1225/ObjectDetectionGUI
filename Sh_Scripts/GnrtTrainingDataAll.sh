#! /bin/bash
# Made by Heemoon Yoon in 2019
# UNIVERSITY OF TASMANIA
# This shell script is part of tensorflow gui program
# This enable program to create record file for tensorflow training

# Activate Conda
export PATH="~/anaconda3/bin:$PATH"
CONDA_BASE=$(conda info --base)

source $CONDA_BASE/etc/profile.d/conda.sh
conda activate $1
conda info|egrep "conda version|active environment"

# Path Setting
cd ~/tensorflowGUI/$1/models/research
export PYTHONPATH=$PYTHONPATH:`pwd`:`pwd`/slim
export PATH=$PATH:PYTHONPATH

# Generate Data
cd ~/tensorflowGUI/$1/models/research/object_detection
python xml_to_csv.py

python generate_tfrecord.py --csv_input=images/train_labels.csv --image_dir=images/train --output_path=train.record
python generate_tfrecord.py --csv_input=images/test_labels.csv --image_dir=images/test --output_path=test.record

