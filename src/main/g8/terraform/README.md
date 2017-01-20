# Setup Remote State (first run):

We store da `tfstate` on s3 to mitigate merge conflicts and to not
have the pesky environment variables make their way back into git.

Setup your access to aws, and run:

```bash
./init_tfstate.sh
```


# Apply terraform

Set up credstash and...

```bash
. creds
terraform plan
terraform apply
```
