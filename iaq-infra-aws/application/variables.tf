
variable "project" {}

variable "region" {}

variable "main_vpc_id" {}

variable "main_subnets" {
    type = list(string)
    // ["${aws_subnet.main.*.id}"]
}

variable "allowed_cidr_blocks" {
    type = list(string)
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