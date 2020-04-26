package tfgui.view.toolbar;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.SwingWorker;

import tfgui.controller.putty.runprocessDia;
import tfgui.controller.sshclient.SSHClient;
import tfgui.model.Model;
import tfgui.view.MainView;
import tfgui.view.right.RightUnderView;
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
public class ConvertCKPT extends JDialog {
	private static final long serialVersionUID = 1L;
	// Models setting
	private JComboBox modelselectionCB;
		
	ConvertCKPT(){
		/*redirect to training folder*/
		MainView.mainViewFrame.leftPane.showFolders("/home/"+Model.username+"/tensorflowGUI/"+Model.ActivatedEnv+"/models/research/object_detection/training");
		
		/*create new dialog*/
	 	this.setTitle("Convert CKPT to PB file");
	 	this.setVisible(true);
	 	this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 	
	 	/*set size of dialog*/
	 	this.setSize(250, 400);
	 	
	 	/*set location*/
	 	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - this.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - this.getHeight() / 2);
		this.setLocation(xpos, ypos);
	 	
	 	/*set layout*/
		this.setLayout(new BorderLayout());
	 	
		// northPane
		JPanel northP = northpane();
		
	 	/*result pane*/
		/*ckpt pane*/
		JPanel ckptpane = new ckptlistpane(this);
	 	
		/*set button1*/
		JButton b1 = new JButton("OK");
		class b1EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				dispose();
			}}
		b1.addActionListener(new b1EventHandler());
		JPanel pb1 = new JPanel(new FlowLayout());
		pb1.add(b1);
		
		/*add components*/
		JPanel buttonPane = new JPanel();
		buttonPane.add(pb1);
		this.add(northP, BorderLayout.NORTH);
		this.add(ckptpane, BorderLayout.CENTER);
		this.add(buttonPane, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	private JPanel northpane() {
		JPanel northpane = new JPanel(new BorderLayout());

		String[] model_types = { "faster_rcnn_inception_resnet_v2_atrous_coco",
				"faster_rcnn_inception_resnet_v2_atrous_lowproposals_coco", "faster_rcnn_inception_v2",
				"faster_rcnn_nas_coco", "faster_rcnn_nas_lowproposals_coco", "faster_rcnn_resnet50_coco",
				"faster_rcnn_resnet50_lowproposals_coco", "faster_rcnn_resnet101_coco",
				"faster_rcnn_resnet101_lowproposals_coco", "mask_rcnn_inception_resnet_v2_atrous_coco",
				"mask_rcnn_inception_v2_coco", "mask_rcnn_resnet50_atrous_coco", "mask_rcnn_resnet101_atrous",
				"rfcn_resnet101_coco", "ssd_inception_v2_coco", "ssd_mobilenet_v1_0.75_depth_300x300_coco",
				"ssd_mobilenet_v1_0.75_depth_quantized_300x300_coco", "ssd_mobilenet_v1_coco",
				"ssd_mobilenet_v1_fpn_shared_box_predictor_640x640_coco",
				"ssd_mobilenet_v1_ppn_shared_box_predictor_300x300_coco", "ssd_mobilenet_v1_quantized_300x300_coco",
				"ssd_mobilenet_v2_coco" };
		modelselectionCB = new JComboBox(model_types);

		// init selected model
		modelselectionCB.setSelectedItem(Model.selectedModel);

		// select action
		modelselectionCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedmodel = modelselectionCB.getSelectedItem().toString();
				Model.selectedModel = selectedmodel;
			}
		});

		northpane.add(modelselectionCB, BorderLayout.WEST);
		
		return northpane;
	}
}

class ckptlistpane extends JPanel{
	private static final long serialVersionUID = 1L;
	private SSHClient sshclient;
	private JLabel selectedFilenamel;
	private JButton convertbt;
	
