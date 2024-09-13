#!/bin/bash
# Cài đặt Maven
curl -o- https://raw.githubusercontent.com/cncf/devstats/master/deploy-maven.sh | bash
# Build dự án
mvn clean install
