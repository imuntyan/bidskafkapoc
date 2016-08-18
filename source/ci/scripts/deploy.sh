#!/bin/sh

cd bidskafkapoc-docker-image

ls -al

cat /etc/hosts

echo "$k8s_ip $k8s_dns_name" >> /etc/hosts

cat /etc/hosts

dig k8s.kubernetes.net