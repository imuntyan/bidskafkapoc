#!/bin/sh

cd resource-bidskafkapoc/source/ci/kafka-manager

mkdir credentials

echo "$k8s_cluster_ca" | base64 -d >> credentials/ca.pem
echo "$k8s_admin_cert" | base64 -d >> credentials/admin.pem
echo "$k8s_admin_key" | base64 -d >> credentials/admin-key.pem

kubectl --kubeconfig=kubeconfig apply -f scripts/kafka-manager-service.yml
kubectl --kubeconfig=kubeconfig apply -f scripts/kafka-manager-cluster.yml
