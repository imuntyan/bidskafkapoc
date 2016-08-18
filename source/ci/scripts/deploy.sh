#!/bin/sh

echo "$k8s_ip $k8s_dns_name"

cd resource-bidskafkapoc/source/ci/scripts/kubernetes

mkdir credentials

echo "$k8s_cluster_ca" | base64 -d >> credentials/ca.pem
echo "$k8s_admin_cert" | base64 -d >> credentials/admin.pem
echo "$k8s_admin_key" | base64 -d >> credentials/admin-key.pem

ls -al

echo "$k8s_cluster_ca"

cat credentials/ca.pem

kubectl --kubeconfig=kubeconfig run my-source --image=imuntyan/newwave:latest --replicas=2 --port=80
