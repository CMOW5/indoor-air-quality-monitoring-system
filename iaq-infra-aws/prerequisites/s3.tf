resource "aws_s3_bucket" "terraform" {
    bucket = "cmow5-iaq-terraform-state-files"
    acl = "private"
}