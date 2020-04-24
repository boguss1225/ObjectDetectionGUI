package tfgui.view.right;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import tfgui.controller.putty.runPutty;
import tfgui.controller.putty.runprocessDia;
import tfgui.controller.view.ModifiedFlowLayout;
import tfgui.model.Model;
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
public class Step4Dialog {
	JTextField GPUselectionTF;
	
	Step4Dialog(){
		
		/*create new dialog*/
	 	JDialog Dia = new JDialog((JFrame)null,"Training Run confirmation",true);
	 	Dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 	
	 	/*set size of dialog*/
	 	Dia.setSize(600, 570);
	 	
	 	/*set location*/
	 	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - Dia.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - Dia.getHeight() / 2);
		Dia.setLocation(xpos, ypos);
	 	
	 	/*set layout*/
	 	Dia.setLayout(new BorderLayout());

	 	//set contents pane
	 	JPanel contentsP = new JPanel(new ModifiedFlowLayout());
	 	JTextArea GPUstatTA = new JTextArea();
	 	GPUselectionTF = new JTextField();
	 	//gpu status
	 	String GPUstat = Model.sshclient.sendCommand("nvidia-smi");
	 	GPUstatTA.setText(GPUstat);
	 	GPUstatTA.setEditable(false);
	 	//gpu selection
	 	JPanel GPUselectionP= new JPanel(new FlowLayout());
	 	JLabel l1 = new JLabel("Select GPU ----> ");
	 	l1.setHorizontalAlignment(SwingConstants.CENTER);
	 	GPUselectionTF.setText("0");
	 	GPUselectionTF.setColumns(2);
	 	GPUselectionP.add(l1);
	 	GPUselectionP.add(GPUselectionTF);
	 	/*announcement */
	 	JLabel l2 = new JLabel("************************************ Run Training? ************************************");
	 	l2.setHorizontalAlignment(SwingConstants.CENTER);
	 	//add components
	 	contentsP.add(GPUstatTA);
	 	contentsP.add(GPUselectionP);
	 	contentsP.add(l2);
	 	
		/*set button1*/
		JButton b1 = new JButton("OK");
		class b1EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				//close all step4 dialog
				Dia.dispose();
				String selectedGPU = GPUselectionTF.getText();

				//set command
				String command = "cd /home/"+Model.username+"/tensorflowGUI/scripts "
						+ "&& bash runTraining.sh "
						+ Model.ActivatedEnv+" "
						+ Model.selectedModel+" "
						+ selectedGPU;
				
				//run Putty
				//new runPutty(command);
				new runprocessDia(command);
				
				RightUpperView.trainingbtn.setBackground(null);
				RightUpperView.trainingbtn.setForeground(null);

			}}
		b1.addActionListener(new b1EventHandler());
		JPanel pb1 = new JPanel(new FlowLayout());
		pb1.add(b1);
	
		/*set button2*/
		JButton b2 = new JButton("back");
		class b2EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				Dia.dispose();
			}}
		b2.addActionListener(new b2EventHandler());
		JPanel pb2 = new JPanel(new FlowLayout());
		pb2.add(b2);
		
		/*add components*/
		JPanel buttonPane = new JPanel(new GridLayout(1,2));
		buttonPane.add(pb1);
		buttonPane.add(pb2);
		Dia.add(contentsP, BorderLayout.CENTER);
		Dia.add(buttonPane, BorderLayout.SOUTH);
		Dia.setVisible(true);
		Dia.pack();
	}
}
