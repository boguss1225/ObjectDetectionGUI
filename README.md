# Easy Tensorflow Object Detection API GUI for anyone
Easy to use, Good looking, Highly utilized, and Light Tensorflow Tool.\
PygIDE (Python graphical integrated development environment) allows anyone, even without any previous knowledge of deep learning frameworks, to design, train and deploy machine intelligence models without coding.\
No need to Code! NO need type Command line!\
All you need to do is download exe file and go!\
![picture](https://github.com/boguss1225/ObjectDetectionGUI/blob/master/screenshot/Detect_faster_rccn_inception.png)

# Requirement for user
* Windows 10
* User needs to prepare set of images and annotation data.

# General Features
* Convert annotated dataset to tf-record files 
* Hyperparameter setting of configuration files
* Training various models
* Real time observation of training processing
* Object detection in test images using trained models
* evaluating model with various metrics

# Usage
## Download exe file and run in windows
(//source link)

## Set classes
![picture](https://github.com/boguss1225/ObjectDetectionGUI/blob/master/screenshot/step1setclasses.PNG)

## Generate TF-record files
![picture](https://github.com/boguss1225/ObjectDetectionGUI/blob/master/screenshot/step2-GenerateTFrecord.PNG)

## Set configurations
![picture](https://github.com/boguss1225/ObjectDetectionGUI/blob/master/screenshot/configuration.PNG)

## Training
![picture](https://github.com/boguss1225/ObjectDetectionGUI/blob/master/screenshot/Train-finished.PNG)

## Generate Pb file (frozen inference graph.pb)
![picture](https://github.com/boguss1225/ObjectDetectionGUI/blob/master/screenshot/convertCKPT-result2.PNG)

## Object Detection
* faster_rcnn_inception\
![picture](https://github.com/boguss1225/ObjectDetectionGUI/blob/master/screenshot/Detect_faster_rccn_inception.png)
* rfcn_resnet101\
![picture](https://github.com/boguss1225/ObjectDetectionGUI/blob/master/screenshot/Detect_rfcn_resnet101.png)
* ssd_mobilenet_v2\
![picture](https://github.com/boguss1225/ObjectDetectionGUI/blob/master/screenshot/Detect_ssd_mobilenet_v2.png)
* mask_rcnn_resnet101\
![picture](https://github.com/boguss1225/ObjectDetectionGUI/blob/master/screenshot/Detect_mask_rccn_resnet101.png)

## Evaluate trained model
![picture](https://github.com/boguss1225/ObjectDetectionGUI/blob/master/screenshot/eval_model-finished.PNG)

# Manual
step by step manual is available here\
https://drive.google.com/file/d/1mZkj5jhdDJcANsP8xcHKdmlPNxeHcmXM/view?usp=sharing

# Guide Video
User guid video is available here in youtube\
//(link)

# Requirements and specification for developer
* Tensorflow environment installed in Ubuntu 16.04.6 LTS
* Anaconda environment version 4.7.11 installed to establish Tensorflow virtual environment
* CUDA version: 10.2
* Tensorflow version: 1.14
* Python version: 3.5.6
* Pre-trained models: COCO dataset
* PygIDE tested within Windows 10

# News
PygIDE 1.0 was initially released on Github in 24th of Apr 2020.

# Request
Please email us if you need more information or free account\
heemoon.yoon@utas.edu.au

# Paper
//(link)

# if you want to cite this
//(citation detail)
