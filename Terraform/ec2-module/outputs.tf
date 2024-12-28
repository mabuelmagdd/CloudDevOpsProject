output "instance_id" {
  description = "The ID of the EC2 instance created."
  value       = aws_instance.main.id
}
