terraform init
terraform plan -var-file=variables/application.tfvars
terraform apply -var-file=variables/application.tfvars
terraform destroy -var-file=variables/application.tfvars
