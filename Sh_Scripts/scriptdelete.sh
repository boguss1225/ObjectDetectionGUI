#! /bin/bash

echo "script1 start"
export PATH="~/anaconda3/bin:$PATH"
# default conda env
conda info|egrep "conda version|active environment"
# activate new env to prove that it works
conda activate tensorflowGUI1
conda info|egrep "conda version|active environment"

# Path Setting
echo "Setting Path"
cd /home/mirap/tensorflowGUI/tensorflowGUI1/models
export PYTHONPATH=$PYTHONPATH:/home/mirap/tensorflowGUI/tensorflowGUI1/models:home/mirap/tensorflowGUI/tensorflowGUI1/models/research:/home/mirap/tensorflowGUI/tensorflowGUI1/models/research/slim
echo $PYTHONPATH
export PATH=$PATH:PYTHONPATH
echo $PATH

echo "Script1 End"
