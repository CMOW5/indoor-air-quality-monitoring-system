
variable "project" {
    description = "the project name (iaq)"
    type = string
}

variable "region" {
    description = "the aws region"
    default = "us-east-1"
    type = string
}

variable "main_vpc_id" {
    description = "the vpc in AWS in which the resources will run"
    type = string
}

variable "main_subnets" {
    description = "the subnets within the provided VPC"
    type = list(string)
    // ["${aws_subnet.main.*.id}"]
}

variable "allowed_cidr_blocks" {
    description = "allowed cidr blocks for the API load balancer"
    type = list(string)
}

variable "desired_count" {
    description = "how many API instances we want running"
    type = number
}

variable "cpu" {
    description = "the cpu size to use for the API instances. see https://docs.aws.amazon.com/AmazonECS/latest/developerguide/task-cpu-memory-error.html"
    type = number
}

variable "memory" {
    description = "the memory size to use for the API instances. see https://docs.aws.amazon.com/AmazonECS/latest/developerguide/task-cpu-memory-error.html"
    type = number
}

variable "container_port" {
    description = "the docker container port"
    type = number
}

variable "docker_image_id" {
    description = "the docker image url (or ARN in case we are using ECR)"
    type = string
}