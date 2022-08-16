resource "aws_security_group" "iaq_api_task_definition_security_group" {
  name        = "${var.project}-task-definition-sg"
  vpc_id      = var.main_vpc_id

  ingress {
    protocol        = "tcp"
    from_port       = 8080
    to_port         = 8080
    security_groups = [aws_security_group.alb.id]
  }

  egress {
    protocol    = "-1"
    from_port   = 0
    to_port     = 0
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_ecs_task_definition" "iaq_api_task_definition" {
  family                   = "${var.project}-task-definition"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = var.cpu
  memory                   = var.memory

  container_definitions = <<DEFINITION
[
  {
    "image": "cmow5/iaq-api",
    "cpu": ${var.cpu},
    "memory": ${var.memory},
    "name":  "${var.project}-container-definition",
    "networkMode": "awsvpc",
    "portMappings": [
      {
        "containerPort": 8080,
        "hostPort": 8080
      }
    ]
  }
]
DEFINITION
}

# ECS cluster
resource "aws_ecs_cluster" "iaq_api_ecs_cluster" {
  name = "${var.project}-cluster"
}

# ECS service
resource "aws_ecs_service" "iaq_api_ecs_service" {
  name            = "${var.project}-service"
  cluster         = aws_ecs_cluster.iaq_api_ecs_cluster.id
  task_definition = aws_ecs_task_definition.iaq_api_task_definition.arn
  desired_count   = var.desired_count
  launch_type     = "FARGATE"

  network_configuration {
    security_groups = [aws_security_group.iaq_api_task_definition_security_group.id]
    subnets         = var.main_subnets
  }

  load_balancer {
    target_group_arn = aws_alb_target_group.group.id
    container_name   = "${var.project}-container-definition"
    container_port   = 8080
  }

  depends_on = [aws_alb_listener.listener_http]
}