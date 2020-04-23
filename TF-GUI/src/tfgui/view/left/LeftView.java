package tfgui.view.left;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import tfgui.controller.sshclient.SSHClient;
import tfgui.controller.view.QuickSort;
import tfgui.model.Model;
import tfgui.view.MainView;
import tfgui.view.right.RightUnderView;
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
public class LeftView extends JPanel {
	private static final long serialVersionUID = 1L;
	private SSHClient sshclient;
	private int numofFiles = 0;
	private int numofFolders = 0;
	private JPanel[] folderBtns;
	private JPanel[] fileBtns;
	private String[] foldername;
	private String[] filename;
	private JCheckBox[] chkbox;
	private static String currentPath;
	

	public LeftView(){
		this.setLayout(new BorderLayout());
		this.setSize(300, 10);
	}
	
	public void showFolders(String path){
		
		//initialize layout
		this.removeAll();
		this.setLayout(new BorderLayout());
		JPanel contentspane = new JPanel(new GridLayout(0,1));
		JScrollPane scrollpane = new JScrollPane(contentspane); 
		scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//set current path
		currentPath = path;
		
		/*get List of Files*/
		sshclient = Model.sshclient;
		String folderlist = sshclient.sendCommand("cd "+ path +" && find . -maxdepth 1 -type d");
		String filelist = sshclient.sendCommand("cd "+ path +" && find . -maxdepth 1 -not -type d");
		
		//arrange & sort names in order		
		QuickSort sorter = new QuickSort();
		foldername = sorter.sort(folderlist.split("\n"));
		filename = sorter.sort(filelist.split("\n"));
		
		numofFolders = foldername.length;
	 	numofFiles = filename.length;
	 	
	 	/*Create Array of File list*/
	 	folderBtns = new JPanel[numofFolders];
	 	fileBtns = new JPanel[numofFiles];
	 	chkbox = new JCheckBox[numofFiles];
	 	
	 	/*Set Click GUI*/
	 	Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
	    Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	    
	    /*Path Label Create*/
	    MainView.mainViewFrame.underpane.UpdatePath(path);
	    
	    /*up Folder Icon Create*/
	    JPanel upfolderBtn = new JPanel(new BorderLayout());
	    upfolderBtn.setBorder(raisedetched);
		JLabel upiconl = new JLabel(new ImageIcon("src/tfgui/icon/upfolder.png"));
		JLabel uplabel = new JLabel("/..");
		upfolderBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
            	upfolderBtn.setBorder(loweredetched);
            }
            @Override
            public void mouseReleased(final MouseEvent e) {
            	upfolderBtn.setBorder(raisedetched);
            }
            public void mouseClicked(MouseEvent event){
            	if (event.getClickCount() == 2) {
            		String pass = path.substring(0,path.lastIndexOf("/"));
            		if(pass.length() > 1){
            			currentPath= pass;
            			refreshview();
            			RightUnderView.updateCMDtxtField(currentPath);
            		}
            	}
            }
        });
		upfolderBtn.setSize(300, 50);
		upfolderBtn.add(upiconl,BorderLayout.WEST);
		upfolderBtn.add(uplabel,BorderLayout.CENTER);
		contentspane.add(upfolderBtn);
		
		/*Plus Files Icon Create*/
		JPanel plusBtn = new JPanel(new BorderLayout());
		plusBtn.setBorder(raisedetched);
		JLabel plusiconl = new JLabel(new ImageIcon("src/tfgui/icon/plusfile.png"));
		JLabel pluslabel = new JLabel("Add Files");
		plusBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
            	plusBtn.setBorder(loweredetched);
            }
            @Override
            public void mouseReleased(final MouseEvent e) {
            	plusBtn.setBorder(raisedetched);
            }
            public void mouseClicked(MouseEvent event){
            	if (event.getClickCount() == 2) {
            		try {
						new putFilesDialog(path);
						refreshview();
					} catch (IOException e) {
						e.printStackTrace();
					}
            	}
            }
        });
		plusBtn.setSize(300, 50);
		plusBtn.add(plusiconl,BorderLayout.WEST);
		plusBtn.add(pluslabel,BorderLayout.CENTER);
		contentspane.add(plusBtn);
		
	 	/*Folder Icon Create*/
		if(numofFolders>1){
	 	for(int i = 1 ; i < numofFolders; i++){
	 		if(foldername[i].substring(2).startsWith(".")){
	 			//systemfolder
	 			//System.out.println("system folder (no show): " + foldername[i].substring(2));
	 		}else{
	 			folderBtns[i] = new JPanel(new BorderLayout());
	 			folderBtns[i].setBorder(raisedetched);
	 			JLabel iconl = new JLabel(new ImageIcon("src/tfgui/icon/bluefolder.png"));
	 			JLabel label = new JLabel(foldername[i].substring(2));
		 		int i2 = i;
	 			folderBtns[i].addMouseListener(new MouseAdapter() {

	 	            @Override
	 	            public void mousePressed(final MouseEvent e) {
	 	            	folderBtns[i2].setBorder(loweredetched);
	 	            }

	 	            @Override
	 	            public void mouseReleased(final MouseEvent e) {
	 	            	folderBtns[i2].setBorder(raisedetched);
	 	            }
	 	            
	 	            public void mouseClicked(MouseEvent event){
	 	            	if (event.getClickCount() == 2) {
	 	            		currentPath = path+foldername[i2].substring(1);
	 	            		refreshview();
	 	            		RightUnderView.updateCMDtxtField(currentPath);
	 	            	}
	 	            }
	 	        });
				folderBtns[i].setSize(300, 50);
				folderBtns[i].add(iconl,BorderLayout.WEST);
				folderBtns[i].add(label,BorderLayout.CENTER);
				contentspane.add(folderBtns[i]);
	 		}
	 	}}
	 	
	 	/*File Icon Create*/
	 	if(filename[0].length()>2){//not empty file folder
	 	for(int i = 0 ; i < numofFiles; i++){
	 		if(filename[i].substring(2).startsWith(".")){
	 			//System.out.println("system file (no show): " + filename[i].substring(2));
	 		}else{
	 			fileBtns[i] = new JPanel(new BorderLayout());
	 			fileBtns[i].setBorder(raisedetched);
	 			JLabel iconl = new JLabel(new ImageIcon("src/tfgui/icon/bluefile.png"));
	 			JLabel label = new JLabel(filename[i].substring(2));
	 			chkbox[i] = new JCheckBox(); 
	 			int i2 = i;
	 			fileBtns[i].addMouseListener(new MouseAdapter() {

	 	            @Override
	 	            public void mousePressed(final MouseEvent e) {
	 	            	fileBtns[i2].setBorder(loweredetched);
	 	            	
	 	            	//check check box
	 	            	if(chkbox[i2].isSelected()){
							chkbox[i2].setSelected(false);
						}else{
							chkbox[i2].setSelected(true);
						}
	 	            	
	 	            	//folder create if not exists
						File directory = new File("tempfile");
						if (! directory.exists())
							directory.mkdir();
						
	 	            	//if image file, load and display it on middle pane
	 	            	if(filename[i2].endsWith("JPG")||filename[i2].endsWith("jpg")||filename[i2].endsWith("JPEG")||filename[i2].endsWith("jpeg"))
							try {
								//load
								//Load Image to middle
			 	            	String filepath = "tempfile" +"/"+ filename[i2];
			 	            	sshclient.getFile(currentPath +"/"+ filename[i2], filepath);
			 	            	File f = new File(filepath);
			 	            			
								//display
								MainView.mainViewFrame.middlePane.setImage("tempfile" +"/"+ filename[i2]);
								
			 	            	f.delete();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
	 	            }

	 	            @Override
	 	            public void mouseReleased(final MouseEvent e) {
	 	            	fileBtns[i2].setBorder(raisedetched);
	 	            }
	 	        });

				fileBtns[i].setSize(300, 50);
				fileBtns[i].add(chkbox[i], BorderLayout.EAST);
				fileBtns[i].add(iconl, BorderLayout.WEST);
				fileBtns[i].add(label, BorderLayout.CENTER);
				contentspane.add(fileBtns[i]);
				
	 		}
	 	}}
	 	
	 	/*for gap cells*/
	 	for(int i = 0 ; i < 20 ; i++)
	 		contentspane.add(new JLabel(""));

	 	this.add(scrollpane);
	 	this.revalidate();
	 	this.repaint();
	}
	
	public void deletefiles(){
		String[] deleteFileList = new String[numofFiles];
		int cnt=0;
		//get checked files
		for(int i = 0; i < numofFiles ; i++){
			if(chkbox[i].isSelected()){
				//add to list
				deleteFileList[cnt] = currentPath +"/"+ filename[i].substring(2);
				cnt++;
			}
		}
		sshclient.rmFile(deleteFileList);
		
		//update view
		refreshview();
	}
	
	public void checkallchkbox(){
		int unchecked_box = 0;
		int i = 0;
		
		//test unchecked box exist
		while(unchecked_box == 0 && i < numofFiles){
			if(chkbox[i].isSelected()==false){
				unchecked_box++;
			}
			i++;
		}

		//if all boxes are checked, uncheck everything
		if(unchecked_box == 0){
			for(i = 0 ; i < numofFiles ; i++)
				chkbox[i].setSelected(false);
		}
		//if some boxes are unchecked, check everything
		else{
			for(i = 0 ; i < numofFiles ; i++)
				chkbox[i].setSelected(true);
		}
	}
	
	public void downloadfiles(){
		String[] downloadFileList = new String[numofFiles];
		String destinPath = "";
		int cnt=0;
		
		//open dialog for path : destinPath
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			destinPath = chooser.getSelectedFile().getAbsolutePath();
					
			//get checked files
			for(int i = 0; i < numofFiles ; i++){
				if(chkbox[i].isSelected()){
					//add to list
					downloadFileList[cnt] = currentPath +"/"+ filename[i].substring(2);
					cnt++;
				}
			}
			
			sshclient.getFile(downloadFileList,destinPath);
		}
	}
	
	public static void refreshview(){
		MainView.mainViewFrame.leftPane.showFolders(currentPath);
	}
	
	public String getcurrentPath(){
		return currentPath;
	}
	
	//get checked file list
	public String[] getCheckeditems(){
		String[] temp = new String[numofFiles];
		String[] checkedfilelist = null;
		int cnt = 0;
		
		//count checked files
		for(int i = 0; i < numofFiles ; i++){
			if(chkbox[i].isSelected()){
				//count
				temp[cnt] = filename[i].substring(2);
				cnt++;
			}
		}
		
		//set size of array
		checkedfilelist = new String[cnt];

		//set list
		for(int i = 0; i < cnt ; i++){
			checkedfilelist[i] = temp[i];
		}

		return checkedfilelist;
	}
}
