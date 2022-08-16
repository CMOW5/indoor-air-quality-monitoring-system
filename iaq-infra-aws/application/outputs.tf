output "load_balancer_ip" {
  value = aws_alb.alb.dns_name
}