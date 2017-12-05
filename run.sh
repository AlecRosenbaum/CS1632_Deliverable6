#!/usr/bin/env bash

set -e 

mkdir -p build
javac -cp :lib/* -d build src/*.java
java -cp :lib/*:build rpn.RPN $@