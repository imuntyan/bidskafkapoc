#!/bin/sh

cd bidskafkapoc-docker-image

ls -al

cat /etc/hosts

echo "$k8s_ip $k8s_dns_name" >> /etc/hosts

cat /etc/hosts

cat /etc/resolv.conf

apt-get install -y dig

nslookup google.com