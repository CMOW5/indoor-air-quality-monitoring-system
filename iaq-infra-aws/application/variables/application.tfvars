project = "iaq" 

region  = "us-east-1"

main_vpc_id = "vpc-0dc11f9ed2d390a22"

main_subnets = ["subnet-0d96dd227ef322974", "subnet-05a6beab125fecc1c", "subnet-0af01433a900d484b", 
                "subnet-0fca9915553fdc5da", "subnet-00df2ffc8d5dc464e", "subnet-0ab4ba3a268ba59a5"]

allowed_cidr_blocks = ["0.0.0.0/0"]

desired_count = 1

# https://docs.aws.amazon.com/AmazonECS/latest/developerguide/task-cpu-memory-error.html
cpu = 512

memory = 1024

container_port = 8080 

docker_image_id = "354562611480.dkr.ecr.us-east-1.amazonaws.com/iaq-api" 