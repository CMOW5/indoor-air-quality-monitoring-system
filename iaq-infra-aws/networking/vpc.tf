
resource "aws_vpc" "main" {                
    cidr_block       = var.main_vpc_cidr     
    instance_tenancy = "default"
}

# Create Internet Gateway and attach it to VPC
resource "aws_internet_gateway" "internet_gateway" {    
    vpc_id =  aws_vpc.main.id            
}



// NAT Gateway (we dont need it at the moment)

/*
resource "aws_eip" "nateIP" {
    vpc   = true
}

# Creating the NAT Gateway using subnet_id and allocation_id
resource "aws_nat_gateway" "NATgw" {
    allocation_id = aws_eip.nateIP.id
    subnet_id = aws_subnet.public_subnet.id
}
*/