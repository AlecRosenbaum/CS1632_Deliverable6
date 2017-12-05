#!/usr/bin/env bash

set -e 

mkdir -p build
javac -cp :lib/* -d build src/*.java
javac -cp :lib/*:build -d build test/*.java
java -cp :lib/*:build rpn.TestRunner
