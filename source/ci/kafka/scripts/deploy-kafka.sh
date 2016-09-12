#!/bin/sh

cd resource-bidskafkapoc/source/ci/kafka

mkdir credentials

echo "$k8s_cluster_ca" | base64 -d >> credentials/ca.pem
echo "$k8s_admin_cert" | base64 -d >> credentials/admin.pem
echo "$k8s_admin_key" | base64 -d >> credentials/admin-key.pem

for ORDINAL in 1 2
do

sed -e "s#\${n}#$ORDINAL#" \
  scripts/kafka-service.yml | kubectl --kubeconfig=kubeconfig apply -f -

done


sleep 10

rm -f proxy.log proxy.pid
touch proxy.log
nohup kubectl --kubeconfig=kubeconfig proxy > proxy.log 2>&1 & echo $! > proxy.pid
# wait for proxy to start
while ! grep "8001" -q proxy.log
do sleep 0.1; done


for ORDINAL in 1 2
do

URL="http://127.0.0.1:8001/api/v1/namespaces/default/services/kafka-service-$ORDINAL"
public_ip=`curl -s $URL 2>&1 | grep '\"hostname\"' | awk '{print $2}' | awk -F\" '{print $2}'`
echo $public_ip

if [ ! -z "$public_ip" ]
then
sed -e "s#\${KAFKA_ADVERTISED_HOST_NAME}#$public_ip#" \
  -e "s#\${n}#$ORDINAL#" \
  scripts/kafka-deployment.yml | kubectl --kubeconfig=kubeconfig apply -f -
else
echo "kafka cluster not created, /api/v1/namespaces/default/services/kafka-service-$ORDINAL did not return valid URL!"
fi

done


kill `cat proxy.pid`


