package tfgui.view.right;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import tfgui.controller.sshclient.SSHClient;
import tfgui.controller.view.DoubleJTextField;
import tfgui.controller.view.JNumberTextField;
import tfgui.controller.view.ModifiedFlowLayout;
import tfgui.model.Model;
import tfgui.view.MainView;

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
public class Step3Dialog {
	private SSHClient sshclient;
	// Dialog
	private JDialog Dia;
	// Panel setting
	private JPanel northP;
	private JScrollPane contentP;
	// Models setting
	private JComboBox modelselectionCB;
	// other model setting
	private JTextArea contentsTA;
	// Num of class setting
	private JNumberTextField numofClassTF;
	private JNumberTextField num_stepsTF;
	// Image_resizer
	private JNumberTextField min_dimensionTF;
	private JNumberTextField max_dimensionTF;
	// Feature_extractor
	@SuppressWarnings("rawtypes")
	private JComboBox feature_extractorCB;
	private JNumberTextField first_stage_features_strideTF;
	// First Stage Setting
	private DoubleJTextField first_stage_nms_score_thresholdTF;
	private DoubleJTextField first_stage_nms_iou_thresholdTF;
	private JNumberTextField first_stage_max_proposalsTF;
	private DoubleJTextField first_stage_localization_loss_weightTF;
	private DoubleJTextField first_stage_objectness_loss_weightTF;
	private JNumberTextField initial_crop_sizeTF;
	private JNumberTextField maxpool_kernel_sizeTF;
	private JNumberTextField maxpool_strideTF;
	// Second Stage Setting
	private DoubleJTextField score_thresholdTF;
	private DoubleJTextField iou_thresholdTF;
	private JNumberTextField max_detections_per_classTF;
	private JNumberTextField max_total_detectionsTF;
	private DoubleJTextField localization_loss_weightTF;
	private DoubleJTextField classification_loss_weightTF;
	// Train_config setting
	private JNumberTextField batch_sizeTF;
	private DoubleJTextField initial_learning_rateTF;
	private JNumberTextField schedule1_stepTF;
	private DoubleJTextField schedule1_learining_rateTF;
	private JNumberTextField schedule2_stepTF;
	private JNumberTextField schedule2_learining_rateTF;
	private DoubleJTextField momentum_optimizer_valueTF;
	private JTextField use_moving_averageTF;
	// Path deatail setting
	private JTextField fine_tune_checkpointTF;
	private JTextField input_pathTF;
	private JTextField label_map_pathTF;
	private JComboBox metrics_setCB;
	private JNumberTextField num_examplesTF;
	private JTextField eval_input_pathTF;
	private JTextField eval_label_map_pathTF;
	// data
	private String[] configdata;
	// MouseAdapter
	private MouseAdapter mouseadapter;

	protected Step3Dialog() {
		sshclient = Model.sshclient;

		/* redirect to training folder */
		MainView.mainViewFrame.leftPane.showFolders("/home/" + Model.username + "/tensorflowGUI/" + Model.ActivatedEnv
				+ "/models/research/object_detection/training");

		/* create new dialog */
		Dia = new JDialog((JFrame) null, "Step3. Setting Config File", true);
		Dia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		/* set size of dialog */
		Dia.setSize(720, 400);

		/* set location */
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - Dia.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - Dia.getHeight() / 2);
		Dia.setLocation(xpos, ypos);

		/* set layout */
		Dia.setLayout(new BorderLayout());

		/* North */
		northP = northpane();

		/* CENTER - Set Contents Pane */
		if (Model.selectedModel.equals("faster_rcnn_inception_v2")) {
			contentP = contentpane();
			initdata();
			loaddata();
		} else {
			contentP = contentpane2();
			loaddata2();
		}

