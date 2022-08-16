/*
data "terraform_remote_state" "networking" {
    backend = "s3"
    config = {
        bucket = "cmow5-iaq-terraform-state-files"
        key = "networking-state.tf"
        region = "us-east-1"
    }
}
// then you can access data.terraform_remote_state.networking.outputs.vpc_id
*/