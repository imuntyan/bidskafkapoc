#!/bin/sh

cd resource-bidskafkapoc/source/ci/mongodb

whoami

pwd

ls -al

mkdir credentials

echo "$k8s_cluster_ca" | base64 -d >> credentials/ca.pem
echo "$k8s_admin_cert" | base64 -d >> credentials/admin.pem
echo "$k8s_admin_key" | base64 -d >> credentials/admin-key.pem


sed -e "s#\${n}#1#" \
  -e "s#\${volumeID}#$VOLUME_ID_1#" \
  scripts/mongodb-service.yml | kubectl --kubeconfig=kubeconfig apply -f -

sed -e "s#\${n}#2#" \
  -e "s#\${volumeID}#$VOLUME_ID_2#" \
  scripts/mongodb-service.yml | kubectl --kubeconfig=kubeconfig apply -f -

sed -e "s#\${n}#3#" \
  -e "s#\${volumeID}#$VOLUME_ID_3#" \
  scripts/mongodb-service.yml | kubectl --kubeconfig=kubeconfig apply -f -

sed -e "s#\${n}#1#" \
  -e "s#\${volumeID}#$VOLUME_ID_1#" \
  scripts/mongodb-cluster.yml | kubectl --kubeconfig=kubeconfig apply -f -

sed -e "s#\${n}#2#" \
  -e "s#\${volumeID}#$VOLUME_ID_2#" \
  scripts/mongodb-cluster.yml | kubectl --kubeconfig=kubeconfig apply -f -

sed -e "s#\${n}#3#" \
  -e "s#\${volumeID}#$VOLUME_ID_3#" \
  scripts/mongodb-cluster.yml | kubectl --kubeconfig=kubeconfig apply -f -

kubectl --kubeconfig=kubeconfig apply -f scripts/mongodb-redundancy.yml