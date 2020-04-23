package tfgui.view.left;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import tfgui.controller.sshclient.SSHClient;
import tfgui.model.Model;
import tfgui.view.right.RightUnderView;
/*
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
* (https://github.com/boguss1225/TF-GUI)
* 
* ==========================================================================
* Description : This program allows users to train models, configure settings,
*		detect objects and control image data within GUI level. 
*		This program converted almost every steps of Tensorflow model 
*		training into GUI so that user can easily utilize Tensorflow. 
*		To operate this program, server need to have preinstalled 
*		Tensorflow virtual environment and relevant script code.
*/
public class Dialogs {

}
class putFilesDialog{
	private SSHClient sshclient;

	public putFilesDialog(String destinPath) throws IOException{
			sshclient = Model.sshclient;

			/*create open dialog*/
			JFileChooser fileChooser = new JFileChooser();

	        /*set initial file*/
	        fileChooser.setDialogTitle("Add Files to Server");
		 	fileChooser.setVisible(true);
		 	fileChooser.setMultiSelectionEnabled(true);
	        int option = fileChooser.showOpenDialog((JFrame)null);		 	

	        /*set action*/
		 	if(option == JFileChooser.APPROVE_OPTION){
	               File[] file = fileChooser.getSelectedFiles();
	               sshclient.putFile(file, destinPath);
	               RightUnderView.updateCMDtxtField("<<PSFTP : put file>>\n"+Arrays.toString(file));
	        }
		 	/*get path(directory and file name)*/
		 	LeftView.refreshview();
		}
}