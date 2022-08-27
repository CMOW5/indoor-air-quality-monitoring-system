output "vpc_id" {
    value = aws_vpc.main.id
}

output "subnet_public_a" {
    value = aws_subnet.public.id
}

output "subnet_private_a" {
    value = aws_subnet.private.id
}