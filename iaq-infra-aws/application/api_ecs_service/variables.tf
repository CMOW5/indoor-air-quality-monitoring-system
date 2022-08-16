
variable "project" {}

variable "region" {}

variable "main_vpc_id" {}

variable "main_subnets" {
    type = list(string)
}

variable "load_balancer_security_group_id" {

}

variable "load_balancer_target_group_id" {

}

variable "load_balancer_listener_http" {
    
}

variable "desired_count" {

}

variable "cpu" {

}

variable "memory" {
    
}

variable "container_port" {

}

variable "docker_image_id" {
    
}