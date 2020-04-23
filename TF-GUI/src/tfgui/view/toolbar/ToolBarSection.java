package tfgui.view.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import tfgui.controller.putty.runPutty;
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
public class ToolBarSection extends JToolBar{
	private static final long serialVersionUID = 1L;
	private JButton[] toolbarBtn;
	
	public ToolBarSection(JFrame frame, SSHClient sshclient){
		toolbarBtn = new JButton[11];
		toolbarBtn[0] = new JButton(new ImageIcon("src/tfgui/icon/addProject.png"));
		toolbarBtn[1] = new JButton(new ImageIcon("src/tfgui/icon/window.png"));
		toolbarBtn[2] = new JButton(new ImageIcon("src/tfgui/icon/stopproject.png"));
		toolbarBtn[3] = new JButton(new ImageIcon("src/tfgui/icon/check-box.png"));
		toolbarBtn[4] = new JButton(new ImageIcon("src/tfgui/icon/popup_delete.png"));
		toolbarBtn[5] = new JButton(new ImageIcon("src/tfgui/icon/download.png"));
		toolbarBtn[6] = new JButton(new ImageIcon("src/tfgui/icon/eval.png"));
		toolbarBtn[7] = new JButton(new ImageIcon("src/tfgui/icon/loupe_pic.png"));
		toolbarBtn[8] = new JButton(new ImageIcon("src/tfgui/icon/loupe_video.png"));
		toolbarBtn[9] = new JButton(new ImageIcon("src/tfgui/icon/pb.png"));
		toolbarBtn[10] = new JButton(new ImageIcon("src/tfgui/icon/cmd.png"));
		
		toolbarBtn[0].setToolTipText("new Environment");
		toolbarBtn[1].setToolTipText("Open Environment");
		toolbarBtn[2].setToolTipText("Stop Environment");
		toolbarBtn[3].setToolTipText("Check All Files");
		toolbarBtn[4].setToolTipText("Delete Files");
		toolbarBtn[5].setToolTipText("Download Files");
		toolbarBtn[6].setToolTipText("eval model");
		toolbarBtn[7].setToolTipText("detect pictures");
		toolbarBtn[8].setToolTipText("detect videos");
		toolbarBtn[9].setToolTipText("make pb file by converting .ckpt");
		toolbarBtn[10].setToolTipText("Open Putty");
				
		this.setFloatable(false);
		
		//set separators
		for(int i=0; i < 11 ; i++){
			toolbarBtn[i].setBorderPainted(false);
			this.add(toolbarBtn[i]);
			//add separators by adding numbers
			if(i==0||i==2||i==5||i==8){
				this.addSeparator();
			}
			toolbarBtn[i].setFocusPainted(false);
		}
		
		//ToolBar Button 0 action
		class toolbarBtn0EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				System.out.println("btn0 action!");
			}}	
		toolbarBtn[0].addActionListener(new toolbarBtn0EventHandler());
		//ToolBar Button 1 action
		class toolbarBtn1EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				System.out.println("Open Environment!");
				new OpenEnv(sshclient);
			}}	
		toolbarBtn[1].addActionListener(new toolbarBtn1EventHandler());
		//ToolBar Button 2 action
		class toolbarBtn2EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				if(EnvOpened())
					new stopenv();
			}}	
		toolbarBtn[2].addActionListener(new toolbarBtn2EventHandler());
		//ToolBar Button 3 action
		class toolbarBtn3EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				if(EnvOpened())
					MainView.mainViewFrame.leftPane.checkallchkbox();
			}}	
		toolbarBtn[3].addActionListener(new toolbarBtn3EventHandler());
		//ToolBar Button 4 action
		class toolbarBtn4EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				if(EnvOpened())
					MainView.mainViewFrame.leftPane.deletefiles();
			}}	
		toolbarBtn[4].addActionListener(new toolbarBtn4EventHandler());
		//ToolBar Button 5 action
		class toolbarBtn5EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				if(EnvOpened())
					MainView.mainViewFrame.leftPane.downloadfiles();
			}}	
		toolbarBtn[5].addActionListener(new toolbarBtn5EventHandler());
		//ToolBar Button 6 action
		class toolbarBtn6EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				if(EnvOpened())
					new evalModel();
			}}	
		toolbarBtn[6].addActionListener(new toolbarBtn6EventHandler());
		//ToolBar Button 7 action
		class toolbarBtn7EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				if(EnvOpened())
					new Detect("picture");
			}}	
		toolbarBtn[7].addActionListener(new toolbarBtn7EventHandler());
		//ToolBar Button 8 action
		class toolbarBtn8EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				if(EnvOpened())
					new Detect("video");
			}}	
		toolbarBtn[8].addActionListener(new toolbarBtn8EventHandler());
		//ToolBar Button 9 action
		class toolbarBtn9EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				if(EnvOpened())
					new ConvertCKPT();
			}}	
		toolbarBtn[9].addActionListener(new toolbarBtn9EventHandler());
		//ToolBar Button 10 action
		class toolbarBtn10EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				//Open Putty
				new runPutty("ls");
			}}	
		toolbarBtn[10].addActionListener(new toolbarBtn10EventHandler());
	}
	
	private boolean EnvOpened(){
		if(Model.ActivatedEnv == null){
			JOptionPane.showMessageDialog((JFrame)null,
					"Open Environment First(at ToolBar).",
					"Inane warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}else{
			return true;
		}
	}
}
