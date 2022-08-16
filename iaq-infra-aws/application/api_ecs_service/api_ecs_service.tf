resource "aws_security_group" "iaq_api_task_definition_security_group" {
  name        = "${var.project}-task-definition-sg"
  vpc_id      = var.main_vpc_id

  ingress {
    protocol        = "tcp"
    from_port       = var.container_port
    to_port         = var.container_port
    # security_groups = [aws_security_group.alb.id]
    security_groups = [var.load_balancer_security_group_id]
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
  #execution_role_arn       = "${data.aws_iam_role.ecs_task_execution_role.arn}"
  execution_role_arn = "arn:aws:iam::354562611480:role/ecsTaskExecutionRole"
  container_definitions = <<DEFINITION
[
  {
    "image": "${var.docker_image_id}",
    "cpu": ${var.cpu},
    "memory": ${var.memory},
    "name":  "${var.project}-container-definition",
    "networkMode": "awsvpc",
    "portMappings": [
      {
        "containerPort": ${var.container_port},
        "hostPort": ${var.container_port}
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
  platform_version = "1.3.0"

  network_configuration {
    security_groups = [aws_security_group.iaq_api_task_definition_security_group.id]
    subnets         = var.main_subnets
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = var.load_balancer_target_group_id
    container_name   = "${var.project}-container-definition"
    container_port   = var.container_port
  }

  depends_on = [var.load_balancer_listener_http]
}