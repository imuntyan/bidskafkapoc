#!/bin/sh


cd resource-bidskafkapoc/source/ci/scripts/kubernetes

mkdir credentials

echo '$k8s_cluster_ca' | base64 --decode --wrap=0  >> credentials/ca.pem
echo '$k8s_admin_cert' | base64 --decode --wrap=0  >> credentials/admin.pem
echo '$k8s_admin_key' | base64 --decode --wrap=0  >> credentials/admin-key.pem

ls -al


dig k8s.kubernetes.net

kubectl --kubeconfig=kubeconfig get nodes
