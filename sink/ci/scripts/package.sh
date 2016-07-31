#!/bin/sh

cd resource-bidskafkapoc

export TERM=${TERM:-dumb}

gradle -Dorg.gradle.native=false build

ls build/libs

cp build/libs/bidskafkapoc.jar ../resource-jar

cp Dockerfile ../resource-jar
