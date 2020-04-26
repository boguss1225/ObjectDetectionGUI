package tfgui.model;

import tfgui.controller.sshclient.SSHClient;
/**
* Copyright 2019 The Block-AI-VIsion Authors. All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* ==========================================================================
*
* This file is part of Tensorflow GUI program under Block-AI-VIsion project.
*
* Made in University of Tasmania, Australia.
*
* @Authors : Dr.Mira Park (mira.park@utas.edu.au)
*	     Dr.Sanghee Lee (knusang1799@gmail.com)
*	     Heemoon Yoon (boguss1225@gmail.com)
*
* Date : Initial Development in 2019
*
* For the latest version, please check the github 
* (https://github.com/boguss1225/ObjectDetectionGUI)
* 
* ==========================================================================
* Description : This program allows users to train models, configure settings,
*		detect objects and control image data within GUI level. 
*		This program converted almost every steps of Tensorflow model 
*		training into GUI so that user can easily utilize Tensorflow. 
*		To operate this program, server need to have preinstalled 
*		Tensorflow virtual environment and relevant script code.
*/
public class Model {
	public static String username = null;
	private static String password = null;
	public static String connectionIP = null;
	
	public static SSHClient sshclient = null;
	public static String ActivatedEnv = null;
	public static String FilePath = null;
	public static String selectedModel = "faster_rcnn_inception_v2";
	
	//Config details
	public static int numofclass = 3;
	public static String[] classlist = null;
	public static String destinPath = "/home/user/not/assigned/path/yet";
	public static String metrics_set = "coco_detection_metrics";
	
	public static void initializeModel(){
		ActivatedEnv = null;
		FilePath = null;
		numofclass =3;
		classlist = null;
		destinPath = "/home/user/not/assigned/path/yet";
		metrics_set = null;
	}
	
	public static void setpassword(String src){ password = src; }
	public static String getpassword(){ return password; }

	public static void setclass() {
		String classchunk = sshclient.sendCommand("cd /home/"+ username+"/tensorflowGUI/scripts "
	 			+ "&& bash classlist.sh"
	 			+ " " + ActivatedEnv);
		
	 	classlist = classchunk.split("\n");
	 	numofclass = classlist.length;
	 	for(int i = 0 ; i < numofclass ; i++ ){
	 		 classlist[i]=classlist[i].substring(classlist[i].indexOf('\'')+1);
	 		 classlist[i]=classlist[i].substring(0, classlist[i].indexOf('\''));
	 	}
	}

	public static void setmetricsSet() {
		//get data from sshclient
		String temp = sshclient.sendCommand("cd /home/"+ username+"/tensorflowGUI/scripts "
								+ "&& bash loadconfig.sh "
								+ ActivatedEnv);
				
		//convert data into array
		String [] configdata = new String[35];
		configdata = temp.split(" ");
		metrics_set = configdata[31];
	}
}
