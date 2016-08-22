#!/bin/sh

echo "$k8s_ip $k8s_dns_name"

cd resource-bidskafkapoc/source/ci/scripts/kubernetes

mkdir credentials

echo "$k8s_cluster_ca" | base64 -d >> credentials/ca.pem
echo "$k8s_admin_cert" | base64 -d >> credentials/admin.pem
echo "$k8s_admin_key" | base64 -d >> credentials/admin-key.pem

export DEPLOY_IMAGE_NAME="$deploy_image_name"

echo $DEPLOY_IMAGE_NAME

kubectl config set DEPLOY_IMAGE_NAME "$deploy_image_name"

kubectl --kubeconfig=kubeconfig apply -f service/service.yml

kubectl --kubeconfig=kubeconfig apply -f service/cluster.yml
