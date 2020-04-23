package tfgui.view.right;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import tfgui.controller.sshclient.SSHClient;
import tfgui.model.Model;
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
public class RightUpperView extends JPanel{
	private static final long serialVersionUID = 1L;
	static JLabel descriptionLabel;
	static int finishedstep = 0;
	static boolean step1finished = false;
	private JButton step1btn, step2btn, step3btn;
	static JButton trainingbtn;
	private JPanel btnsPanel;
	
	public RightUpperView(SSHClient sshclient){
		this.setLayout(new BorderLayout());
		
		//North
		descriptionLabel = new JLabel();
		
		/*CENTER*/
		btnsPanel = new JPanel(new GridLayout(4,1,3,3));
		
		step1btn = new JButton("step1. set Classes");
			class step1btnEventHandler implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent ae){
					if(Model.ActivatedEnv == null){
						JOptionPane.showMessageDialog((JFrame)null,
							"Open Environment First(at ToolBar).",
							"Inane warning",
							JOptionPane.WARNING_MESSAGE);
					}else {
						//Open step 1 dialog
						new Step1Dialog();
					}
			}}
			step1btn.addActionListener(new step1btnEventHandler());
			
		step2btn = new JButton("step2. Generate Training Data");
			class step2btnEventHandler implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent ae){
					if(Model.ActivatedEnv == null){
						JOptionPane.showMessageDialog((JFrame)null,
							"Open Environment First (at ToolBar).",
							"Inane warning",
							JOptionPane.WARNING_MESSAGE);
					}else { 
						if(finishedstep<1){
							JOptionPane.showMessageDialog((JFrame)null,
								"[Warning] You didn't excute privious step!"
								+"\nThis can cause abortion during training",
								"Inane warning",
								JOptionPane.WARNING_MESSAGE);
						}
						//Open step 2 dialog
						new Step2Dialog();
					}
			}}
			step2btn.addActionListener(new step2btnEventHandler());
			
		step3btn = new JButton("step3. set config");
			class step3btnEventHandler implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent ae){
					if(Model.ActivatedEnv == null){
						JOptionPane.showMessageDialog((JFrame)null,
							    "Open Environment First (at ToolBar).",
							    "Inane warning",
							    JOptionPane.WARNING_MESSAGE);
					}else {
						if(finishedstep<2){
							JOptionPane.showMessageDialog((JFrame)null,
								"[Warning] You didn't excute privious step!"
								+"\nThis can cause abortion during training",
							    "Inane warning",
							    JOptionPane.WARNING_MESSAGE);
						}
						//Open step 3 dialog
						new Step3Dialog();
					}
			}}
			step3btn.addActionListener(new step3btnEventHandler());

		trainingbtn = new JButton("START TRAINING");
			class trainingbtnEventHandler implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent ae){
					
					if(Model.ActivatedEnv == null){
						JOptionPane.showMessageDialog((JFrame)null,
							    "Open Environment First (at ToolBar).",
							    "Inane warning",
							    JOptionPane.WARNING_MESSAGE);
					}else {
						if(finishedstep<3){
							JOptionPane.showMessageDialog((JFrame)null,
								"[Warning] You didn't excute privious step!"
								+"\nThis can cause abortion during training",
								"Inane warning",
								JOptionPane.WARNING_MESSAGE);
						}
						//Open step 4 dialog
						new Step4Dialog();
					}
			}}
			trainingbtn.addActionListener(new trainingbtnEventHandler());
			trainingbtn.setBackground(Color.GRAY);
			
		initiallizeRightUpperView();
			
		/*add components*/
		btnsPanel.add(step1btn);
		btnsPanel.add(step2btn);
		btnsPanel.add(step3btn);
		btnsPanel.add(trainingbtn);
		this.add(descriptionLabel, BorderLayout.NORTH);
		this.add(btnsPanel, BorderLayout.CENTER);
	}

	public static void initiallizeRightUpperView(){
		descriptionLabel.setText("none of the steps accomplished");
		descriptionLabel.setForeground(Color.PINK);
		finishedstep = 0;
		step1finished = false;
		trainingbtn.setForeground(null);
		trainingbtn.setBackground(null);
	}
}
