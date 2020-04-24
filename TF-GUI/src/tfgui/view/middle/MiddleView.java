package tfgui.view.middle;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
public class MiddleView extends JPanel{
	private static final long serialVersionUID = 1L;
	private JLabel middleimage;
	private JScrollPane scrP;
	private JTextArea middletxt;
	private int width, height;
	
	public MiddleView(){
		this.setLayout(new BorderLayout());
		this.setSize(300, 10);
		
		// init image
		ImageIcon mainIcon = new ImageIcon("src/tfgui/icon/tfutaslogo.png");	
		middleimage = new JLabel(mainIcon);
		
		// init text field
		middletxt = new JTextArea(28,20);
		middletxt.setEditable(false);
		scrP = new JScrollPane(middletxt);
		scrP.setVisible(false);
		
		//add components
		this.add(scrP, BorderLayout.NORTH);
		this.add(middleimage, BorderLayout.CENTER);
	}
	
	public void setImage(String path) throws IOException{
		Image srcImg = null;
		
		srcImg = ImageIO.read(new File(path));
		BufferedImage bimg = ImageIO.read(new File(path));		
		width          = bimg.getWidth();
		height         = bimg.getHeight();
		recursiveResizer(width, height);
		
		BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, width, height, null);
	    g2.dispose();
	    
		middleimage.setIcon(new ImageIcon(resizedImg));
		
		// set visibility
		scrP.setVisible(false);
		middleimage.setVisible(true);
	}
	
	public void setText(String path)throws IOException{
		String srcTxt = null;

		//get text from path
		srcTxt = new String(Files.readAllBytes(Paths.get(path)));
		middletxt.setText(srcTxt);
		
		// set visibility
		scrP.setVisible(true);
		middleimage.setVisible(false);
	}
	
	private void recursiveResizer(int w, int h){
		if(w>500||h>500){
			width = (int) (w * 0.9);
			height = (int) (h * 0.9);
			recursiveResizer(width, height);
		}
	}
}
