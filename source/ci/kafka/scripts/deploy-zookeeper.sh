#!/bin/sh

cd resource-bidskafkapoc/source/ci/kafka

mkdir credentials

echo "$k8s_cluster_ca" | base64 -d >> credentials/ca.pem
echo "$k8s_admin_cert" | base64 -d >> credentials/admin.pem
echo "$k8s_admin_key" | base64 -d >> credentials/admin-key.pem


kubectl --kubeconfig=kubeconfig apply -f scripts/zookeeper-redundancy.yml
kubectl --kubeconfig=kubeconfig apply -f scripts/zookeeper-services.yml
kubectl --kubeconfig=kubeconfig apply -f scripts/zookeeper-cluster.yml
