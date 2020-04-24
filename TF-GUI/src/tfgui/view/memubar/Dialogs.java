package tfgui.view.memubar;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import tfgui.controller.sshclient.SSHClient;
import tfgui.controller.view.AdjustDialogLocation;
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
*		Tensorflow virtual environment and relevant script code.
*/
public class Dialogs {
}

class openDialog{
	public openDialog(JFrame f) throws IOException{
			/*create open dialog*/
		 	FileDialog Dia = new FileDialog(f,"Open File",FileDialog.LOAD);
		 	Dia.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					Dia.dispose();
				}
			});
		 	
		 	/*set initial file*/
		 	Dia.setFile("*.*");
		 	Dia.setVisible(true);
		 	
		 	/*get path(directory and file name)*/
		 	String DirName = Dia.getDirectory();
		 	String FileName = Dia.getFile();
		 	String pathName = DirName + FileName;
		 	System.out.println(pathName);
		}
}

class saveDialog{
	public saveDialog(JFrame f) throws IOException{
			//if save for the first time{
			if(Model.FilePath == null){
				/*create save dialog*/
			 	FileDialog Dia = new FileDialog(f,"Save",FileDialog.SAVE);
			 	Dia.addWindowListener(new WindowAdapter(){
					public void windowClosing(WindowEvent e){
						Dia.dispose();
					}
				});
			 	
			 	/*set initial file*/
			 	Dia.setFile("*.*");
			 	Dia.setVisible(true);
			 	
			 	/*get path(directory and file name)*/
			 	String DirName = Dia.getDirectory();
			 	String FileName = Dia.getFile();
			 	String pathName = DirName + FileName;
			 	Model.FilePath = pathName;
			 	//make new save file on the pathName
			 	
			 	//set static path of this file

			 //if once saved already
			}else{
				//save at Model.FilePath
			}
	}
}

class saveAsDialog{
	public saveAsDialog(JFrame f) throws IOException{
			/*create save dialog*/
		 	FileDialog Dia = new FileDialog(f,"Save As...",FileDialog.SAVE);
		 	Dia.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					Dia.dispose();
				}
			});
		 	
		 	/*set initial file*/
		 	Dia.setFile("*.*");
		 	Dia.setVisible(true);
		 	
		 	/*get path(directory and file name)*/
		 	String DirName = Dia.getDirectory();
		 	String FileName = Dia.getFile();
		 	String pathName = DirName + FileName;
		 	
		 	//make new save file on the pathName
		 	
			//set static path of this file
		 	Model.FilePath = pathName;
	}
}

class exitDialog{
	public exitDialog(JFrame f,SSHClient sshclient){
		/*create new dialog*/
	 	JDialog Dia = new JDialog((JFrame)null,"exit",true);
	 	Dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	 	/*set size of dialog*/
	 	Dia.setSize(300, 100);
	 	
	 	/*set location*/
	 	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - Dia.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - Dia.getHeight() / 2);
		Dia.setLocation(xpos, ypos);
	 	
	 	/*set layout*/
	 	Dia.setLayout(new BorderLayout());
	 	
	 	/*announcement */
	 	JLabel l1 = new JLabel("Will you EXIT this program?");
	 	l1.setHorizontalAlignment(SwingConstants.CENTER);
		/*set button1*/
		JButton b1 = new JButton("OK");
		class b1EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				sshclient.CloseSession();
				System.exit(0);
			}}
		b1.addActionListener(new b1EventHandler());
	 	
		/*set button1*/
		JButton b2 = new JButton("cancel");
		class b2EventHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae){
				Dia.dispose();
			}}
		b2.addActionListener(new b2EventHandler());
		
		/*add components*/
		JPanel buttonPane = new JPanel(new GridLayout(1,2));
		buttonPane.add(b1);
		buttonPane.add(b2);
		Dia.add(l1, BorderLayout.CENTER);
		Dia.add(buttonPane, BorderLayout.SOUTH);
		Dia.setVisible(true);
	}
	
}

