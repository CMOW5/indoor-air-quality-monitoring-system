provider "aws" {
  region = "us-east-1"
}

terraform {
  backend "s3" {
    bucket = "cmow5-iaq-terraform-state-files"
    key = "application-state.tf"
    region = "us-east-1"
  }
}