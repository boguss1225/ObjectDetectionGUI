package tfgui.view.right;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
public class RightUnderView extends JPanel {
	private static final long serialVersionUID = 1L;
	private static String cmdReturnstxt;
	private JTextField cmdtxtField;
	private static JEditorPane cmdReturnstxtPane;
	
	public RightUnderView(SSHClient sshclient){
		this.setLayout(new BorderLayout());
		
		JPanel NorthrightUnderPane = new JPanel(new BorderLayout());
		 	JLabel cmdLabel = new JLabel("CMD");
		 	cmdtxtField = new JTextField("ls");
		 	NorthrightUnderPane.add(cmdLabel, BorderLayout.WEST);
		 	NorthrightUnderPane.add(cmdtxtField, BorderLayout.CENTER);
	 	
	 	JPanel CenterrightUnderPane = new JPanel(new BorderLayout());
		
		cmdReturnstxtPane = new JEditorPane();
			cmdReturnstxt = 
					    "***************************************************"
					+ "\n                          Login success"
					+ "\n***************************************************"
					+ "\n      You can use basic Linux command here"
					+ "\n      example) mkdir, rm, rmdir, ls, chmod..."
					+ "\n***************************************************";
			cmdReturnstxtPane.setText(cmdReturnstxt);
			cmdReturnstxtPane.setForeground(Color.YELLOW);
			cmdReturnstxtPane.setBackground(Color.BLACK);
			cmdReturnstxtPane.setSelectedTextColor(Color.GREEN);
			cmdReturnstxtPane.setEditable(false);
		JScrollPane cmdReturns = new JScrollPane(cmdReturnstxtPane);
			cmdReturns.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
			cmdReturns.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
			CenterrightUnderPane.add(cmdReturns, BorderLayout.CENTER);
		class cmdtxtFieldEventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				String cmd = cmdtxtField.getText();
				String output = sshclient.sendCommand(cmd);
				cmdReturnstxt = cmdReturnstxt+"\n[CMD:"+sshclient.pwd+"] "+cmd+"\n"+output;
				//display result on the view
				cmdReturnstxtPane.setText(cmdReturnstxt);
				cmdtxtField.setText("");
			}}
	 		cmdtxtField.addActionListener(new cmdtxtFieldEventHandler());
	 	
	 	/*for initialize sshclient channel*/
	 	sshclient.sendCommand("ls");
	 	
		this.add(NorthrightUnderPane, BorderLayout.NORTH);
		this.add(CenterrightUnderPane, BorderLayout.CENTER);
	}
	
	public void setCMDtxtField(String str){
		cmdReturnstxt = str;
	}
	
	public static void updateCMDtxtField(String str) {
		cmdReturnstxt = cmdReturnstxt + "\n" + str;
		cmdReturnstxtPane.setText(cmdReturnstxt);
	}
}
