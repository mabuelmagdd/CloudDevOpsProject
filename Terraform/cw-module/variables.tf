##### SNS #####
variable "sns_topic_name" {
  type = string
}
variable "sns_topic_target" {
  type = string
}
variable "sns_target_protocol" {
  type = string
}
##### Cloud Watch #####
variable "cw_alarm_name" {
  type = string
}
variable "cw_comparison_operator" {
  type = string
}
variable "cw_evaluation_periods" {
  type = number
}
variable "cw_metric_name" {
  type = string
}
variable "cw_namespace" {
  type = string
}
variable "cw_period" {
  type = number
}
variable "cw_statistic" {
  type = string
}
variable "cw_threshold" {
  type = number
}
variable "cw_alarm_description" {
  type = string
}
variable "instance_id" {
  description = "The ID of the EC2 instance to monitor."
  type        = string
}
