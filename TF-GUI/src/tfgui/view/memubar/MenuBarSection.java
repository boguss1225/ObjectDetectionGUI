package tfgui.view.memubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import tfgui.controller.sshclient.SSHClient;

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
public class MenuBarSection extends JMenuBar{
	private static final long serialVersionUID = 1L;

	public MenuBarSection(JFrame f, SSHClient sshclient){
		/*Menu_bar*/
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMenu setting = new JMenu("Setting");
		JMenu help = new JMenu("Help");
		/*File Menu*/
		JMenuItem newfile = new JMenuItem("New");
		newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		JMenuItem open = new JMenuItem("Open");
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		JMenuItem saveAs = new JMenuItem("Save As..");
		JMenuItem exit = new JMenuItem("Close");
		
		JMenuItem edit_1 = new JMenuItem("edit_1");
		JMenuItem edit_2 = new JMenuItem("edit_2");
		JMenuItem edit_3 = new JMenuItem("edit_3");
		
		JMenuItem settingt = new JMenuItem("Setting");
		JMenuItem helpwords = new JMenuItem("Help");
		JMenuItem about = new JMenuItem("About");
		JMenuItem Testing = new JMenuItem("Testing");
		file.add(newfile);
		file.add(open);
		file.add(save);
		file.add(saveAs);
		file.addSeparator();
		file.add(exit);
		edit.add(edit_1);
		edit.add(edit_2);
		edit.add(edit_3);
		setting.add(settingt);
		help.add(helpwords);
		help.add(about);
		file.addSeparator();
		help.add(Testing);
		/*<Menu_SetUp>*/
		this.add(file);
		this.add(edit);
		this.add(setting);
		this.add(help);
		this.add(about);
		
		/*<MenuItem_Action>*/
		/*new
		class newfileEventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				try {
					new newfileDialog(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}}	
		newfile.addActionListener(new newfileEventHandler());
		
		/*open*/
		class openEventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				try {
					new openDialog(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}}	
		open.addActionListener(new openEventHandler());
		
		/*save*/
		class saveEventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				try {
					new saveDialog(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}}	
		save.addActionListener(new saveEventHandler());
		
		/*saveAs*/
		class saveAsEventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				try {
					new saveAsDialog(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}}	
		saveAs.addActionListener(new saveAsEventHandler());
		
		/*exit*/
		class exitEventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				new exitDialog(f, sshclient);
			}}	
		exit.addActionListener(new exitEventHandler());
		
		/*theme menu item*/
		class settingtEventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				//new ThemeDialog(f);
			}}
		settingt.addActionListener(new settingtEventHandler());
		
		/*help*/
		class helpEventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				new HelpDialog(f);
			}}
		helpwords.addActionListener(new helpEventHandler());
		
		/*about*/
		class aboutEventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				new AboutDialog(f);
			}}	
		about.addActionListener(new aboutEventHandler());
		
		/*testing*/
		class TestingEventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				/*something want to test*/

			}}	
		Testing.addActionListener(new TestingEventHandler());
		setVisible(true);
		
	}
}