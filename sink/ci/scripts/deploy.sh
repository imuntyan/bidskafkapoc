#!/bin/sh

echo "$k8s_ip $k8s_dns_name"

cd resource-bidskafkapoc/sink/ci/scripts/kubernetes

mkdir credentials

echo "$k8s_cluster_ca" | base64 -d >> credentials/ca.pem
echo "$k8s_admin_cert" | base64 -d >> credentials/admin.pem
echo "$k8s_admin_key" | base64 -d >> credentials/admin-key.pem

sed -e "s#\${APP_NAME}#$app_name#" \
  -e "s#\${APP_PORT}#$app_port#" \
  service/service.yml | kubectl --kubeconfig=kubeconfig apply -f -

sed -e "s#\${DEPLOY_IMAGE_NAME}#$deploy_image_name#" \
  -e "s#\${APP_NAME}#$app_name#"  \
  -e "s#\${APP_PORT}#$app_port#" \
  service/cluster.yml | kubectl --kubeconfig=kubeconfig apply -f -
