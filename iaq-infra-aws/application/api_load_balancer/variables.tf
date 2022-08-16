
variable "project" {}

variable "region" {}

variable "main_vpc_id" {}

variable "main_subnets" {
    type = list(string)
}

variable "allowed_cidr_blocks" {
    type = list(string)
}