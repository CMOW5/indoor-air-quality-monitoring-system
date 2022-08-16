# These resources handle networking and communication to and from the internet outside of the VPC. 
# The internet gateway is what allows communication between the VPC and the internet at all. 
# The NAT gateway allows resources within the VPC to communicate with the internet but will prevent communication 
# to the VPC from outside sources. 
# That is all tied together with the route table association, where the private route table that includes 
# the NAT gateway is added to the private subnets defined earlier. 
# Security groups will need to be added next to allow or reject traffic in a more fine-grained way both from the load balancer 
# and the application service.

# Create Internet Gateway and attach it to VPC
resource "aws_internet_gateway" "gateway" {
  vpc_id = aws_vpc.main.id

  tags = {
    Name = "${var.project}-${var.service}-igw"
  }
}

# Route table for Public Subnet's
resource "aws_route_table" "public_route_table" {    # Creating RT for Public Subnet
    vpc_id =  aws_vpc.main.id
    route {
        cidr_block = "0.0.0.0/0"  # Traffic from Public Subnet reaches Internet via Internet Gateway
        gateway_id = aws_internet_gateway.gateway.id
    }

    tags = {
        Name = "${var.project}-${var.service}-public-route-table"
    }
}

# Route table Association with Public Subnet's
resource "aws_route_table_association" "public_route_table_association" {
    subnet_id = aws_subnet.public.id
    route_table_id = aws_route_table.public_route_table.id
}

# Route table for Private Subnet's
/*
resource "aws_route_table" "private_route_table" {    # Creating RT for Private Subnet
    vpc_id = aws_vpc.main.id
    route {
        #cidr_block = "0.0.0.0/0" # Traffic from Private Subnet reaches Internet via NAT Gateway
        #nat_gateway_id = aws_nat_gateway.NATgw.id
    }

    tags = {
        Name = "${var.project}-${var.service}-private-route-table"
    }
}

# Route table Association with Private Subnet's
resource "aws_route_table_association" "private_route_table_association" {
    subnet_id = aws_subnet.private.id
    route_table_id = aws_route_table.private_route_table.id
}
*/

// NAT Gateway (we dont need it at the moment)
/*
resource "aws_eip" "nateIP" {
    vpc   = true
}

# Creating the NAT Gateway using subnet_id and allocation_id
resource "aws_nat_gateway" "NATgw" {
    allocation_id = aws_eip.nateIP.id
    subnet_id = aws_subnet.public.id
}
*/


