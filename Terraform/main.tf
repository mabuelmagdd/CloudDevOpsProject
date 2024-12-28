module "vpc" {
  source  = "./vpc-module"
  vpc_cidr    = var.vpc_cidr
  subnet_cidr = var.subnet_cidr
}

module "ec2" {
  depends_on   = [module.vpc]
  source       = "./ec2-module"
  subnet_id    = module.vpc.public_subnet_id
  instance_type = var.instance_type
  key_name      = var.key_name
  vpc_id        = module.vpc.vpc_id
  EBS_size      = var.EBS_size
}


module "cw" {
  depends_on = [module.ec2]
  source     = "./cw-module"

  cw_alarm_name          = var.cw_alarm_name
  cw_comparison_operator = var.cw_comparison_operator
  cw_evaluation_periods  = var.cw_evaluation_periods
  cw_metric_name         = var.cw_metric_name
  cw_namespace           = var.cw_namespace
  cw_period              = var.cw_period
  cw_statistic           = var.cw_statistic
  cw_threshold           = var.cw_threshold
  cw_alarm_description   = var.cw_alarm_description

  sns_topic_name        = var.sns_topic_name
  sns_target_protocol   = var.sns_target_protocol
  sns_topic_target      = var.sns_topic_target

  instance_id = module.ec2.instance_id
}


