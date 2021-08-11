#!/bin/sh

JAVA_OPTS="-Xmx${XMX} -Xms${XMS}"

exec java ${JAVA_OPTS} -jar /apps/sb/${MSNAME}-svc-${VERSION}.jar