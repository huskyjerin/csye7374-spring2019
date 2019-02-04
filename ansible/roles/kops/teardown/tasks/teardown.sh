export NAME=$2

export KOPS_STATE_STORE=s3://$1

kops delete cluster --name $NAME --yes
