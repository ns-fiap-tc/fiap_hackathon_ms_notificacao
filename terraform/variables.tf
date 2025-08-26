# AWS provider configuration
variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "us-east-1"
}

#Variaveis DockerHUB

variable "dockerhub_username" {
  description = "The username of the dockerhub image to deploy"
  type        = string
}

/*variable "dockerhub_token" {
  description = "The access token of the dockerhub image to deploy"
  type        = string
}*/