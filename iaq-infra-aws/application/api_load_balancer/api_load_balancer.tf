resource "aws_security_group" "alb" {
  name        = "${var.project}-alb-security-group"
  description = "application load balancer security group"
  vpc_id      = "${var.main_vpc_id}"

  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = "${var.allowed_cidr_blocks}"
  }

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = "${var.allowed_cidr_blocks}"
  }

  # Allow all outbound traffic.
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_alb" "alb" {
  name            = "${var.project}-alb"
  security_groups = ["${aws_security_group.alb.id}"]
  //subnets         = ["${aws_subnet.main.*.id}"]
  subnets         = "${var.main_subnets}"
}


# target groups
resource "aws_alb_target_group" "https_443" {
  name_prefix = "${var.project}-tg"
  port     = 8080
  protocol = "HTTPS"
  vpc_id   = "${var.main_vpc_id}"
  target_type = "ip"

  # Alter the destination of the health check to be the login page.
  
  health_check {
    path = "/health"
    port = 8080
    protocol = "HTTPS"
  }

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_alb_listener" "listener_http" {
  load_balancer_arn = "${aws_alb.alb.arn}"
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type = "redirect"

    redirect {
      port = "443"
      protocol = "HTTPS"
      status_code = "HTTP_301"
    }
  }
}
resource "aws_alb_listener" "listener_https" {
  load_balancer_arn = "${aws_alb.alb.arn}"
  port              = "443"
  protocol          = "HTTPS"
  ssl_policy        = "ELBSecurityPolicy-2016-08"
  certificate_arn   = "arn:aws:acm:us-east-1:354562611480:certificate/3d6ab075-4187-42f8-a44e-af36d5e00d17"
  default_action {
    target_group_arn = "${aws_alb_target_group.https_443.arn}"
    type             = "forward"
  }
}