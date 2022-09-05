

module "api_load_balancer" {
  source = "./api_load_balancer"
  project = var.project  
  region = var.region
  main_vpc_id = var.main_vpc_id
  main_subnets = var.main_subnets
  allowed_cidr_blocks = var.allowed_cidr_blocks
}


module "api_ecs_service" {
  source = "./api_ecs_service"
  project = var.project  
  region = var.region
  main_vpc_id = var.main_vpc_id
  main_subnets = var.main_subnets
  load_balancer_security_group_id = module.api_load_balancer.load_balancer_security_group_id
  load_balancer_target_group_https_id = module.api_load_balancer.load_balancer_target_group_https_443_id
  load_balancer_listener_http = module.api_load_balancer.load_balancer_listener_http
  load_balancer_listener_https = module.api_load_balancer.load_balancer_listener_https
  desired_count = var.desired_count
  cpu = var.cpu
  memory = var.memory
  container_port = var.container_port
  docker_image_id = var.docker_image_id
}


