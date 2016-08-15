#!/bin/sh

cd resource-bidskafkapoc/source

export TERM=${TERM:-dumb}

gradle -Dorg.gradle.native=false build

pwd

ls build/libs

ls ..

ls ../resource-jar

cp build/libs/bidskafkapoc.jar ../resource-jar

cp Dockerfile ../resource-jar
