variable "vpc_cidr" {
  default = "10.0.0.0/16"
}

variable "subnet_cidr" {
  default = "10.0.1.0/24"
}

variable "instance_type" {
  default = "t3.xlarge"
}

variable "EBS_size" {
  default = "20"
}
variable "key_name" {
  description = "The name of the key pair to use"
}
# CloudWatch Alarm Variables
variable "cw_alarm_name" {
  description = "Name of the CloudWatch alarm."
  type        = string
}

variable "cw_comparison_operator" {
  description = "Comparison operator for the CloudWatch alarm (e.g., GreaterThanThreshold, LessThanThreshold)."
  type        = string
}

variable "cw_evaluation_periods" {
  description = "Number of periods for evaluating the metric."
  type        = number
}

variable "cw_metric_name" {
  description = "Metric name to monitor (e.g., CPUUtilization)."
  type        = string
}

variable "cw_namespace" {
  description = "Namespace of the metric (e.g., AWS/EC2)."
  type        = string
}

variable "cw_period" {
  description = "Evaluation period for the metric in seconds."
  type        = number
}

variable "cw_statistic" {
  description = "Statistic for the metric (e.g., Average, Sum, Maximum)."
  type        = string
}

variable "cw_threshold" {
  description = "Threshold value for triggering the alarm."
  type        = number
}

variable "cw_alarm_description" {
  description = "Description of the CloudWatch alarm."
  type        = string
}

# SNS Topic Variables
variable "sns_topic_name" {
  description = "Name of the SNS topic for notifications."
  type        = string
}

variable "sns_target_protocol" {
  description = "Protocol for the SNS subscription (e.g., email, https)."
  type        = string
}

variable "sns_topic_target" {
  description = "Target endpoint for the SNS subscription (e.g., email address or URL)."
  type        = string
}

