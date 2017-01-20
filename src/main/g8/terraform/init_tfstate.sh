#!/bin/bash

echo "==> Setting up remote tfstate"

rm -rf ./.terraform

terraform remote config \
  -backend=s3 \
  -backend-config="bucket=bosp" \
  -backend-config="key=tfstate/$name$" \
  -backend-config="region=eu-west-1"

terraform refresh

