#!/bin/sh

cd bidskafkapoc-docker-image

ls -al

less /etc/hosts

sudo echo "$k8s_ip $k8s_dns_name" >> /etc/hosts

