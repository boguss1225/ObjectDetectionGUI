package tfgui.view.right;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import tfgui.controller.putty.runprocessDia;
import tfgui.controller.sshclient.SSHClient;
import tfgui.model.Model;
import tfgui.view.MainView;
import tfgui.view.left.LeftView;
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
public class Step2Dialog{
	private SSHClient sshclient;
	private radiopanel radioPanel;
	
	protected Step2Dialog(){
		sshclient = Model.sshclient;
		/*redirect to training folder*/
		MainView.mainViewFrame.leftPane.showFolders("/home/"+Model.username+"/tensorflowGUI/"+Model.ActivatedEnv+"/models/research/object_detection/images");
		
		/*create new dialog*/
	 	JDialog Dia = new JDialog((JFrame)null,"Step2. Generate Training Data",true);
	 	Dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 	
	 	/*set size of dialog*/
	 	Dia.setSize(500, 400);
	 	
	 	/*set location*/
	 	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - Dia.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - Dia.getHeight() / 2);
		Dia.setLocation(xpos, ypos);
	 	
	 	/*set layout*/
	 	Dia.setLayout(new BorderLayout());
	 	
	 	/*announcement */
	 	JLabel l1 = new JLabel("Generate Training Data");
	 	l1.setHorizontalAlignment(SwingConstants.LEFT);
	 	
	 	/*Set Contents Pane*/
	 	radioPanel = new radiopanel();
	 	
		/*set button1*/
		JButton b1 = new JButton("OK");
		
		class b1EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				RightUpperView.finishedstep=2;
				
				//set RightUpperView components
				RightUpperView.descriptionLabel.setText("Complished - Step2");
				RightUpperView.descriptionLabel.setForeground(Color.BLUE);
				
				LeftView.refreshview();
				
				//update log on RightUnderView
				RightUnderView.updateCMDtxtField("*** Step2 'Generate Training Data' executed ***");
				
				//new step2resultDia(output);
				Dia.dispose();
				
				// display process view
				runprocessDia rundia;
				if(radioPanel.allButton.isSelected()){
					rundia = new runprocessDia("cd /home/"+Model.username+"/tensorflowGUI/scripts && bash GnrtTrainingDataAll.sh "+Model.ActivatedEnv);
				}else if(radioPanel.recButton.isSelected()){
					rundia = new runprocessDia("cd /home/"+Model.username+"/tensorflowGUI/scripts && bash GnrtTrainingDataREC.sh "+Model.ActivatedEnv);
				}
			}}
		b1.addActionListener(new b1EventHandler());
		JPanel pb1 = new JPanel(new FlowLayout());
		pb1.add(b1);
		
		/*set button1*/
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
		Dia.add(l1, BorderLayout.NORTH);
		Dia.add(radioPanel, BorderLayout.CENTER);
		Dia.add(buttonPane, BorderLayout.SOUTH);
		Dia.setVisible(true);
		
	}

}

class radiopanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JLabel picture;
	protected JRadioButton allButton, recButton, noneButton;
	
	radiopanel(){
		JPanel radiop = new JPanel();
		allButton = new JRadioButton("All (CSV + record)");
		allButton.setActionCommand("all");
		allButton.setSelected(true);
		recButton = new JRadioButton("Only Record (CSV ready)");
	 	recButton.setActionCommand("recordonly");
	 	noneButton = new JRadioButton("None (All ready)");
	 	noneButton.setActionCommand("nogene");
	 	
	 	ButtonGroup group = new ButtonGroup();
	 	group.add(allButton);
	 	group.add(recButton);
	 	group.add(noneButton);
	 	allButton.addActionListener(this);
	 	recButton.addActionListener(this);
	 	noneButton.addActionListener(this);
	 	radiop.setLayout(new GridLayout(1,0));
	 	radiop.add(allButton);
	 	radiop.add(recButton);
	 	radiop.add(noneButton);
	 	
	 	picture = new JLabel(new ImageIcon("src/tfgui/icon/all.png"));
		
		this.setLayout(new BorderLayout());
		this.add(radiop, BorderLayout.NORTH);
		this.add(picture, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		picture.setIcon(new ImageIcon("src/tfgui/icon/"
                + e.getActionCommand()
                + ".png"));
	}
}

class step2resultDia{
	private JLabel l1;
	private JEditorPane resultpane;
	private JScrollPane scrlpane;
	
	step2resultDia(String result){
		/*redirect to training folder*/
		MainView.mainViewFrame.leftPane.showFolders("/home/"+Model.username+"/tensorflowGUI/"+Model.ActivatedEnv+"/models/research/object_detection/images");
		
		/*create new dialog*/
	 	JDialog Dia = new JDialog((JFrame)null,"Step2 Finished result",true);
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
		l1 = new JLabel("Succssfully Generated");
		l1.setHorizontalAlignment(SwingConstants.CENTER);
				 	
	 	resultpane = new JEditorPane();
	 	scrlpane = new JScrollPane(resultpane);
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