		// SOUTH - set Button Pane
		/* set button1 */
		JButton b1 = new JButton("OK");
		class b1EventHandler implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent ae) {
				// action
				if (Model.selectedModel.equals("faster_rcnn_inception_v2"))
					exportdata();
				else
					exportdata2();

				// set RightUpperView components
				RightUpperView.descriptionLabel.setText("Complished - Step3");
				RightUpperView.descriptionLabel.setForeground(Color.GREEN);
				RightUpperView.finishedstep = 3;
				RightUpperView.trainingbtn.setBackground(Color.decode("#148214"));
				RightUpperView.trainingbtn.setForeground(Color.white);

				// update log on RightUnderView
				RightUnderView.updateCMDtxtField("**** Step3 'Setting Config File' executed ****");

				Dia.dispose();

			}
		}
		b1.addActionListener(new b1EventHandler());
		JPanel pb1 = new JPanel(new FlowLayout());
		pb1.add(b1);

		/* set button2 */
		JButton b2 = new JButton("cancel");
		class b2EventHandler implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent ae) {
				Dia.dispose();
			}
		}
		b2.addActionListener(new b2EventHandler());
		JPanel pb2 = new JPanel(new FlowLayout());
		pb2.add(b2);

		/* set init button1 */
		JButton b3 = new JButton("initialize settings");
		class b3EventHandler implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent ae) {
				// action
				if (Model.selectedModel.equals("faster_rcnn_inception_v2"))
					initdata();
			}
		}
		b3.addActionListener(new b3EventHandler());
		JPanel pb3 = new JPanel(new FlowLayout());
		pb3.add(b3);

		/* set Button Pane */
		JPanel buttonPane = new JPanel(new GridLayout(1, 3));
		buttonPane.add(pb1);
		buttonPane.add(pb3);
		buttonPane.add(pb2);

		/* add components */
		Dia.add(northP, BorderLayout.NORTH);
		Dia.add(contentP, BorderLayout.CENTER);
		Dia.add(buttonPane, BorderLayout.SOUTH);
		Dia.setVisible(true);

	}

	private JPanel northpane() {
		JPanel northpane = new JPanel(new BorderLayout());

		String[] model_types = { "faster_rcnn_inception_resnet_v2_atrous_coco",
				"faster_rcnn_inception_resnet_v2_atrous_lowproposals_coco", "faster_rcnn_inception_v2",
				"faster_rcnn_nas_coco", "faster_rcnn_nas_lowproposals_coco", "faster_rcnn_resnet50_coco",
				"faster_rcnn_resnet50_lowproposals_coco", "faster_rcnn_resnet101_coco",
				"faster_rcnn_resnet101_lowproposals_coco", "mask_rcnn_inception_resnet_v2_atrous_coco",
				"mask_rcnn_inception_v2_coco", "mask_rcnn_resnet50_atrous_coco", "mask_rcnn_resnet101_atrous",
				"rfcn_resnet101_coco", "ssd_inception_v2_coco", "ssd_mobilenet_v1_0.75_depth_300x300_coco",
				"ssd_mobilenet_v1_0.75_depth_quantized_300x300_coco", "ssd_mobilenet_v1_coco",
				"ssd_mobilenet_v1_fpn_shared_box_predictor_640x640_coco",
				"ssd_mobilenet_v1_ppn_shared_box_predictor_300x300_coco", "ssd_mobilenet_v1_quantized_300x300_coco",
				"ssd_mobilenet_v2_coco" };
		modelselectionCB = new JComboBox(model_types);

		// init selected model
		modelselectionCB.setSelectedItem(Model.selectedModel);

		// select action
		modelselectionCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedmodel = modelselectionCB.getSelectedItem().toString();
				Model.selectedModel = selectedmodel;
				Dia.dispose();
				new Step3Dialog();
			}
		});

		northpane.add(modelselectionCB, BorderLayout.WEST);
		
		return northpane;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JScrollPane contentpane() {
		// initialize MouseAdapter
		mouseadapter = new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 2) {
					JTextField temptxtfld = (JTextField) event.getComponent();
					temptxtfld.setEditable(true);
					temptxtfld.setBackground(Color.white);
					temptxtfld.setForeground(Color.black);
				}
			}
		};

		// num_classes
		JPanel num_classesP = new JPanel(new FlowLayout());
		JLabel num_classesL = new JLabel("num_classes");
		numofClassTF = new JNumberTextField();
		numofClassTF.setColumns(7);
		numofClassTF.setEditable(false);
		numofClassTF.setToolTipText("exactly same with the number of classes set at step1");
		numofClassTF.addMouseListener(mouseadapter);
		num_classesP.add(num_classesL);
		num_classesP.add(numofClassTF);

		// num_steps
		JPanel num_stepsP = new JPanel(new FlowLayout());
		JLabel num_stepsL = new JLabel("num_steps");
		num_stepsTF = new JNumberTextField();
		num_stepsTF.setColumns(7);
		num_stepsTF.setEditable(true);
		num_stepsTF.setBackground(Color.white);
		num_stepsTF.setForeground(Color.BLACK);
		num_stepsTF.setToolTipText("number of training steps. recommand CPU ver : 100 GPU ver : 10000");
		num_stepsP.add(num_stepsL);
		num_stepsP.add(num_stepsTF);

		// Panel for num_classes & steps
		JPanel numClassesAndStepP = new JPanel(new FlowLayout());
		numClassesAndStepP.add(num_classesP);
		numClassesAndStepP.add(num_stepsP);

		// image_resizer
		TitledBorder image_resizertitleb = BorderFactory.createTitledBorder("image_resizer");
		JPanel image_resizerP = new JPanel(new GridLayout(2, 2, 1, 4));

		JLabel min_dimensionLB = new JLabel("min_dimension");
		JLabel max_dimensionLB = new JLabel("max_dimension");
		min_dimensionTF = new JNumberTextField();
		max_dimensionTF = new JNumberTextField();

		min_dimensionTF.setToolTipText("double click to edit");
		max_dimensionTF.setToolTipText("double click to edit");
		min_dimensionTF.setEditable(false);
		min_dimensionTF.addMouseListener(mouseadapter);
		min_dimensionTF.setBackground(Color.white);
		min_dimensionTF.setForeground(Color.black);
		max_dimensionTF.setEditable(false);
		max_dimensionTF.addMouseListener(mouseadapter);
		max_dimensionTF.setBackground(Color.white);
		max_dimensionTF.setForeground(Color.black);

		image_resizerP.add(min_dimensionLB);
		image_resizerP.add(min_dimensionTF);
		image_resizerP.add(max_dimensionLB);
		image_resizerP.add(max_dimensionTF);
		image_resizerP.setBorder(image_resizertitleb);

		// feature_extractor
		TitledBorder feature_extractortitleb = BorderFactory.createTitledBorder("Feature_extractor");
		JPanel feature_extractorP = new JPanel(new GridLayout(2, 2, 1, 1));

		JLabel feature_extractortypesL = new JLabel("type");
		JLabel first_stage_features_strideL = new JLabel("first_stage_features_stride");
		String[] feature_extractortypes = { "faster_rcnn_inception_v2", "none", "none" };
		feature_extractorCB = new JComboBox(feature_extractortypes);

		first_stage_features_strideTF = new JNumberTextField();
		first_stage_features_strideTF.setEditable(false);
		first_stage_features_strideTF.addMouseListener(mouseadapter);

		feature_extractorP.add(feature_extractortypesL);
		feature_extractorP.add(feature_extractorCB);
		feature_extractorP.add(first_stage_features_strideL);
		feature_extractorP.add(first_stage_features_strideTF);
		feature_extractorP.setBorder(feature_extractortitleb);

		// Panel for image_resizer & feature_extractor
		JPanel resizerAndExtractorP = new JPanel(new FlowLayout());
		resizerAndExtractorP.add(image_resizerP);
		resizerAndExtractorP.add(feature_extractorP);

		// First_stage_post_processing
		TitledBorder firstStgtitleb = BorderFactory.createTitledBorder("First Stage Setting");
		JPanel firstStgP = new JPanel(new FlowLayout());
		JPanel firstStgLP = new JPanel(new GridLayout(8, 1, 2, 14));
		JPanel firstStgTFP = new JPanel(new GridLayout(8, 1, 1, 1));

		JLabel first_stage_nms_score_thresholdL = new JLabel("first_stage_nms_score_threshold");
		JLabel first_stage_nms_iou_thresholdL = new JLabel("first_stage_nms_iou_threshold");
		JLabel first_stage_max_proposalsL = new JLabel("first_stage_max_proposals");
		JLabel first_stage_localization_loss_weightL = new JLabel("first_stage_localization_loss_weight");
		JLabel first_stage_objectness_loss_weightL = new JLabel("first_stage_objectness_loss_weight");
		JLabel initial_crop_sizeL = new JLabel("initial_crop_size");
		JLabel maxpool_kernel_sizeL = new JLabel("maxpool_kernel_size");
		JLabel maxpool_strideL = new JLabel("maxpool_stride");

		first_stage_nms_score_thresholdTF = new DoubleJTextField();
		first_stage_nms_iou_thresholdTF = new DoubleJTextField();
		first_stage_max_proposalsTF = new JNumberTextField();
		first_stage_localization_loss_weightTF = new DoubleJTextField();
		first_stage_objectness_loss_weightTF = new DoubleJTextField();
		initial_crop_sizeTF = new JNumberTextField();
		maxpool_kernel_sizeTF = new JNumberTextField();
		maxpool_strideTF = new JNumberTextField();

		first_stage_nms_score_thresholdTF.setEditable(false);
		first_stage_nms_iou_thresholdTF.setEditable(false);
		first_stage_max_proposalsTF.setEditable(false);
		first_stage_localization_loss_weightTF.setEditable(false);
		first_stage_objectness_loss_weightTF.setEditable(false);
		initial_crop_sizeTF.setEditable(false);
		maxpool_kernel_sizeTF.setEditable(false);
		maxpool_strideTF.setEditable(false);
		maxpool_strideTF.setColumns(7);

		first_stage_nms_score_thresholdTF.addMouseListener(mouseadapter);
		first_stage_nms_iou_thresholdTF.addMouseListener(mouseadapter);
		first_stage_max_proposalsTF.addMouseListener(mouseadapter);
		first_stage_localization_loss_weightTF.addMouseListener(mouseadapter);
		first_stage_objectness_loss_weightTF.addMouseListener(mouseadapter);
		initial_crop_sizeTF.addMouseListener(mouseadapter);
		maxpool_kernel_sizeTF.addMouseListener(mouseadapter);
		maxpool_strideTF.addMouseListener(mouseadapter);

		firstStgLP.add(first_stage_nms_score_thresholdL);
		firstStgLP.add(first_stage_nms_iou_thresholdL);
		firstStgLP.add(first_stage_max_proposalsL);
		firstStgLP.add(first_stage_localization_loss_weightL);
		firstStgLP.add(first_stage_objectness_loss_weightL);
		firstStgLP.add(initial_crop_sizeL);
		firstStgLP.add(maxpool_kernel_sizeL);
		firstStgLP.add(maxpool_strideL);
		firstStgTFP.add(first_stage_nms_score_thresholdTF);
		firstStgTFP.add(first_stage_nms_iou_thresholdTF);
		firstStgTFP.add(first_stage_max_proposalsTF);
		firstStgTFP.add(first_stage_localization_loss_weightTF);
		firstStgTFP.add(first_stage_objectness_loss_weightTF);
		firstStgTFP.add(initial_crop_sizeTF);
		firstStgTFP.add(maxpool_kernel_sizeTF);
		firstStgTFP.add(maxpool_strideTF);
		firstStgP.add(firstStgLP);
		firstStgP.add(firstStgTFP);
		firstStgP.setBorder(firstStgtitleb);

		// Second_stage_post_processing
		TitledBorder secondStgtitleb = BorderFactory.createTitledBorder("Second Stage Setting");
		JPanel secondStgP = new JPanel(new FlowLayout());
		JPanel secondStgLP = new JPanel(new GridLayout(8, 1, 2, 14));
		JPanel secondStgTFP = new JPanel(new GridLayout(8, 1, 1, 1));

		JLabel score_thresholdL = new JLabel("score_threshold");
		JLabel iou_thresholdL = new JLabel("iou_threshold");
		JLabel max_detections_per_classL = new JLabel("max_detections_per_class");
		JLabel max_total_detectionsL = new JLabel("max_total_detections");
		JLabel localization_loss_weightL = new JLabel("localization_loss_weight");
		JLabel classification_loss_weightL = new JLabel("classification_loss_weight");

		score_thresholdTF = new DoubleJTextField();
		iou_thresholdTF = new DoubleJTextField();
		max_detections_per_classTF = new JNumberTextField();
		max_total_detectionsTF = new JNumberTextField();
		localization_loss_weightTF = new DoubleJTextField();
		classification_loss_weightTF = new DoubleJTextField();

		score_thresholdTF.setEditable(false);
		iou_thresholdTF.setEditable(false);
		max_detections_per_classTF.setEditable(false);
		max_total_detectionsTF.setEditable(false);
		localization_loss_weightTF.setEditable(false);
		classification_loss_weightTF.setEditable(false);

		score_thresholdTF.addMouseListener(mouseadapter);
		iou_thresholdTF.addMouseListener(mouseadapter);
		max_detections_per_classTF.addMouseListener(mouseadapter);
		max_total_detectionsTF.addMouseListener(mouseadapter);
		localization_loss_weightTF.addMouseListener(mouseadapter);
		classification_loss_weightTF.addMouseListener(mouseadapter);

		secondStgLP.add(score_thresholdL);
		secondStgLP.add(iou_thresholdL);
		secondStgLP.add(max_detections_per_classL);
		secondStgLP.add(max_total_detectionsL);
		secondStgLP.add(localization_loss_weightL);
		secondStgLP.add(classification_loss_weightL);
		secondStgLP.add(new JLabel());
		secondStgLP.add(new JLabel());
		secondStgTFP.add(score_thresholdTF);
		secondStgTFP.add(iou_thresholdTF);
		secondStgTFP.add(max_detections_per_classTF);
		secondStgTFP.add(max_total_detectionsTF);
		secondStgTFP.add(localization_loss_weightTF);
		secondStgTFP.add(classification_loss_weightTF);
		secondStgTFP.add(new JLabel());
		secondStgTFP.add(new JLabel());
		secondStgP.add(secondStgLP);
		secondStgP.add(secondStgTFP);
		secondStgP.setBorder(secondStgtitleb);

		// First+ Second stage post processing Pane
		JPanel firstSecondP = new JPanel(new GridLayout(1, 2, 2, 2));
		firstSecondP.add(firstStgP);
		firstSecondP.add(secondStgP);
		firstSecondP.setVisible(false);

		// Show Advanced setting button
		JPanel showadvancedP = new JPanel(new FlowLayout());
		JButton advancedB = new JButton(new ImageIcon("src/tfgui/icon/underarrowyellow.png"));
		JLabel advancedL = new JLabel(
				"Show Advanced Setting                                                                                                                                       ");
		class advancedBEventHandler implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (firstSecondP.isVisible()) {
					firstSecondP.setVisible(false);
					advancedB.setIcon(new ImageIcon("src/tfgui/icon/underarrowyellow.png"));
				} else {
					firstSecondP.setVisible(true);
					advancedB.setIcon(new ImageIcon("src/tfgui/icon/upperarrowyellow.png"));
				}
			}
		}
		advancedB.addActionListener(new advancedBEventHandler());

		showadvancedP.add(advancedB);
		showadvancedP.add(advancedL);

		// train_config
		TitledBorder trainconfigtitleb = BorderFactory.createTitledBorder("train_config Setting");
		JPanel trainconfigP = new JPanel(new FlowLayout());
		JPanel trainconfigLP = new JPanel(new GridLayout(8, 1, 2, 14));
		JPanel trainconfigTFP = new JPanel(new GridLayout(8, 1, 1, 1));

		JLabel batch_sizeL = new JLabel("batch_size");
		JLabel initial_learning_rateL = new JLabel("initial_learning rate");
		JLabel schedule1_stepL = new JLabel("iou_threshold");
		JLabel schedule1_learining_rateL = new JLabel("max_detections_per_class");
		JLabel schedule2_stepL = new JLabel("max_total_detections");
		JLabel schedule2_learining_rateL = new JLabel("localization_loss_weight");
		JLabel momentum_optimizer_valueL = new JLabel("momentum_optimizer_value");
		JLabel use_moving_averageL = new JLabel("use_moving_average");

		batch_sizeTF = new JNumberTextField();
		initial_learning_rateTF = new DoubleJTextField();
		schedule1_stepTF = new JNumberTextField();
		schedule1_learining_rateTF = new DoubleJTextField();
		schedule2_stepTF = new JNumberTextField();
		schedule2_learining_rateTF = new JNumberTextField();
		momentum_optimizer_valueTF = new DoubleJTextField();
		use_moving_averageTF = new JTextField();

		batch_sizeTF.setEditable(false);
		initial_learning_rateTF.setEditable(false);
		schedule1_stepTF.setEditable(false);
		schedule1_learining_rateTF.setEditable(false);
		schedule2_stepTF.setEditable(false);
		schedule2_learining_rateTF.setEditable(false);
		momentum_optimizer_valueTF.setEditable(false);
		use_moving_averageTF.setEditable(false);

		batch_sizeTF.addMouseListener(mouseadapter);
		initial_learning_rateTF.addMouseListener(mouseadapter);
		schedule1_stepTF.addMouseListener(mouseadapter);
		schedule1_learining_rateTF.addMouseListener(mouseadapter);
		schedule2_stepTF.addMouseListener(mouseadapter);
		schedule2_learining_rateTF.addMouseListener(mouseadapter);
		momentum_optimizer_valueTF.addMouseListener(mouseadapter);
		use_moving_averageTF.addMouseListener(mouseadapter);

		trainconfigLP.add(batch_sizeL);
		trainconfigLP.add(initial_learning_rateL);
		trainconfigLP.add(schedule1_stepL);
		trainconfigLP.add(schedule1_learining_rateL);
		trainconfigLP.add(schedule2_stepL);
		trainconfigLP.add(schedule2_learining_rateL);
		trainconfigLP.add(momentum_optimizer_valueL);
		trainconfigLP.add(use_moving_averageL);

		trainconfigTFP.add(batch_sizeTF);
		trainconfigTFP.add(initial_learning_rateTF);
		trainconfigTFP.add(schedule1_stepTF);
		trainconfigTFP.add(schedule1_learining_rateTF);
		trainconfigTFP.add(schedule2_stepTF);
		trainconfigTFP.add(schedule2_learining_rateTF);
		trainconfigTFP.add(momentum_optimizer_valueTF);
		trainconfigTFP.add(use_moving_averageTF);

		trainconfigP.add(trainconfigLP);
		trainconfigP.add(trainconfigTFP);
		trainconfigP.setBorder(trainconfigtitleb);
		trainconfigP.setVisible(false);

		// show train_config button
		JPanel showtrainconfigP = new JPanel(new FlowLayout());
		JButton showtrainconfigB = new JButton(new ImageIcon("src/tfgui/icon/underarrowblue.png"));
		JLabel showtrainconfigL = new JLabel(
				"Show Path Detail of Input Reader                                                                                                                                       ");
		class showtrainconfigBEventHandler implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (trainconfigP.isVisible()) {
					trainconfigP.setVisible(false);
					showtrainconfigB.setIcon(new ImageIcon("src/tfgui/icon/underarrowblue.png"));
				} else {
					trainconfigP.setVisible(true);
					showtrainconfigB.setIcon(new ImageIcon("src/tfgui/icon/upperarrowblue.png"));
				}
			}
		}
		showtrainconfigB.addActionListener(new showtrainconfigBEventHandler());

		showtrainconfigP.add(showtrainconfigB);
		showtrainconfigP.add(showtrainconfigL);

		// fine_tune_checkpoint
		TitledBorder fine_tune_checkpointtitleb = BorderFactory.createTitledBorder("fine_tune_checkpoint");
		JPanel fine_tune_checkpointP = new JPanel(new FlowLayout());
		fine_tune_checkpointTF = new JTextField();
		fine_tune_checkpointTF.setEditable(false);
		fine_tune_checkpointTF.addMouseListener(mouseadapter);

		fine_tune_checkpointP.add(fine_tune_checkpointTF);
		fine_tune_checkpointP.setBorder(fine_tune_checkpointtitleb);

		// train_input_reader:
		TitledBorder train_input_readerPtitleb = BorderFactory.createTitledBorder("train_input_reader");
		JPanel train_input_readerP = new JPanel(new FlowLayout());
		JPanel label_map_pathP = new JPanel(new GridLayout(2, 1, 1, 1));
		JPanel input_pathP = new JPanel(new GridLayout(2, 1, 1, 1));
		JLabel input_pathL = new JLabel("input_path");
		JLabel label_map_pathL = new JLabel("label_map_path");
		input_pathTF = new JTextField();
		label_map_pathTF = new JTextField();

		input_pathTF.setEditable(false);
		label_map_pathTF.setEditable(false);
		input_pathTF.addMouseListener(mouseadapter);
		label_map_pathTF.addMouseListener(mouseadapter);

		input_pathP.add(input_pathL);
		input_pathP.add(label_map_pathL);
		label_map_pathP.add(input_pathTF);
		label_map_pathP.add(label_map_pathTF);
		train_input_readerP.add(input_pathP);
		train_input_readerP.add(label_map_pathP);
		train_input_readerP.setBorder(train_input_readerPtitleb);

		// eval_config:
		TitledBorder eval_configtitleb = BorderFactory.createTitledBorder("eval_config");
		JPanel eval_configP = new JPanel(new GridLayout(2, 2, 1, 1));
		JLabel metrics_setL = new JLabel("metrics_set");
		JLabel num_examplesL = new JLabel("num_examples");
		String[] metrics_settypes = { "coco_detection_metrics", "coco_mask_metrics", "pascal_voc_detection_metrics",
				"weighted_pascal_voc_detection_metrics", "pascal_voc_instance_segmentation_metrics",
				"weighted_pascal_voc_instance_segmentation_metrics", "oid_V2_detection_metrics",
				"open_images_V2_detection_metrics", "oid_challenge_detection_metrics",
				"oid_challenge_object_detection_metrics", "oid_challenge_segmentation_metrics" };
		metrics_setCB = new JComboBox(metrics_settypes);
		num_examplesTF = new JNumberTextField();

		num_examplesTF.setEditable(false);
		num_examplesTF.addMouseListener(mouseadapter);

		eval_configP.add(metrics_setL);
		eval_configP.add(metrics_setCB);
		eval_configP.add(num_examplesL);
		eval_configP.add(num_examplesTF);
		eval_configP.setBorder(eval_configtitleb);

		// eval_input_reader:
		TitledBorder eval_input_readertitleb = BorderFactory.createTitledBorder("eval_input_reader");
		JPanel eval_input_readerP = new JPanel(new FlowLayout());
		JPanel eval_input_pathP = new JPanel(new GridLayout(2, 1, 1, 1));
		JPanel eval_label_map_pathP = new JPanel(new GridLayout(2, 1, 1, 1));
		JLabel eval_input_pathL = new JLabel("input_path");
		JLabel eval_label_map_pathL = new JLabel("label_map_path");
		eval_input_pathTF = new JTextField();
		eval_label_map_pathTF = new JTextField();

		eval_input_pathTF.setEditable(false);
		eval_label_map_pathTF.setEditable(false);
		eval_input_pathTF.addMouseListener(mouseadapter);
		eval_label_map_pathTF.addMouseListener(mouseadapter);

		eval_label_map_pathP.add(eval_input_pathL);
		eval_label_map_pathP.add(eval_label_map_pathL);
		eval_input_pathP.add(eval_input_pathTF);
		eval_input_pathP.add(eval_label_map_pathTF);
		eval_input_readerP.add(eval_label_map_pathP);
		eval_input_readerP.add(eval_input_pathP);
		eval_input_readerP.setBorder(eval_input_readertitleb);

		// add to path panel
		JPanel pathPanels = new JPanel(new GridLayout(4, 1, 3, 3));
		pathPanels.add(fine_tune_checkpointP);
		pathPanels.add(train_input_readerP);
		pathPanels.add(eval_configP);
		pathPanels.add(eval_input_readerP);
		pathPanels.setVisible(false);

		// show path button
		JPanel showPathP = new JPanel(new FlowLayout());
		JButton showPathB = new JButton(new ImageIcon("src/tfgui/icon/underarrowgreen.png"));
		JLabel showPathL = new JLabel(
				"Show Path Detail of Input Reader                                                                                                                                       ");
		class showPathBEventHandler implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (pathPanels.isVisible()) {
					pathPanels.setVisible(false);
					showPathB.setIcon(new ImageIcon("src/tfgui/icon/underarrowgreen.png"));
				} else {
					pathPanels.setVisible(true);
					showPathB.setIcon(new ImageIcon("src/tfgui/icon/upperarrowgreen.png"));
				}
			}
		}
		showPathB.addActionListener(new showPathBEventHandler());

		showPathP.add(showPathB);
		showPathP.add(showPathL);

		// add to main panel
		JPanel mainp = new JPanel();
		mainp.setLayout(new ModifiedFlowLayout(ModifiedFlowLayout.LEFT));

		mainp.add(numClassesAndStepP);
		mainp.add(resizerAndExtractorP);
		mainp.add(showadvancedP);
		mainp.add(firstSecondP);
		mainp.add(showtrainconfigP);
		mainp.add(trainconfigP);
		mainp.add(showPathP);
		mainp.add(pathPanels);

		mainp.setVisible(true);
		JScrollPane jscrollpane = new JScrollPane(mainp);
		return jscrollpane;
	}

	private JScrollPane contentpane2() {
		contentsTA = new JTextArea();
		contentsTA.setEditable(true);
		contentsTA.setBackground(Color.black);
		JScrollPane contentsSP = new JScrollPane(contentsTA);
		return contentsSP;
	}

	private void initdata() {
		configdata = new String[35];
		String pathToObjDetec = "/home/" + Model.username + "/tensorflowGUI/" + Model.ActivatedEnv
				+ "/models/research/object_detection";
		String fine_tune_checkpoint = pathToObjDetec + "/faster_rcnn_inception_v2_coco_2018_01_28/model.ckpt";
		String input_path = pathToObjDetec + "/train.record";
		String label_map_path = pathToObjDetec + "/training/labelmap.pbtxt";
		String eval_input_path = pathToObjDetec + "/test.record";
		String eval_label_map_path = pathToObjDetec + "/training/labelmap.pbtxt";

		// Num of class setting
		numofClassTF.setText(Integer.toString(Model.numofclass));
		num_stepsTF.setText("1000");
		// Image_resizer
		min_dimensionTF.setText("600");
		max_dimensionTF.setText("1024");
		// Feature_extractor
		first_stage_features_strideTF.setText("16");
		// First Stage Setting
		first_stage_nms_score_thresholdTF.setText("0.0");
		first_stage_nms_iou_thresholdTF.setText("0.7");
		first_stage_max_proposalsTF.setText("300");
		first_stage_localization_loss_weightTF.setText("2.0");
		first_stage_objectness_loss_weightTF.setText("1.0");
		initial_crop_sizeTF.setText("14");
		maxpool_kernel_sizeTF.setText("2");
		maxpool_strideTF.setText("2");
		// Second Stage Setting
		score_thresholdTF.setText("0.0");
		iou_thresholdTF.setText("0.6");
		max_detections_per_classTF.setText("100");
		max_total_detectionsTF.setText("300");
		localization_loss_weightTF.setText("2.0");
		classification_loss_weightTF.setText("1.0");
		// Train_config setting
		batch_sizeTF.setText("1");
		initial_learning_rateTF.setText("0.0002");
		schedule1_stepTF.setText("900000");
		schedule1_learining_rateTF.setText("0.00002");
		schedule2_stepTF.setText("1200000");
		schedule2_learining_rateTF.setText("0.000002");
		momentum_optimizer_valueTF.setText("0.9");
		use_moving_averageTF.setText("false");
		// Path detail setting
		fine_tune_checkpointTF.setText(fine_tune_checkpoint);
		input_pathTF.setText(input_path);
		label_map_pathTF.setText(label_map_path);
		num_examplesTF.setText("154");
		eval_input_pathTF.setText(eval_input_path);
		eval_label_map_pathTF.setText(eval_label_map_path);

	}

	private void loaddata() {

		// get data from sshclient
		String temp = sshclient.sendCommand("cd /home/" + Model.username + "/tensorflowGUI/scripts "
				+ "&& bash loadconfig_faster_rcnn_inception_v2.sh " + Model.ActivatedEnv);

		// convert data into array
		configdata = new String[35];
		configdata = temp.split(" ");
		configdata[0] = configdata[0].substring(3);
		configdata[34] = configdata[34].substring(0, configdata[34].length() - 4);

		// Num of class setting
		if (RightUpperView.step1finished) {
			numofClassTF.setText(Integer.toString(Model.numofclass));
		} else {
			numofClassTF.setText(configdata[0]);
		}
		num_stepsTF.setText(configdata[1]);
		// Image_resizer
		min_dimensionTF.setText(configdata[2]);
		max_dimensionTF.setText(configdata[3]);
		// Feature_extractor
		feature_extractorCB.setSelectedItem(configdata[4]);
		first_stage_features_strideTF.setText(configdata[5]);
		// First Stage Setting
		first_stage_nms_score_thresholdTF.setText(configdata[6]);
		first_stage_nms_iou_thresholdTF.setText(configdata[7]);
		first_stage_max_proposalsTF.setText(configdata[8]);
		first_stage_localization_loss_weightTF.setText(configdata[9]);
		first_stage_objectness_loss_weightTF.setText(configdata[10]);
		initial_crop_sizeTF.setText(configdata[11]);
		maxpool_kernel_sizeTF.setText(configdata[12]);
		maxpool_strideTF.setText(configdata[13]);
		// Second Stage Setting
		score_thresholdTF.setText(configdata[14]);
		iou_thresholdTF.setText(configdata[15]);
		max_detections_per_classTF.setText(configdata[16]);
		max_total_detectionsTF.setText(configdata[17]);
		localization_loss_weightTF.setText(configdata[18]);
		classification_loss_weightTF.setText(configdata[19]);
		// Train_config setting
		batch_sizeTF.setText(configdata[20]);
		initial_learning_rateTF.setText(configdata[21]);
		schedule1_stepTF.setText(configdata[22]);
		schedule1_learining_rateTF.setText(configdata[23]);
		schedule2_stepTF.setText(configdata[24]);
		schedule2_learining_rateTF.setText(configdata[25]);
		momentum_optimizer_valueTF.setText(configdata[26]);
		use_moving_averageTF.setText(configdata[27]);
		// Path detail setting
		fine_tune_checkpointTF.setText(configdata[28]);
		input_pathTF.setText(configdata[29]);
		label_map_pathTF.setText(configdata[30]);
		metrics_setCB.setSelectedItem(configdata[31]);
		num_examplesTF.setText(configdata[32]);
		eval_input_pathTF.setText(configdata[33]);
		eval_label_map_pathTF.setText(configdata[34]);

	}

	private void loaddata2() {
		// get config text
		String temp = sshclient.sendCommand("cd /home/" + Model.username + "/tensorflowGUI/" + Model.ActivatedEnv
				+ "/models/research/object_detection/training/" + " && cat " + Model.selectedModel + ".config ");

		// set text on view
		contentsTA.setText(temp);
	}

	private void exportdata() {
		// Num of class setting
		configdata[0] = numofClassTF.getText();
		Model.numofclass = Integer.parseInt(configdata[0]);
		configdata[1] = num_stepsTF.getText();
		// Image_resizer
		configdata[2] = min_dimensionTF.getText();
		configdata[3] = max_dimensionTF.getText();
		// Feature_extractor
		configdata[4] = feature_extractorCB.getSelectedItem().toString();
		configdata[5] = first_stage_features_strideTF.getText();
		// First Stage Setting
		configdata[6] = first_stage_nms_score_thresholdTF.getText();
		configdata[7] = first_stage_nms_iou_thresholdTF.getText();
		configdata[8] = first_stage_max_proposalsTF.getText();
		configdata[9] = first_stage_localization_loss_weightTF.getText();
		configdata[10] = first_stage_objectness_loss_weightTF.getText();
		configdata[11] = initial_crop_sizeTF.getText();
		configdata[12] = maxpool_kernel_sizeTF.getText();
		configdata[13] = maxpool_strideTF.getText();
		// Second Stage Setting
		configdata[14] = score_thresholdTF.getText();
		configdata[15] = iou_thresholdTF.getText();
		configdata[16] = max_detections_per_classTF.getText();
		configdata[17] = max_total_detectionsTF.getText();
		configdata[18] = localization_loss_weightTF.getText();
		configdata[19] = classification_loss_weightTF.getText();
		// Train_config setting
		configdata[20] = batch_sizeTF.getText();
		configdata[21] = initial_learning_rateTF.getText();
		configdata[22] = schedule1_stepTF.getText();
		configdata[23] = schedule1_learining_rateTF.getText();
		configdata[24] = schedule2_stepTF.getText();
		configdata[25] = schedule2_learining_rateTF.getText();
		configdata[26] = momentum_optimizer_valueTF.getText();
		configdata[27] = use_moving_averageTF.getText();
		// Path detail setting
		configdata[28] = fine_tune_checkpointTF.getText();
		configdata[29] = input_pathTF.getText();
		configdata[30] = label_map_pathTF.getText();
		configdata[31] = metrics_setCB.getSelectedItem().toString();
		Model.metrics_set = configdata[31];
		configdata[32] = num_examplesTF.getText();
		configdata[33] = eval_input_pathTF.getText();
		configdata[34] = eval_label_map_pathTF.getText();

		String parameters = "";
		for (int i = 0; i < configdata.length; i++) {
			parameters = parameters + configdata[i] + " ";
		}

		sshclient.sendCommand("cd /home/" + Model.username + "/tensorflowGUI/scripts " + "&& bash setconfig_faster_rcnn_inception_v2.sh "
				+ Model.ActivatedEnv + " " + parameters);

	}

	private void exportdata2() {
		// get string from view
		String temp = contentsTA.getText();
		temp = "\"" + temp + "\"";
		
		// export data to config
		sshclient.sendCommand("cd /home/" + Model.username + "/tensorflowGUI/scripts" + "&& bash setconfig_others.sh "
				+ Model.ActivatedEnv + " " + Model.selectedModel + ".config " + temp);
	}
}
