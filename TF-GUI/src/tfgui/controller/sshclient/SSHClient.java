package tfgui.controller.sshclient;

import com.jcraft.jsch.*;

import tfgui.model.Model;
import tfgui.view.LoginView;
import tfgui.view.MainView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
public class SSHClient{
	private static final Logger LOGGER = Logger.getLogger(SSHClient.class.getName());
	private JSch jschSSHChannel;
	private String strUserName;
	private String strConnectionIP;
	private int intConnectionPort;
	private String strPassword;
	private Session sesConnection;
	private int intTimeOut=60000;
	
	private Channel channel;
	private InputStream commandOutput;
	private StringBuilder outputBuffer;
	
	public String pwd = "~";
	
	private void doCommonConstructorActions(String userName, 
			String password)
	{
		jschSSHChannel = new JSch();
	
	   try
	   {
	      jschSSHChannel.setKnownHosts("");
	   }
	   catch(JSchException jschX)
	   {
	      logError(jschX.getMessage());
	   }
	
	   strUserName = userName;
	   strPassword = password;
	}
	
	public SSHClient(String ConnectionIP, String userName, String password)
	{
	   strConnectionIP = ConnectionIP;
	   doCommonConstructorActions(userName, password);
	   intConnectionPort = 22;
	   
       String errorMessage = this.connecting();
          
       if(errorMessage != null)
       {	
          System.out.println(errorMessage);
          JOptionPane.showMessageDialog((JFrame)null,
				    "server connection failed check network or VPN",
				    "Inane warning",
				    JOptionPane.WARNING_MESSAGE);
       }else{
    	   new MainView(this);
           LoginView.loginframe.setVisible(false);
           
       }
	}
		
	public String connecting()
	{
	   String errorMessage = null;
	   System.out.println("try to connect to : "+strConnectionIP);
	   try
	   {
	      sesConnection = jschSSHChannel.getSession(strUserName, strConnectionIP, intConnectionPort);
	      sesConnection.setPassword(strPassword);
	      sesConnection.setConfig("StrictHostKeyChecking", "no");
	      sesConnection.connect(intTimeOut);
	      
	      System.out.println("Connect Success");
	      Model.username=strUserName;
	      Model.setpassword(strPassword);
	      Model.connectionIP = strConnectionIP;
	   }
	   catch(JSchException jschX)
	   {
		  System.out.println("connect Error Catch");
	      errorMessage = jschX.getMessage();
	   }
	
	   return errorMessage;
	}

	public String sendCommand(String command)
	{   
		command = refactoryCommand(command);
		outputBuffer = new StringBuilder();
	   try
	   {	
		  channel = sesConnection.openChannel("exec");
	      ((ChannelExec)channel).setCommand(command+ " 2>&1");
	      channel.setInputStream(null);
	      ((ChannelExec)channel).setErrStream(System.err);
		  commandOutput = channel.getInputStream();

	      channel.connect();
	      
	      int readByte = commandOutput.read();

	      while(readByte != 0xffffffff)
	      {
	         outputBuffer.append((char)readByte);
	         readByte = commandOutput.read();
	      }
	   }
	   
	   catch(IOException ioX)
	   {
	      logWarning(ioX.getMessage());
	      return null;
	   }
	   catch(JSchException jschX)
	   {
	      logWarning(jschX.getMessage());
	      return null;
	   }

	   return outputBuffer.toString();
	}

	public void putFile(File[] file, String destinPath){
		try{
			channel = sesConnection.openChannel("sftp");
			channel.connect();
			
			ChannelSftp sftp = (ChannelSftp) channel;
			for(int i = 0; i< file.length ; i++)
				sftp.put(file[i].getAbsolutePath(), destinPath);
		} catch (JSchException | SftpException e) {
			e.printStackTrace();
		}
	}
	
	public void getFile(String serverPath, String destinPath){
		try{
			channel = sesConnection.openChannel("sftp");
			channel.connect();
			
			ChannelSftp sftp = (ChannelSftp) channel;
			
			sftp.get(serverPath, destinPath);
			
			channel.disconnect();			
		} catch (JSchException | SftpException e) {
			e.printStackTrace();
		}
	}
	
	public void getFile(String[] serverPath, String destinPath){
		try{
			channel = sesConnection.openChannel("sftp");
			channel.connect();
			
			ChannelSftp sftp = (ChannelSftp) channel;
			
			for(int i = 0; i< serverPath.length; i++)
				sftp.get(serverPath[i],destinPath);
			
			channel.disconnect();			
		} catch (JSchException | SftpException e) {
			e.printStackTrace();
		}
	}
	
	public void rmFile(String[] file){
		try{
			channel = sesConnection.openChannel("sftp");
			channel.connect();
			
			ChannelSftp sftp = (ChannelSftp) channel;
			for(int i = 0; i< file.length ; i++)
				sftp.rm(file[i]);
			channel.disconnect();
		} catch (JSchException | SftpException e) {
			e.printStackTrace();
		}
	}
	
	public void CloseSession(){
		channel.disconnect();
		sesConnection.disconnect();
		System.out.println("--Disconnected--");
	}
	
	private String logError(String errorMessage)
	{
	   if(errorMessage != null)
	   {
	      LOGGER.log(Level.SEVERE, "{0}:{1} - {2}", 
	          new Object[]{strConnectionIP, intConnectionPort, errorMessage});
	   }
	
	   return errorMessage;
	}
	
	private String logWarning(String warnMessage)
	{
	   if(warnMessage != null)
	   {
	      LOGGER.log(Level.WARNING, "{0}:{1} - {2}", 
	         new Object[]{strConnectionIP, intConnectionPort, warnMessage});
	   }
	
	   return warnMessage;
	}
	
	private String refactoryCommand(String command)
	{   
		if(command.startsWith("cd ~")||command.startsWith("cd /home")){
			if(command.endsWith("/")){
				command = command.substring(0, command.length() - 1);
			}
			String[] cmd = command.split("&&");
			pwd = cmd[0].substring(3);
		}else if(command.startsWith("cd ..")){
			if(!pwd.matches("~")){
				command = "cd "+ pwd + " && " + command;
				//lower pwd level
				while(true){
					if(pwd.endsWith("/")){
						pwd = pwd.substring(0, pwd.length() - 1);
						break;
					}else{
						pwd = pwd.substring(0, pwd.length() - 1);
					}	
				}
			}else{
				command = "echo \"already home dir\"";
			}
		}else if(command.startsWith("cd")){
			if(command.endsWith("/")){
				command = command.substring(0, command.length() - 1);
			}
			//success (TO DO later add fail case)
			pwd = pwd +"/"+ command.substring(3);
			command = "cd "+ pwd;
		}else if(command.startsWith("clear")){
			MainView.mainViewFrame.rightUnderPane.setCMDtxtField("");
		}else{
			command = "cd "+ pwd + " && " + command;
		}
		return command;
	}

}
