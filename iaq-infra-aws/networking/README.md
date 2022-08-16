terraform init
terraform plan -var-file=variables/networking.tfvars
terraform apply -var-file=variables/networking.tfvars
terraform destroy
