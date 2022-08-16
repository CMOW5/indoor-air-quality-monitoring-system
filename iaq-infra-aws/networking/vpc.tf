
resource "aws_vpc" "main" {                
    cidr_block       = var.main_vpc_cidr     
    instance_tenancy = "default"
    tags = {
        Name = "${var.project}-${var.service}-vpc"
    }
}