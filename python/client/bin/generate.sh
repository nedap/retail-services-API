#!/bin/sh

executable="../bin/swagger-codegen-cli.jar"

# if you've executed sbt assembly previously it will use that instead.
export JAVA_OPTS=" -Dmodels -Dapis -DsupportingFiles -XX:MaxPermSize=256M -Xmx1024M \
 -DloggerPath=conf/log4j.properties"
ags="$@ generate -i ../openapi/idcloud-openapi.json -l python -c client/bin/client.json -o \
target -DhideGenerationTimestamp=true -Dmodels -Dapis"

java $JAVA_OPTS -jar $executable $ags