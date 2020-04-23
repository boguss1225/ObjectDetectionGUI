package tfgui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import tfgui.controller.sshclient.SSHClient;
import tfgui.view.left.LeftView;
import tfgui.view.memubar.MenuBarSection;
import tfgui.view.middle.MiddleView;
import tfgui.view.right.RightUnderView;
import tfgui.view.right.RightUpperView;
import tfgui.view.toolbar.ToolBarSection;
import tfgui.view.under.UnderView;
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
public class MainView extends JFrame {
	private static final long serialVersionUID = 1L;
	public static MainView mainViewFrame;
	public LeftView leftPane;
	public MiddleView middlePane;
 	public RightUpperView rightUpperPane;
 	public RightUnderView rightUnderPane;
 	public UnderView underpane;
	
	public MainView(SSHClient sshclient){
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		    	new exitDialog(sshclient); // Will not return if user clicks yes.
		        super.windowClosing(e);
		    }
		});
		/*set title*/
		this.setTitle("Tensorflow-GUI Login");
		
		/*set size*/
		setSize(1100,700);
		this.setMinimumSize(new Dimension(400,300));
		
		/*set location*/
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - getHeight() / 2);
		this.setLocation(xpos, ypos);
		
		/*set icon of this frame*/
		Image frameIcon = Toolkit.getDefaultToolkit().getImage("src/tfgui/icon/UTASlogow64.png");
	 	this.setIconImage(frameIcon);
	 	
		/*set Menubar*/
		MenuBarSection MenuBar = new MenuBarSection(this, sshclient);
	 	this.setJMenuBar(MenuBar);
	 	/*set ToolBar*/
	 	ToolBarSection toolbar = new ToolBarSection(this, sshclient);
	 	
	 	/*set Layout*/
	 	this.setLayout(new BorderLayout());
	 	JPanel mainPanel = new JPanel(new BorderLayout(10,10));
	 	mainPanel.setBorder(BorderFactory.createEmptyBorder(5 , 5 , 5 , 5));
	 	
	 	leftPane = new LeftView();
	 	middlePane = new MiddleView();
	 	rightUpperPane = new RightUpperView(sshclient);
	 	rightUnderPane = new RightUnderView(sshclient);
	 	underpane = new UnderView();
	 	
	 	JSplitPane rightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
	 			rightUpperPane, rightUnderPane);
	 	JSplitPane leftMiddlePane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                leftPane, middlePane);
	 	JSplitPane middleRightPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
	 			leftMiddlePane, rightPane);
		 	rightPane.setOneTouchExpandable(true);
		 	rightPane.setDividerLocation(200);
		 	leftMiddlePane.setOneTouchExpandable(true);
		 	leftMiddlePane.setDividerLocation(200);
		 	middleRightPane.setOneTouchExpandable(true);
		 	middleRightPane.setDividerLocation(this.getWidth()-330);
		 	
	 	mainPanel.add(middleRightPane, BorderLayout.CENTER);
	 	this.add(toolbar, BorderLayout.NORTH);
	 	this.add(mainPanel, BorderLayout.CENTER);
	 	this.add(underpane, BorderLayout.SOUTH);
	 	this.setVisible(true);
	 	
	 	mainViewFrame = this;
	}
}