class HelpDialog{
	public  HelpDialog(JFrame f){
			/*create new dialog*/
		 	Dialog HelpDia = new Dialog(f,"Help Dialog",true);
		 	HelpDia.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					HelpDia.dispose();
				}
			});
		 	/*set size of dialog*/
		 	HelpDia.setSize(440, 200);
		 	
		 	/*set location*/
		 	new AdjustDialogLocation(f,HelpDia);
		 	
		 	/*set layout*/
		 	HelpDia.setLayout(new BorderLayout());
		 	
			/*add help statements here :) */
		 	String detailstring = 
		 	"Tensorflow GUI for UTAS DI Server!"
		 	+ "\nThis Tensorflow GUI is made in UTAS."
		 	+ "\nYou can train models with this."
		 	+ "\nThis Program can be easily edited in code level"
		 	+ "\nThis program focused on improving Tensorflow Usability"
		 	+ "\nEnjoy your Tensorflow!"
		 	+ "\n"
		 	+ "\ncontact details"
		 	+ "\nHM Yoon heemoony@utas.edu.au"
		 	+ "\n";
		 	
		 	/*set text area*/
		 	JEditorPane txt1 = new JEditorPane();
		 	txt1.setText(detailstring);
		 	txt1.setEditable(false);
		 	JScrollPane t1 = new JScrollPane(txt1);
			t1.getVerticalScrollBar().setValue(0);
			
			/*set button*/
			JButton b1 = new JButton("OK");
			class b1EventHandler implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent ae){
					HelpDia.dispose();
				}}
			b1.addActionListener(new b1EventHandler());
			
			/*add components*/
			HelpDia.add(t1, BorderLayout.CENTER);
			HelpDia.add(b1, BorderLayout.SOUTH);
			HelpDia.setVisible(true);
	 }
	
}

class AboutDialog{
	public AboutDialog(JFrame f){
			/*create new dialog*/
		 	Dialog AboutDia = new Dialog(f,"About",true);
		 	AboutDia.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					AboutDia.dispose();
				}
			});
		 	
			/*set size of dialog*/
		 	AboutDia.setSize(620, 600);
		 	
			/*set location*/
		 	new AdjustDialogLocation(f,AboutDia);
		 	
			/*set layout*/
		 	AboutDia.setLayout(new BorderLayout());
		 	
			/*add About statements here :) */
		 	String detailstring = 
		 	"* Copyright 2019 The Block-AI-VIsion Authors. All Rights Reserved.\r\n" + 
		 	"*\r\n" + 
		 	"* Licensed under the Apache License, Version 2.0 (the \"License\");\r\n" + 
		 	"* you may not use this file except in compliance with the License.\r\n" + 
		 	"* You may obtain a copy of the License at\r\n" + 
		 	"*\r\n" + 
		 	"*     http://www.apache.org/licenses/LICENSE-2.0\r\n" + 
		 	"*\r\n" + 
		 	"* Unless required by applicable law or agreed to in writing, software\r\n" + 
		 	"* distributed under the License is distributed on an \"AS IS\" BASIS,\r\n" + 
		 	"* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\r\n" + 
		 	"* See the License for the specific language governing permissions and\r\n" + 
		 	"* limitations under the License.\r\n" + 
		 	"* ==========================================================================\r\n" + 
		 	"*\r\n" + 
		 	"* This file is part of Tensorflow GUI program under Block-AI-VIsion project.\r\n" + 
		 	"*\r\n" + 
		 	"* Made in University of Tasmania, Australia.\r\n" + 
		 	"*\r\n" + 
		 	"* @Authors : Dr.Mira Park (mira.park@utas.edu.au)\r\n" + 
		 	"*	     Dr.Sanghee Lee (knusang1799@gmail.com)\r\n" + 
		 	"*	     Heemoon Yoon (boguss1225@gmail.com)\r\n" + 
		 	"*\r\n" + 
		 	"* Date : Initial Development in 2019\r\n" + 
		 	"*\r\n" + 
		 	"* For the latest version, please check the github \r\n" + 
		 	"* (https://github.com/boguss1225/ObjectDetectionGUI)\r\n" + 
		 	"* \r\n" + 
		 	"* ==========================================================================\r\n" + 
		 	"* Description : This program allows users to train models, configure settings,\r\n" + 
		 	"*		detect objects and control image data within GUI level. \r\n" + 
		 	"*		This program converted almost every steps of Tensorflow model \r\n" + 
		 	"*		training into GUI so that user can easily utilize Tensorflow. \r\n" + 
		 	"*		To operate this program, server need to have preinstalled \r\n" + 
		 	"*		Tensorflow virtual environment and relevant script code.";
		 	
		 	/*set text area*/
		 	JEditorPane txt1 = new JEditorPane();
		 	txt1.setEditable(false);
		 	txt1.setText(detailstring);
			JScrollPane t1 = new JScrollPane(txt1);
			t1.getVerticalScrollBar().setValue(0);
			
			/*set button*/
			JButton b1 = new JButton("OK");
			class b1EventHandler implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent ae){
					AboutDia.dispose();
				}}
			b1.addActionListener(new b1EventHandler());
			
			/*add components*/
			AboutDia.add(t1, BorderLayout.CENTER);
			AboutDia.add(b1, BorderLayout.SOUTH);
			AboutDia.setVisible(true);
	 }
}
