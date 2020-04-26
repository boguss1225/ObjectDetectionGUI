package tfgui.view.right;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import tfgui.controller.view.JNumberTextField;
import tfgui.model.Model;
import tfgui.view.MainView;
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
public class Step1Dialog{
	private int numofClass =1;
	private JTextField[] classListTxtFld;
	private JPanel classpane;
	
	protected Step1Dialog(){
		
		/*redirect to training folder*/
		MainView.mainViewFrame.leftPane.showFolders("/home/"+Model.username+"/tensorflowGUI/"+Model.ActivatedEnv+"/models/research/object_detection/images");
		
		/*create new dialog*/
	 	JDialog Dia = new JDialog((JFrame)null,"Step1. Set Class",true);
	 	Dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 	
	 	/*set size of dialog*/
	 	Dia.setSize(300, 300);
	 	
	 	/*set location*/
	 	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - Dia.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - Dia.getHeight() / 2);
		Dia.setLocation(xpos, ypos);
	 	
	 	/*set layout*/
	 	Dia.setLayout(new BorderLayout());
	 	
	 	numofClass = Model.numofclass;
	 	
	 	/*announcement */
	 	JPanel northp = new JPanel(new FlowLayout());
	 	JLabel l1 = new JLabel("Number of Classes : ");
	 		l1.setHorizontalAlignment(SwingConstants.LEFT);
	 	JNumberTextField numofClassTF = new JNumberTextField();
	 		numofClassTF.setText(Integer.toString(Model.numofclass));
	 		numofClassTF.setForeground(Color.BLACK);
			numofClassTF.setBackground(Color.white);
			
		 	class numofClassTFEventHandler implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent ae){
					numofClass = Integer.parseInt(numofClassTF.getText());
					numofClassTF.setEditable(false);
					numofClassTF.setBackground(Color.gray);
					classpane = updateClassListView(classpane);
			}}
		 	numofClassTF.addActionListener(new numofClassTFEventHandler());
		 	numofClassTF.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent event){
	            	if (event.getClickCount() == 2) {
	            		numofClassTF.setEditable(true);
						numofClassTF.setBackground(Color.white);
	            	}
	            }
	        });
		 	numofClassTF.setColumns(4);
	 	JButton numofClassbtn = new JButton("Set");
		 	class numofClassbtnEventHandler implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent ae){
					numofClass = Integer.parseInt(numofClassTF.getText());
					numofClassTF.setEditable(false);
					classpane = updateClassListView(classpane);
					numofClassTF.setBackground(Color.gray);
			}}
		 	numofClassbtn.addActionListener(new numofClassbtnEventHandler());
	 	northp.add(l1);
	 	northp.add(numofClassTF);
	 	northp.add(numofClassbtn);
	 	
	 	/*set Class list Pane*/
	 	JScrollPane scrollpane = new JScrollPane();
	 	classpane = new JPanel();
	 	classpane = updateClassListView(classpane);
	 	scrollpane.setViewportView(classpane);
	 	scrollpane.setVisible(true);
	 	scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	 	
	 	/*set button1*/
		JButton b1 = new JButton("OK");
		class b1EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				if(numofClass <1){
					JOptionPane.showMessageDialog((JFrame)null,
						    "class number has to be more than 0.",
						    "Inane warning",
						    JOptionPane.WARNING_MESSAGE);
				}else{
					//checking dialog
					setClassOKDialog(Dia);
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
		Dia.add(northp, BorderLayout.NORTH);
		Dia.add(scrollpane, BorderLayout.CENTER);
		Dia.add(buttonPane, BorderLayout.SOUTH);
		Dia.setVisible(true);
	}
	
	private JPanel updateClassListView(JPanel p){
		p.removeAll();
		p.setLayout(new GridLayout(0,1,2,2));
		classListTxtFld = new JTextField[numofClass];
		try{
			for(int i = 0; i < numofClass ; i++)
			{	
				classListTxtFld[i] = new JTextField();
				JPanel temp = new JPanel(new BorderLayout());
				JLabel lb = new JLabel("class "+Integer.toString(i+1));
				classListTxtFld[i].setBackground(Color.WHITE); 
				classListTxtFld[i].setForeground(Color.BLACK);
				if(Model.classlist.length>i)
					classListTxtFld[i].setText(Model.classlist[i]);
				temp.add(lb ,BorderLayout.WEST);
				temp.add(classListTxtFld[i],BorderLayout.CENTER);
				p.add(temp);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}
		
		//add components
		p.add(new JLabel("Double click class number to add more"));
		 //add gap space for grid layout
		for(int i = 0; i< 5 ; i++)
			p.add(new JLabel());
		p.revalidate();
		p.setVisible(true);
		return p;
	}
	
	private void setClassOKDialog(JDialog parent){
		/*create new dialog*/
	 	JDialog Dia = new JDialog((JFrame)null,"Set Class Check",true);
	 	Dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 	
	 	/*set size of dialog*/
	 	Dia.setSize(400, 150);
	 	
	 	/*set location*/
	 	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - Dia.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - Dia.getHeight() / 2);
		Dia.setLocation(xpos, ypos);
	 	
	 	/*set layout*/
	 	Dia.setLayout(new BorderLayout());
	 	
	 	/*announcement */
	 	JLabel l1 = new JLabel("<html><body>Class names have to be 'exactly same' with that of data set<br>"
	 			+ "are you sure those class names are correct?</body></html>");
	 	l1.setHorizontalAlignment(SwingConstants.CENTER);
	 	
		/*set button1*/
		JButton b1 = new JButton("Yes");
		class b1EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				//get names from classListTxtFld[]
				String parameters = "";
				for(int i = 0; i < numofClass ; i++)
					parameters = parameters + " \""+ classListTxtFld[i].getText()+"\"";
				
				//set Model class data
				Model.numofclass = numofClass;
				Model.classlist = new String[numofClass];
				for(int i = 0; i < numofClass ; i++)
					Model.classlist[i] = classListTxtFld[i].getText();
								
				//execute step1 command
				Model.sshclient.sendCommand(
						"cd /home/"+Model.username+"/tensorflowGUI/scripts "
								+ "&& bash setclass.sh "
								+ Model.ActivatedEnv
								+ parameters);
				System.out.println("Step1 'Setting Class' executed");
				
				//change color and set finished step flag
				RightUpperView.descriptionLabel.setText("Complished - Step1");
				RightUpperView.descriptionLabel.setForeground(Color.ORANGE);
				RightUpperView.finishedstep=1;
				RightUpperView.step1finished = true;
				
				//update log on RightUnderView
				RightUnderView.updateCMDtxtField("***** Step1 'Setting Class' executed *****");
				
				//close all step1 dialog
				Dia.dispose();
				parent.dispose();
				
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
		Dia.add(l1, BorderLayout.CENTER);
		Dia.add(buttonPane, BorderLayout.SOUTH);
		Dia.setVisible(true);
	}
}