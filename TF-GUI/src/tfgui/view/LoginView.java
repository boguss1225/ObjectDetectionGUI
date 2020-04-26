package tfgui.view;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import tfgui.controller.sshclient.SSHClient;
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
*		Tensorflow virtual environment and relevant script code.s
*/

public class LoginView extends JFrame {
	private static final long serialVersionUID = 1L;
	private Image frameIcon;
	private ImageIcon MainIcon;
	private String ConnectionIP;
	private String username;
	private String password;
	public static LoginView loginframe;
	public LoginView(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*set title*/
		this.setTitle("Tensorflow-GUI Login");
		
		/*set size*/
		setSize(500,250);
		
		/*set location*/
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - getHeight() / 2);
		this.setLocation(xpos, ypos);
		
		/*set icon of this frame*/
		frameIcon = Toolkit.getDefaultToolkit().getImage("src/tfgui/icon/UTASlogow64.png");
	 	this.setIconImage(frameIcon);
	 	
	 	/*set Layout*/
	 	JPanel mainPanel = new JPanel(new BorderLayout());
	 	this.setLayout(new BorderLayout());
	 	mainPanel.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));

	 	/*set contents*/
	 	MainIcon = new ImageIcon("src/tfgui/icon/UTASlogo128.png");
	 	JLabel utaslogo = new JLabel(MainIcon);
	 	
	 	JLabel welcomeLabel = new JLabel("Tensorflow for UTAS DI Server 2019");
	 	welcomeLabel.setFont(new Font("courier ITALIC", Font.BOLD, 10));
	 	welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
	 	
	 	//create components
	 	JPanel loginpanel = new JPanel(new GridLayout(4,1));
	 		// IP address
	 		JPanel ConnectionIPpanel = new JPanel(new FlowLayout());
	 		JLabel ConnectionIPLabel = new JLabel("IP address");
	 		JTextField ConnectionIPTF = new JTextField();
	 		ConnectionIPTF.setColumns(15);
	 		ConnectionIPTF.setText("ictdls.cis.utas.edu.au");
	 		//user ID
	 		JPanel userpanel = new JPanel(new FlowLayout());
	 		JLabel usernameLabel = new JLabel("username");
	 		JTextField usernameTF = new JTextField();
	 		usernameTF.setColumns(15);
	 		//passwords
	 		JPanel passwordpanel = new JPanel(new FlowLayout());
		 	JPanel buttonpanel = new JPanel(new FlowLayout());
		 	JLabel passwordLabel = new JLabel("password");
		 	JPasswordField passwordTF = new JPasswordField();
		 	class passwordTFEventHandler implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent ae){
					ConnectionIP = ConnectionIPTF.getText();
					username = usernameTF.getText();
					password = new String(passwordTF.getPassword());
					Model.sshclient = new SSHClient(ConnectionIP, username, password);
			}}
		 	passwordTF.addActionListener(new passwordTFEventHandler());
		 	passwordTF.setColumns(15);
		 	//login button
		 	JButton loginButton = new JButton("Login");
		 	loginButton.addActionListener(new passwordTFEventHandler());
		 	buttonpanel.add(loginButton);
		 	
	 	//add components (body part)
	 	ConnectionIPpanel.add(ConnectionIPLabel);
	 	ConnectionIPpanel.add(ConnectionIPTF);
	 	userpanel.add(usernameLabel);
	 	userpanel.add(usernameTF);
	 	passwordpanel.add(passwordLabel);
	 	passwordpanel.add(passwordTF);

	 	loginpanel.add(ConnectionIPpanel);
	 	loginpanel.add(userpanel);
	 	loginpanel.add(passwordpanel);
	 	loginpanel.add(buttonpanel);
	 	
	 	/*add components*/
	 	mainPanel.add(utaslogo, BorderLayout.NORTH);
	 	mainPanel.add(loginpanel, BorderLayout.CENTER);
	 	mainPanel.add(welcomeLabel, BorderLayout.SOUTH);
	 	this.add(mainPanel);
	 	this.pack();
	 	loginframe = this;
	 	setVisible(true);
	 	
	 	//delete this after test
	    //Model.sshclient = new SSHClient("10.200.196.26", "guest2", "guest002");
	    Model.sshclient = new SSHClient("ictdls.cis.utas.edu.au", "mirap", "Yolo777@");

	}
}
