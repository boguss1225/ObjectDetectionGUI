package tfgui.controller.view;

import java.awt.Color;

import javax.swing.UIManager;
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
public class Theme {
	public Theme(){
		  UIManager.put( "control", new Color( 128, 128, 128) );
		  UIManager.put( "info", new Color(128,128,128) );
		  UIManager.put( "nimbusBase", new Color( 18, 30, 49) );
		  UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
		  UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
		  UIManager.put( "nimbusFocus", new Color(115,164,209) );
		  UIManager.put( "nimbusGreen", new Color(176,179,50) );
		  UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
		  UIManager.put( "nimbusLightBackground", new Color( 18, 30, 49) );
		  UIManager.put( "nimbusOrange", new Color(191,98,4) );
		  UIManager.put( "nimbusRed", new Color(169,46,34) );
		  UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) );
		  UIManager.put( "nimbusSelectionBackground", new Color( 104, 93, 156) );
		  UIManager.put( "text", new Color( 230, 230, 230) );
		  try {
		    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		      if ("Nimbus".equals(info.getName())) {
		          javax.swing.UIManager.setLookAndFeel(info.getClassName());
		          break;
		      }
		    }
		  } catch (ClassNotFoundException e) {
		    e.printStackTrace();
		  } catch (InstantiationException e) {
		    e.printStackTrace();
		  } catch (IllegalAccessException e) {
		    e.printStackTrace();
		  } catch (javax.swing.UnsupportedLookAndFeelException e) {
		    e.printStackTrace();
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
	}
}
