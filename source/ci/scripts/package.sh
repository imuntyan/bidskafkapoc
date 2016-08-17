#!/bin/sh

cd resource-bidskafkapoc/source

export TERM=${TERM:-dumb}

#gradle -Dorg.gradle.native=false build

pwd

cat /etc/hosts

echo "$k8s_ip $k8s_dns_name" >> /etc/hosts

cat /etc/hosts

ls build/libs

ls ../..

ls ../../resource-jar

#cp build/libs/bidskafkapoc.jar ../../resource-jar

cp Dockerfile ../../resource-jar
