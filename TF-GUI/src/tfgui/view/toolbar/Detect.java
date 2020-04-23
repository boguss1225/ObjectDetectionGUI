package tfgui.view.toolbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import tfgui.model.Model;
import tfgui.view.MainView;
import tfgui.view.left.LeftView;
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
public class Detect{
	private String currentpath;
	private String destinPath = Model.destinPath;
	private JTextField l1txtfld;
	private JTextField l2txtfld;
	private JTextField l3txtfld;
	private JTextField l4txtfld;
	private int thickness = 3;
	private double min_score_thresh = 0.7;
	
	public Detect(String flag){
		currentpath = MainView.mainViewFrame.leftPane.getcurrentPath();
		
		/*create new dialog*/
	 	JDialog Dia = new JDialog((JFrame)null,"Object Detection",true);
	 	Dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 	
	 	/*set size of dialog*/
	 	Dia.setSize(400, 230);
	 	
	 	/*set location*/
	 	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - Dia.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - Dia.getHeight() / 2);
		Dia.setLocation(xpos, ypos);
	 	
	 	/*set layout*/
	 	Dia.setLayout(new BorderLayout());
	 	
	 	/*announcement*/
	 	JLabel lb = new JLabel("Detection Setting");
	 	
	 	/*Body*/
	 	JPanel bodyp = new JPanel(new FlowLayout(FlowLayout.LEFT));
	 	JPanel thicknessAndMinScoreP = new JPanel(new GridLayout(3,2,2,2));
	 		JLabel l1 = new JLabel("Num of Class");
	 		l1.setHorizontalAlignment(SwingConstants.LEFT);
	 		l1txtfld = new JTextField(Integer.toString(Model.numofclass));
	 		l1txtfld.setEditable(false);
	 		String listofclass="";
	 			for(int i = 0; i < Model.numofclass ; i++){
	 				listofclass = listofclass + Model.classlist[i]+" / ";
	 			}
	 		l1txtfld.setToolTipText("list of class:[ "+listofclass+"] if it is wrong, do step1 & step3");
	 		thicknessAndMinScoreP.add(l1);
	 		thicknessAndMinScoreP.add(l1txtfld);
	 		JLabel l2 = new JLabel("Box Line Thickness");
	 		l2.setHorizontalAlignment(SwingConstants.LEFT);
	 		l2txtfld = new JTextField("3");
	 		thicknessAndMinScoreP.add(l2);
	 		thicknessAndMinScoreP.add(l2txtfld);
	 		JLabel l3 = new JLabel("Min_score_thresh");
	 		l3.setHorizontalAlignment(SwingConstants.LEFT);
	 		l3txtfld = new JTextField("0.7");
	 		thicknessAndMinScoreP.add(l3);
	 		thicknessAndMinScoreP.add(l3txtfld);
	 	JPanel pathP = new JPanel(new FlowLayout());
	 		JLabel l4 = new JLabel("Saving_Path");
	 		l4txtfld = new JTextField(destinPath);
	 		JButton l4btn = new JButton("/..");
	 		class l4btnEventHandler implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent ae){
					//open dialog for path : destinPath
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = chooser.showOpenDialog(MainView.mainViewFrame);
					
					//if choose destin path
					if(returnVal == JFileChooser.APPROVE_OPTION){
						destinPath = chooser.getSelectedFile().getAbsolutePath();
						Model.destinPath = destinPath;
					}
					
					//display on text field
					l4txtfld.setText(destinPath); 
				}}
	 		l4btn.addActionListener(new l4btnEventHandler());
	 		pathP.add(l4);
	 		pathP.add(l4txtfld);
	 		pathP.add(l4btn);
	 	bodyp.add(thicknessAndMinScoreP);
	 	bodyp.add(pathP);
	 	
		/*set button1*/
		JButton b1 = new JButton("OK");
		class b1EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				//get values from text field
				thickness = Integer.parseInt(l2txtfld.getText().trim());
				min_score_thresh = Double.parseDouble(l3txtfld.getText().trim());
				
				//test values
				if(thickness<1 || thickness>100){
					JOptionPane.showMessageDialog((JFrame)null,
							"thickness value has to be between 1~100 (int)",
							"Inane warning",
							JOptionPane.WARNING_MESSAGE);
				}else if(min_score_thresh<0 || min_score_thresh>1){
					JOptionPane.showMessageDialog((JFrame)null,
							"Min_score_thresh value has to be between 0.00 ~ 1.00",
							"Inane warning",
							JOptionPane.WARNING_MESSAGE);
				}else if(destinPath=="/home/user/not/assigned/path/yet"){
					JOptionPane.showMessageDialog((JFrame)null,
							"Please assign Saving_Path",
							"Inane warning",
							JOptionPane.WARNING_MESSAGE);
				}else if(!currentpath.endsWith("test_images")){
					JOptionPane.showMessageDialog((JFrame)null,
							"Selected image for test has to be in ../object_detection/images/test_images",
							"Inane warning",
							JOptionPane.WARNING_MESSAGE);
				}else if(Integer.parseInt(l1txtfld.getText())<1){
					JOptionPane.showMessageDialog((JFrame)null,
							"Number of classes has to be more than 1!\n"
							+ "It has to be exactly same number with config file\n"
							+ "If you not sure do step3. set config",
							"Inane warning",
							JOptionPane.WARNING_MESSAGE);					
				}else{
					Dia.dispose();
					try {
						rundetect(flag);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
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
		Dia.add(lb, BorderLayout.NORTH);
		Dia.add(bodyp, BorderLayout.CENTER);
		Dia.add(buttonPane, BorderLayout.SOUTH);
		Dia.setVisible(true);
	}
	
	private void rundetect(String flag) throws IOException{
		String result = "";
		String shellscript = "";
		String[] filename = null;
		
		//test video or picture
		if(flag=="video")
			shellscript = "&& bash detectvideo.sh ";
		else
			shellscript = "&& bash detectpic.sh ";
		
		//get checked item names
		filename = MainView.mainViewFrame.leftPane.getCheckeditems();
			
		//if no checked item exist
		if(filename.length == 0){
			//show error message
			JOptionPane.showMessageDialog((JFrame)null,
					"No item selected!",
					"Inane warning",
					JOptionPane.WARNING_MESSAGE);
		}else{
			//make file name array to string of file list 
			String filenamelist = refactoryArray(filename);
								
			//Run Detectpic.sh
			result = Model.sshclient.sendCommand("cd /home/"+Model.username+"/tensorflowGUI/scripts "
					+ shellscript
					+ Model.ActivatedEnv +" "
					+ Model.numofclass + " "
					+ thickness + " "
					+ min_score_thresh + " "
					+ filenamelist);
		
			//Load Image to destin folder
			for(int i = 0 ; i < filename.length ; i++){
				filename[i] = "save_"+ filename[i];
				System.out.println(filename[i]);
			}
			String[] loadfilelist = addPath(filename);
		   	Model.sshclient.getFile(loadfilelist,destinPath);
		   	
		   	//Load Image to middle pane
		   	String filepath = destinPath +"/"+ filename[0];
		   	System.out.println("Load to middle: "+filepath);
		   	MainView.mainViewFrame.middlePane.setImage(filepath);
		}
		
	new detectresultDia(result);
}
	
	//make file name array to file list string with double quotation marks and path
	private String refactoryArray(String[] arry){
		String result = "";
			
		for(int i = 0; i < arry.length ; i++){
			result = result +"\"" +arry[i]+"\" ";
		}

		return result;
	}
	
	private String[] addPath(String[] arry){
		String[] result = new String[arry.length];
		
		for(int i = 0; i < arry.length ; i++){
			result[i] = "/home/"+Model.username+"/tensorflowGUI/"+Model.ActivatedEnv+"/models/research/object_detection/test_images/" +arry[i];
		}

		return result;
	}
}

