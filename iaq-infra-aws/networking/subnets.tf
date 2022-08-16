# Create a Public Subnet
resource "aws_subnet" "public" {
    vpc_id =  aws_vpc.main.id
    cidr_block = "${var.public_subnets_cidr}"
    # availability_zone = data.aws_availability_zones.available_zones.names[count.index]
    map_public_ip_on_launch = true
    tags = {
        Name = "${var.project}-${var.service}-public-subnet"
    }
}

#Create a Private Subnet                   
resource "aws_subnet" "private" {
    vpc_id =  aws_vpc.main.id
    cidr_block = "${var.private_subnets_cidr}" 
    # availability_zone = data.aws_availability_zones.available_zones.names[count.index] 

    tags = {
        Name = "${var.project}-${var.service}-private-subnet"
    }
}

