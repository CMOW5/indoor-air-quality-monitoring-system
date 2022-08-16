# Create a Public Subnet
resource "aws_subnet" "public_subnet" {
    vpc_id =  aws_vpc.main.id
    cidr_block = "${var.public_subnets}"
}

#Create a Private Subnet                   
resource "aws_subnet" "private_subnet" {
    vpc_id =  aws_vpc.main.id
    cidr_block = "${var.private_subnets}"  
}

# Route table for Public Subnet's
resource "aws_route_table" "public_route_table" {    # Creating RT for Public Subnet
vpc_id =  aws_vpc.main.id
        route {
            cidr_block = "0.0.0.0/0"               # Traffic from Public Subnet reaches Internet via Internet Gateway
            gateway_id = aws_internet_gateway.internet_gateway.id
    }
}

# Route table for Private Subnet's
resource "aws_route_table" "private_route_table" {    # Creating RT for Private Subnet
vpc_id = aws_vpc.main.id
    route {
        #cidr_block = "0.0.0.0/0"             # Traffic from Private Subnet reaches Internet via NAT Gateway
        #nat_gateway_id = aws_nat_gateway.NATgw.id
    }
}

# Route table Association with Public Subnet's
resource "aws_route_table_association" "public_route_table_association" {
    subnet_id = aws_subnet.public_subnet.id
    route_table_id = aws_route_table.public_route_table.id
}

# Route table Association with Private Subnet's
resource "aws_route_table_association" "private_route_table_association" {
    subnet_id = aws_subnet.private_subnet.id
    route_table_id = aws_route_table.private_route_table.id
}