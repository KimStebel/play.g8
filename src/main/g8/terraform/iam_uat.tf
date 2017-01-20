resource "aws_iam_instance_profile" "$name$-uat" {
  name  = "$name$-uat"
  roles = ["\${aws_iam_role.$name$-uat.name}"]
}

resource "aws_iam_role" "$name$-uat" {
  name = "$name$-uat"

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

resource "aws_iam_role_policy_attachment" "ebs-web-attach-uat" {
  role       = "\${aws_iam_role.$name$-uat.name}"
  policy_arn = "arn:aws:iam::aws:policy/AWSElasticBeanstalkWebTier"
}

resource "aws_iam_role_policy_attachment" "ebs-worker-attach-uat" {
  role       = "\${aws_iam_role.$name$-uat.name}"
  policy_arn = "arn:aws:iam::aws:policy/AWSElasticBeanstalkWorkerTier"
}

resource "aws_iam_role_policy_attachment" "ecr-attach-uat" {
  role       = "\${aws_iam_role.$name$-uat.name}"
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
}

resource "aws_iam_policy" "dynamo-uat" {
  name        = "$name$-uat-dynamo"
  path        = "/"
  description = "Grant access to dynamo payment-tokens"

  policy = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "Stmt1470234312000",
            "Effect": "Allow",
            "Action": [
                "dynamodb:DeleteItem",
                "dynamodb:DescribeTable",
                "dynamodb:GetItem",
                "dynamodb:PutItem",
                "dynamodb:Query",
                "dynamodb:Scan",
                "dynamodb:UpdateItem"
            ],
            "Resource": [
                "*"
            ]
        }
    ]
}
EOF
}

resource "aws_iam_policy_attachment" "dynamo-attach-uat" {
  name       = "dynamo-uat-attach"
  roles      = ["\${aws_iam_role.$name$-uat.name}"]
  policy_arn = "\${aws_iam_policy.dynamo-uat.arn}"
}