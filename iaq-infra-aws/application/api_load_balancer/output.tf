output "load_balancer_ip" {
  value = aws_alb.alb.dns_name
}

output "load_balancer_security_group_id" {
  value = aws_security_group.alb.id
}

output "load_balancer_target_group_https_443_id" {
  value = aws_alb_target_group.https_443.id
}

output "load_balancer_listener_http" {
  value = aws_alb_listener.listener_http
}

output "load_balancer_listener_https" {
  value = aws_alb_listener.listener_https
}