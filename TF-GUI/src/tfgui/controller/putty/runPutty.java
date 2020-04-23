package tfgui.controller.putty;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
public class runPutty {
	public runPutty(String command){
		execommand(command);
		
		//instruction dialog
		JOptionPane.showMessageDialog((JFrame)null,
				"[[[ ! Important Instruction ! ]]]\n\n"
				+ "<<after finish>>\n"
				+ "PLEASE close window with X button on right upper side\n"
				+ "*** NEVER close process with ctrl+z ***\n"
				+ "(if you accidently close this with ctrl+z, contact admin)",
				"Inane warning",
				JOptionPane.WARNING_MESSAGE);
	}
	
	public runPutty(String command, String message){
		execommand(command);
		
		//instruction dialog
		JOptionPane.showMessageDialog((JFrame)null,
				"[[[ ! Important Instruction ! ]]]\n\n"
				+ "<<after finish>>\n"
				+ message +"\n"
				+ "PLEASE close window with X button on right upper side\n"
				+ "*** NEVER close process with ctrl+z ***\n"
				+ "(if you accidently close this with ctrl+z, contact admin)",
				"Inane warning",
				JOptionPane.WARNING_MESSAGE);
	}
	
	private void execommand(String command){
		//Declare Putty Variables
		Runtime r;
		Process p;
		InputStream std, err;
		OutputStream out;
			
		//check command
		if(!command.endsWith("\n"))
			command = command + "\n";
		
		//Open Putty
		String s = "src/putty.exe -ssh -l "+Model.username+" -pw "+Model.getpassword()+" "+Model.connectionIP+"";
		try
		{
			//open the putty session with the above given username, password and server
			r = Runtime.getRuntime();
			p = r.exec(s);
			Thread.sleep(2200);
			
			//send command
			StringSelection stringSelection = new StringSelection(command);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, stringSelection);
					
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_INSERT);
			robot.keyRelease(KeyEvent.VK_SHIFT);
			robot.keyRelease(KeyEvent.VK_INSERT);	
					
		} 
		catch (Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
