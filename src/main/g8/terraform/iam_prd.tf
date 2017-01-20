resource "aws_iam_instance_profile" "$name$-prd" {
  name  = "$name$-prd"
  roles = ["\${aws_iam_role.$name$-prd.name}"]
}

resource "aws_iam_role" "$name$-prd" {
  name = "$name$-prd"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "ec2.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF
}

resource "aws_iam_role_policy_attachment" "ebs-web-attach-prd" {
  role       = "\${aws_iam_role.$name$-prd.name}"
  policy_arn = "arn:aws:iam::aws:policy/AWSElasticBeanstalkWebTier"
}

resource "aws_iam_role_policy_attachment" "ebs-worker-attach-prd" {
  role       = "\${aws_iam_role.$name$-prd.name}"
  policy_arn = "arn:aws:iam::aws:policy/AWSElasticBeanstalkWorkerTier"
}

resource "aws_iam_role_policy_attachment" "ecr-attach-prd" {
  role       = "\${aws_iam_role.$name$-prd.name}"
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
}
