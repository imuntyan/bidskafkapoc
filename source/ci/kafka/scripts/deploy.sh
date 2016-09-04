#!/bin/sh

cd resource-bidskafkapoc/source/ci/kafka

mkdir credentials

echo "$k8s_cluster_ca" | base64 -d >> credentials/ca.pem
echo "$k8s_admin_cert" | base64 -d >> credentials/admin.pem
echo "$k8s_admin_key" | base64 -d >> credentials/admin-key.pem


kubectl --kubeconfig=kubeconfig apply -f scripts/zookeeper-redundancy.yml
kubectl --kubeconfig=kubeconfig apply -f scripts/zookeeper-services.yml
kubectl --kubeconfig=kubeconfig apply -f scripts/kafka-service.yml

sleep 5

rm -f proxy.log proxy.pid
touch proxy.log
nohup kubectl --kubeconfig=kubeconfig proxy > proxy.log 2>&1 & echo $! > proxy.pid
# wait for proxy to start
while ! grep "8001" -q proxy.log
do sleep 0.1; done
URL=http://127.0.0.1:8001/api/v1/namespaces/default/services/kafka-service
public_ip=`curl -s $URL 2>&1 | grep '\"hostname\"' | awk '{print $2}' | awk -F\" '{print $2}'`
kill `cat proxy.pid`
echo $public_ip


kubectl --kubeconfig=kubeconfig apply -f scripts/zookeeper-cluster.yml

sed -e "s#\${KAFKA_ADVERTISED_HOST_NAME}#$public_ip#" \
  scripts/kafka-cluster.yml | kubectl --kubeconfig=kubeconfig apply -f -
