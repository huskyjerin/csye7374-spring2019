aws s3api create-bucket --bucket $1 --region us-east-1

aws s3api put-bucket-versioning --bucket $1 --versioning-configuration Status=Enabled

aws s3api put-bucket-encryption --bucket $1 --server-side-encryption-configuration '{"Rules":[{"ApplyServerSideEncryptionByDefault":{"SSEAlgorithm":"AES256"}}]}'

export NAME=$3


export KOPS_STATE_STORE=s3://$1


sudo kops create cluster --zones us-east-1a --node-count=$2 ${NAME} --state=s3://$1 

sleep 2m

sudo kops update cluster ${NAME} --yes --state=s3://$1

sleep 6m

sudo kops validate cluster --state=s3://$1