class detectresultDia{
	private JLabel l1;
	private JEditorPane resultpane;
	private JScrollPane scrlpane;
		
	detectresultDia(String result){
		/*redirect to training folder*/
		LeftView.refreshview();
		
		/*create new dialog*/
	 	JDialog Dia = new JDialog((JFrame)null,"Finished result",true);
	 	Dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 	
	 	/*set size of dialog*/
	 	Dia.setSize(400, 400);
	 	
	 	/*set location*/
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - Dia.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - Dia.getHeight() / 2);
		Dia.setLocation(xpos, ypos);
		 	
		/*set layout*/
		Dia.setLayout(new BorderLayout());
		 	
		/*result pane*/
		l1 = new JLabel("Detection result log");
		l1.setHorizontalAlignment(SwingConstants.CENTER);
					 	
		resultpane = new JEditorPane();
		scrlpane = new JScrollPane(resultpane);
		if(result.contains("out of memory"))
			result = result +  "\n[[warning]]****** There was out of GPU memory!!!! ******";
		resultpane.setText(result);

		/*set button1*/
		JButton b1 = new JButton("OK");
		class b1EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				//close all step1 dialog
				Dia.dispose();
			}}
		b1.addActionListener(new b1EventHandler());
		JPanel pb1 = new JPanel(new FlowLayout());
		pb1.add(b1);
		
		/*add components*/
		JPanel buttonPane = new JPanel();
		buttonPane.add(pb1);
		Dia.add(l1, BorderLayout.NORTH);
		Dia.add(scrlpane, BorderLayout.CENTER);
		Dia.add(buttonPane, BorderLayout.SOUTH);
		Dia.setVisible(true);
	}
}