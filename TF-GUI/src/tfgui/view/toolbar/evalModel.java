package tfgui.view.toolbar;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tfgui.controller.putty.runPutty;
import tfgui.controller.putty.runprocessDia;
import tfgui.model.Model;
import tfgui.view.MainView;
import tfgui.view.right.Step3Dialog;
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
public class evalModel {
	
	public evalModel(){
		/*create new dialog*/
	 	JDialog Dia = new JDialog((JFrame)null,"Evaluation Model",true);
	 	Dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 	
	 	/*set size of dialog*/
	 	Dia.setSize(300, 100);
	 	
	 	/*set location*/
	 	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - Dia.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - Dia.getHeight() / 2);
		Dia.setLocation(xpos, ypos);
	 	
	 	/*set layout*/
	 	Dia.setLayout(new BorderLayout());
	 	
	 	/*announcement */
	 	JPanel labelp = new JPanel(new FlowLayout());
	 	JLabel l1 = new JLabel("Evaluation metrics set ");
	 	String[] metrics_settypes = { "coco_detection_metrics", "coco_mask_metrics", "pascal_voc_detection_metrics",
				"weighted_pascal_voc_detection_metrics", "pascal_voc_instance_segmentation_metrics",
				"weighted_pascal_voc_instance_segmentation_metrics", "oid_V2_detection_metrics",
				"open_images_V2_detection_metrics", "oid_challenge_detection_metrics",
				"oid_challenge_object_detection_metrics", "oid_challenge_segmentation_metrics" };
	 	JComboBox metrics_setCB = new JComboBox(metrics_settypes);
	 	metrics_setCB.setSelectedItem(Model.metrics_set);
	 	// select action
	 	metrics_setCB.addActionListener(new ActionListener() {
	 		public void actionPerformed(ActionEvent e) {
	 			String selectedmetrics = metrics_setCB.getSelectedItem().toString();
	 			Model.metrics_set = selectedmetrics;
	 		}
	 	});
	 	l1.setToolTipText("If you want to change metrics set setting -> please finish step 3. configure setting");
	 	labelp.add(l1);
	 	labelp.add(metrics_setCB);
	 	
	 	/*set button1*/
		JButton b1 = new JButton("Start Evaluation");
		class b1EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				Dia.dispose();
				runevalDia();
			}}
		b1.addActionListener(new b1EventHandler());
		JPanel pb1 = new JPanel(new FlowLayout());
		pb1.add(b1);
	
		/*set button2*/
		JButton b2 = new JButton("cancel");
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
		Dia.add(labelp, BorderLayout.CENTER);
		Dia.add(buttonPane, BorderLayout.SOUTH);
		Dia.pack();
		Dia.setVisible(true);
	}

	private void runevalDia(){
		/*redirect to training folder*/
		MainView.mainViewFrame.leftPane.showFolders("/home/"+Model.username+"/tensorflowGUI/"+Model.ActivatedEnv+"/models/research/object_detection/training");

		//set command
		String command = "cd /home/"+Model.username+"/tensorflowGUI/scripts "
				+ "&& bash runeval.sh "
				+ Model.ActivatedEnv+" "
				+ Model.selectedModel;

		//run Process
		new runprocessDia(command);
		
	}
	
}
