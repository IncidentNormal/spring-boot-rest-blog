#!/bin/sh

JAVA_OPTS="-Xmx${XMX-512m} -Xms${XMS-512m}"

exec java ${JAVA_OPTS} -jar /apps/sb/${APPNAME}-${VERSION}.jar