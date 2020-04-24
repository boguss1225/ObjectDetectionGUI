package tfgui.controller.putty;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import com.jcraft.jsch.Channel;

import tfgui.model.Model;

/**
 * Copyright 2019 The Block-AI-VIsion Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * ==========================================================================
 *
 * This file is part of Tensorflow GUI program under Block-AI-VIsion project.
 *
 * Made in University of Tasmania, Australia.
 *
 * @Authors : Dr.Mira Park (mira.park@utas.edu.au) Dr.Sanghee Lee
 *          (knusang1799@gmail.com) Heemoon Yoon (boguss1225@gmail.com)
 *
 *          Date : Initial Development in 2019
 *
 *          For the latest version, please check the github
 *          (https://github.com/boguss1225/ObjectDetectionGUI)
 * 
 *          ==========================================================================
 *          Description : This program allows users to train models, configure
 *          settings, detect objects and control image data within GUI level.
 *          This program converted almost every steps of Tensorflow model
 *          training into GUI so that user can easily utilize Tensorflow. To
 *          operate this program, server need to have preinstalled Tensorflow
 *          virtual environment and relevant script code.
 */

public class runprocessDia extends JDialog {
	private JTextArea middletxt;
	private JScrollPane scrP;
	private JDialog thisdia;
	private boolean ongoinflag = true;
	private boolean processfinishedsafely = false;

	public runprocessDia(String cmd) {
		/* create new dialog */
		this.setTitle("run process");
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (ongoinflag) {
					closedia();
				} else {
					thisdia.dispose();
				}
			}
		});

		/* set size of dialog */
		this.setSize(550, 550);

		/* set location */
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - this.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - this.getHeight() / 2);
		this.setLocation(xpos, ypos);

		/* set layout */
		this.setLayout(new BorderLayout());

		/* contents */
		middletxt = new JTextArea();
		middletxt.setEditable(false);
		scrP = new JScrollPane(middletxt);
		// start Process
		SwingWorker sw1 = new SwingWorker() {
			@Override
			protected String doInBackground() throws Exception {
				// get new Channel
				Channel channel = Model.sshclient.sendCommandStream(cmd);

				// ready for stream
				StringBuilder outputBuffer = new StringBuilder();
				InputStream commandOutput = channel.getInputStream();
				channel.connect();
				int readByte = commandOutput.read();
				int cnt = 30;
				
				// get streams
				while (ongoinflag && readByte != 0xffffffff) {
					outputBuffer.append((char) readByte);
					readByte = commandOutput.read();
					cnt++;
					if (cnt == 75) {
						middletxt.setText(outputBuffer.toString());
						cnt = 0;
					}
				}
				// finished stream
				ongoinflag = false;
				String res = outputBuffer.toString();
				//check normaly finished?
				if(readByte != 0xffffffff) {
					res = res + "<<<<  Finished Execution  >>>>";
					processfinishedsafely();
				}
				middletxt.setText(res);
				return "finish";
			}
		};
		sw1.execute();

		/* buttons */
		JButton b1 = new JButton("close");
		class b1EventHandler implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (ongoinflag) {
					closedia();
				} else {
					thisdia.dispose();
				}
			}
		}
		b1.addActionListener(new b1EventHandler());
		JPanel btnP = new JPanel(new FlowLayout());
		btnP.add(b1);

		// add components
		this.add(scrP, BorderLayout.CENTER);
		this.add(btnP, BorderLayout.SOUTH);
		this.setVisible(true);

		thisdia = this;
	}

	private void closedia() {
		/* create new dialog */
		JDialog closeDia = new JDialog((JFrame) null, "Login Environment", true);
		closeDia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		/* set size of dialog */
		closeDia.setSize(300, 150);

		/* set location */
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - closeDia.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - closeDia.getHeight() / 2);
		closeDia.setLocation(xpos, ypos);

		/* set layout */
		closeDia.setLayout(new BorderLayout());

		/* contents */
		JLabel lb = new JLabel(
				"<html><body>Process will be stopped" + "<br>Do you really want to close window?</body></html>\n");
		lb.setHorizontalAlignment(SwingConstants.CENTER);

		/* buttons */
		JButton b1 = new JButton("OK");
		class b1EventHandler implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent ae) {
				thisdia.dispose();
				closeDia.dispose();
				ongoinflag = false;
			}
		}
		b1.addActionListener(new b1EventHandler());

		JButton b2 = new JButton("cancel");
		class b2EventHandler implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent ae) {
				closeDia.dispose();
			}
		}
		b2.addActionListener(new b2EventHandler());

		JPanel btnP = new JPanel(new FlowLayout());
		btnP.add(b1);
		btnP.add(b2);

		// add components
		closeDia.add(lb, BorderLayout.CENTER);
		closeDia.add(btnP, BorderLayout.SOUTH);
		closeDia.setVisible(true);
	}
	
	private void processfinishedsafely() {
		this.processfinishedsafely = true;
	}
	
	public boolean getprocessfinishedsafely() {
		return this.processfinishedsafely;
	}
	
	public boolean getongoinflag() {
		return this.ongoinflag;
	}
}
