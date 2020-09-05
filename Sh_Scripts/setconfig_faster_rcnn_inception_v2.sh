#! /bin/bash
# made by Heemoon Yoon in 2019
# UNIVERSITY OF TASMANIA
# Script for setting config files of tensorflow

# Parameter check
if [ -z "${36}" ]; then
	echo "parameter error"
	exit
fi

# Delete existing lines in config file
`sed -i '1,$d' ~/tensorflowGUI/$1/models/research/object_detection/training/faster_rcnn_inception_v2.config`

# Rewrite config file

echo "# Faster R-CNN with Inception v2, configured for Oxford-IIIT Pets Dataset." >> ~/tensorflowGUI/$1/models/research/object_detection/training/faster_rcnn_inception_v2.config

echo "# Users should configure the fine_tune_checkpoint field in the train config as" >> ~/tensorflowGUI/$1/models/research/object_detection/training/faster_rcnn_inception_v2.config
echo "# well as the label_map_path and input_path fields in the train_input_reader and" >> ~/tensorflowGUI/$1/models/research/object_detection/training/faster_rcnn_inception_v2.config
echo "# eval_input_reader. Search for \"PATH_TO_BE_CONFIGURED\" to find the fields that" >> ~/tensorflowGUI/$1/models/research/object_detection/training/faster_rcnn_inception_v2.config
echo "# should be configured." >> ~/tensorflowGUI/$1/models/research/object_detection/training/faster_rcnn_inception_v2.config
echo "" >> ~/tensorflowGUI/$1/models/research/object_detection/training/faster_rcnn_inception_v2.config

echo "model {
   faster_rcnn {
    num_classes: $2
    image_resizer {
      keep_aspect_ratio_resizer {
        min_dimension: $4
        max_dimension: $5
      }
    }
    feature_extractor {
      type: '$6'
      first_stage_features_stride: $7
    }
    first_stage_anchor_generator {
      grid_anchor_generator {
        scales: [0.25, 0.5, 1.0, 2.0]
        aspect_ratios: [0.5, 1.0, 2.0]
        height_stride: 16
        width_stride: 16
      }
    }
    first_stage_box_predictor_conv_hyperparams {
      op: CONV
      regularizer {
        l2_regularizer {
          weight: 0.0
        }
      }
      initializer {
        truncated_normal_initializer {
          stddev: 0.01
        }
      }
    }
    first_stage_nms_score_threshold: $8
    first_stage_nms_iou_threshold: $9
    first_stage_max_proposals: ${10}
    first_stage_localization_loss_weight: ${11}
    first_stage_objectness_loss_weight: ${12}
    initial_crop_size: ${13}
    maxpool_kernel_size: ${14}
    maxpool_stride: ${15}
    second_stage_box_predictor {
      mask_rcnn_box_predictor {
        use_dropout: false
        dropout_keep_probability: 1.0
        fc_hyperparams {
          op: FC
          regularizer {
            l2_regularizer {
              weight: 0.0
            }
          }
          initializer {
            variance_scaling_initializer {
              factor: 1.0
              uniform: true
              mode: FAN_AVG
            }
          }
        }
      }
    }
    second_stage_post_processing {
      batch_non_max_suppression {
        score_threshold: ${16}
        iou_threshold: ${17}
        max_detections_per_class: ${18}
        max_total_detections: ${19}
      }
      score_converter: SOFTMAX
    }
    second_stage_localization_loss_weight: ${20}
    second_stage_classification_loss_weight: ${21}
  }
}

train_config: {
  batch_size: ${22}
  optimizer {
    momentum_optimizer: {
      learning_rate: {
        manual_step_learning_rate {
          initial_learning_rate: ${23}
          schedule {
            step: ${24}
            learning_rate: ${25}
          }
          schedule {
            step: ${26}
            learning_rate: ${27}
          }
        }
      }
      momentum_optimizer_value: ${28}
    }
    use_moving_average: ${29}
  }
  gradient_clipping_by_norm: 10.0
  fine_tune_checkpoint: \"${30}\"
  from_detection_checkpoint: true
  load_all_detection_checkpoint_vars: true
  # Note: The below line limits the training process to 200K steps, which we
  # empirically found to be sufficient enough to train the pets dataset. This
  # effectively bypasses the learning rate schedule (the learning rate will
  # never decay). Remove the below line to train indefinitely.
  num_steps: $3
  data_augmentation_options {
    random_horizontal_flip {
    }
  }
}


train_input_reader: {
  tf_record_input_reader {
    input_path: \"${31}\"
  }
  label_map_path: \"${32}\"
}

eval_config: {
  metrics_set: \"${33}\"
  num_examples: ${34}
}

eval_input_reader: {
  tf_record_input_reader {
    input_path: \"${35}\"
  }
  label_map_path: \"${36}\"
  shuffle: false
  num_readers: 1
}

###${2} ${3} ${4} ${5} ${6} ${7} ${8} ${9} ${10} ${11} ${12} ${13} ${14} ${15} ${16} ${17} ${18} ${19} ${20} ${21} ${22} ${23} ${24} ${25} ${26} ${27} ${28} ${29} ${30} ${31} ${32} ${33} ${34} ${35} ${36}#@#" >> ~/tensorflowGUI/$1/models/research/object_detection/training/faster_rcnn_inception_v2.config

