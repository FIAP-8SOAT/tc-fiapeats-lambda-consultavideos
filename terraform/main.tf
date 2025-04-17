terraform {
  backend "s3" {
    bucket         = "terraform-fiapeats-videos" # Substitua pelo nome do bucket
    key            = "state/fiapeats-lambda-consultavideos/terraform.tfstate"         # Caminho do estado no bucket
    region         = "us-east-1"                       # Região do bucket
    encrypt        = true                              # Criptografia no bucket
  }
}

provider "aws" {
  region = "us-east-1"
}

data "aws_vpc" "default" {
  default = true
}

data "aws_subnets" "default" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.default.id]
  }
}

resource "aws_security_group" "lambda_sg" {
  name   = "lambda_sg"
  vpc_id = data.aws_vpc.default.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_iam_role" "lambda_exec_role" {
  name = "lambda_exec_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = {
        Service = "lambda.amazonaws.com"
      }
    }]
  })
}

resource "aws_iam_role_policy_attachment" "lambda_vpc_access" {
  role       = aws_iam_role.lambda_exec_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaVPCAccessExecutionRole"
}

resource "aws_iam_role_policy_attachment" "lambda_basic_logs" {
  role       = aws_iam_role.lambda_exec_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

resource "aws_lambda_function" "consultavideos" {
  function_name = "fiapeats-lambda-consultavideos"
  role          = aws_iam_role.lambda_exec_role.arn
  handler       = "br.com.fiap.consultavideos.App::handleRequest"
  runtime       = "java17"
  filename      = "${path.module}/../build/function.zip"
  source_code_hash = filebase64sha256("${path.module}/../build/function.zip")
  timeout       = 15
  memory_size   = 512

  vpc_config {
    subnet_ids         = data.aws_subnets.default.ids
    security_group_ids = [aws_security_group.lambda_sg.id]
  }
}