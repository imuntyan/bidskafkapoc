#!/bin/sh

cd resource-bidskafkapoc/source/ci/kafka

mkdir credentials

echo "$k8s_cluster_ca" | base64 -d >> credentials/ca.pem
echo "$k8s_admin_cert" | base64 -d >> credentials/admin.pem
echo "$k8s_admin_key" | base64 -d >> credentials/admin-key.pem

sed -e "s#\${n}#1#" \
  scripts/kafka-service.yml | kubectl --kubeconfig=kubeconfig apply -f -
sed -e "s#\${n}#2#" \
  scripts/kafka-service.yml | kubectl --kubeconfig=kubeconfig apply -f -

sleep 10

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


if [ ! -z "$public_ip" ]
then
sed -e "s#\${KAFKA_ADVERTISED_HOST_NAME}#$public_ip#" \
  -e "s#\${n}#1#" \
  scripts/kafka-deployment.yml | kubectl --kubeconfig=kubeconfig apply -f -
sed -e "s#\${KAFKA_ADVERTISED_HOST_NAME}#$public_ip#" \
  -e "s#\${n}#2#" \
  scripts/kafka-deployment.yml | kubectl --kubeconfig=kubeconfig apply -f -
else
echo 'kafka cluster not created, /api/v1/namespaces/default/services/kafka-service did not return valid URL!'
fi