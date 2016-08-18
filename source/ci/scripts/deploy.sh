#!/bin/sh


cd resource-bidskafkapoc/source/ci/scripts/kubernetes

mkdir credentials

base64 --decode --wrap=0 '$k8s_cluster_ca' >> credentials/ca.pem
base64 --decode --wrap=0 '$k8s_admin_cert' >> credentials/admin.pem
base64 --decode --wrap=0 '$k8s_admin_key' >> credentials/admin-key.pem

ls -al


dig k8s.kubernetes.net

kubectl --kubeconfig=kubeconfig get nodes