	ckptlistpane(JDialog parent){
		//set Model
		sshclient = Model.sshclient;

		//set Layout
		this.setLayout(new BorderLayout(3,3));
		
		//set selected file Label		
		selectedFilenamel = new JLabel("Not selected");
		selectedFilenamel.setForeground(Color.red);
		
		//get list of ckpt files
		String filelist = sshclient.sendCommand("cd ~/tensorflowGUI/"+ Model.ActivatedEnv +"/models/research/object_detection/training"+" && find . -maxdepth 1 -not -type d");
		String[] filename = filelist.split("\n");
		int numofFiles = filename.length;
		int cnt = 0;
		for (int i = 0 ; i < numofFiles ; i ++){
			if(filename[i].startsWith("./model.ckpt")&&filename[i].endsWith("index")){
				filename[cnt] = filename[i].substring(2, filename[i].lastIndexOf("."));
				cnt++;
			}
		}
		
		//set file list panel
		JPanel filelistpanel = new JPanel(new GridLayout(5,1));
		for (int i = 0 ; i < cnt ; i ++){
			JButton filenamebt = new JButton(filename[i]);
			class filenamebtEventHandler implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent ae){
					selectedFilenamel.setText(filenamebt.getText());
					selectedFilenamel.setForeground(Color.ORANGE);
					convertbt.setForeground(Color.ORANGE);
				}}
			filenamebt.addActionListener(new filenamebtEventHandler());
			filelistpanel.add(filenamebt);
		}
		   //fill empty cells for clean layout
		for(int i = 0 ; i < 5-cnt ; i++) 
			filelistpanel.add(new JLabel());
		
		//set selected File Name Panel
		JPanel selectedFileNamePanel = new JPanel(new FlowLayout());
		convertbt = new JButton("Convert-->");
		class convertbtEventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				if(selectedFilenamel.getText().equals("Not selected")) {
					//show error message
					JOptionPane.showMessageDialog((JFrame)null,
							"Please select ckpt in list!",
							"Inane warning",
							JOptionPane.WARNING_MESSAGE);
				}else
					new convertdia(selectedFilenamel.getText(), parent);
			}}
		convertbt.addActionListener(new convertbtEventHandler());
		selectedFileNamePanel.add(convertbt);
		selectedFileNamePanel.add(selectedFilenamel);
		
		//add components
		JLabel lb = new JLabel("Select a file to convert to 'pb'");
		this.add(lb, BorderLayout.NORTH);
		this.add(filelistpanel,BorderLayout.CENTER);
		this.add(selectedFileNamePanel,BorderLayout.SOUTH);
	}
}

class convertdia{
	convertdia(String str, JDialog parent){
		/*create new dialog*/
	 	JDialog Dia = new JDialog((JFrame)null,"convert 'ckpt' to 'pb' file",true);
	 	Dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 	
	 	/*set size of dialog*/
	 	Dia.setSize(300, 150);
	 	
	 	/*set location*/
	 	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - Dia.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - Dia.getHeight() / 2);
		Dia.setLocation(xpos, ypos);
	 	
	 	/*set layout*/
	 	Dia.setLayout(new BorderLayout(3,3));
	 	
	 	/*get number*/
	 	String number = str.substring(str.lastIndexOf("-")+1,str.length());
	 		 	
	 	/*result pane*/
	 	JLabel infolabel = new JLabel(" Do you want to convert \n" + str + " to 'pb' file?");

		/*set button1*/
		JButton b1 = new JButton("OK");
		class b1EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				//close all step1 dialog
				String cmd = "cd /home/"+Model.username+"/tensorflowGUI/scripts "
						+ "&& bash converckpt.sh "
						+ Model.ActivatedEnv +" "
						+ number + " "
						+ Model.selectedModel;
				
				//run Process
				runprocessDia rundia = new runprocessDia(cmd);
				//Test process whether finished
				@SuppressWarnings("rawtypes")
				SwingWorker sw = new SwingWorker() {
					@Override
					protected String doInBackground() throws Exception {
						while(true) {
							//check whether finished
							if(!rundia.getongoinflag())
								break;
							Thread.sleep(1000);
						}
						//finished normally
						if(rundia.getprocessfinishedsafely()) {
							//update log on RightUnderView
							RightUnderView.updateCMDtxtField("**** Step4 'Convert ckpit file' executed ****\n");
							parent.dispose();
						}
						return "";
					}
				};
				sw.execute();
				Dia.dispose();
			}}
		b1.addActionListener(new b1EventHandler());
		JPanel pb1 = new JPanel(new FlowLayout());
		pb1.add(b1);
		
		/*set button2*/
		JButton b2 = new JButton("Cancel");
		class b2EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				//close all step1 dialog
				Dia.dispose();
			}}
		b2.addActionListener(new b2EventHandler());
		JPanel pb2 = new JPanel(new FlowLayout());
		pb2.add(b2);
		
		/*add components*/
		JPanel buttonPane = new JPanel(new FlowLayout());
		buttonPane.add(pb1);
		buttonPane.add(pb2);
		Dia.add(infolabel, BorderLayout.CENTER);
		Dia.add(buttonPane, BorderLayout.SOUTH);
		Dia.setVisible(true);
	}
}