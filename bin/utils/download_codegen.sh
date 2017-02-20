#!/usr/bin/env bash

VERSION="2.2.2-SNAPSHOT"
URL="http://central.maven.org/maven2/io/swagger/swagger-codegen-cli/${VERSION}/swagger-codegen-cli-${VERSION}.jar"
SCRIPT_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

if [ ! -e "${SCRIPT_PATH}/swagger-codegen-cli.jar" ]; then
    echo -ne "Downloading codegen-cli..."
    curl ${URL} -o ${SCRIPT_PATH}/swagger-codegen-cli.jar
fi