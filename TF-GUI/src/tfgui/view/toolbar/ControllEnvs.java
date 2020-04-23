package tfgui.view.toolbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import tfgui.controller.sshclient.SSHClient;
import tfgui.model.Model;
import tfgui.view.MainView;
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
public class ControllEnvs {

}

class OpenEnv{
	private int numofEnvs = 3;
	private JButton[] btn;

	public  OpenEnv(SSHClient sshclient){
		/*create new dialog*/
	 	JDialog Dia = new JDialog((JFrame)null,"Open Environment",true);
	 	Dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 	
	 	/*set size of dialog*/
	 	Dia.setSize(200, 200);
	 	
	 	/*set location*/
	 	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - Dia.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - Dia.getHeight() / 2);
		Dia.setLocation(xpos, ypos);
	
	 	/*set label*/
	 	JLabel l1 = new JLabel("Availiable Environments");
	 	l1.setHorizontalAlignment(SwingConstants.CENTER);
	 	
	 	/*get environment list*/
	 	String envlist = sshclient.sendCommand("cd /home/"+Model.username+"/tensorflowGUI/scripts "
	 			+ "&& bash envlist.sh"
	 			+ " " + Model.username);
	 	String[] envl = envlist.split("# conda environments:\n#");
	 	String[] envline = envl[1].split("\n");
	 	numofEnvs = envline.length-2;
	 	String[] listofEnvName = new String[numofEnvs];
	 	String[] envName = {"base"};
	 	for(int i = 0 ; i < numofEnvs ; i++ ){
		 	envName = envline[i+2].split(" ",2);
	 		listofEnvName[i] = envName[0];
	 	}

	 	/*set layout*/
	 	Dia.setLayout(new GridLayout(numofEnvs+1,1,3,3));
	 	
	 	/*set buttons*/
	 	btn = new JButton[numofEnvs];
	 	for(int i = 0 ; i < numofEnvs ; i++){
	 		btn[i] = new JButton(listofEnvName[i]);
			class btnEventHandler implements ActionListener{
				String envName;
				public btnEventHandler(String envNamePassed) {
					this.envName = envNamePassed;
				}
				@Override
				public void actionPerformed(ActionEvent ae){
					System.out.println(envName + " environment is selected");
					Model.ActivatedEnv= envName;
					MainView.mainViewFrame.underpane.setActivatedEnvName(envName);
					
					//set exist classes and config data to model
					Model.setclass();
					Model.setmetricsSet();
					
					//show folders of activated env
					MainView.mainViewFrame.leftPane.showFolders("/home/"+Model.username+"/tensorflowGUI/"+envName+"/models/research/object_detection/images");
					Dia.dispose();
				}}
			btn[i].addActionListener(new btnEventHandler(listofEnvName[i]));
	 	}

	 	/*add components*/
	 	Dia.add(l1, BorderLayout.NORTH);
	 	for(int i = 0 ; i < numofEnvs ; i++){
	 		Dia.add(btn[i]);
	 	}
		Dia.pack();
		Dia.setVisible(true);
	}
}