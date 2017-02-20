#!/bin/sh

. bin/utils/base.sh

[[ -e "api-clients/csharp4" ]] && rm -rf api-clients/csharp4

# if you've executed sbt assembly previously it will use that instead.
export JAVA_OPTS="${JAVA_OPTS} -XX:MaxPermSize=256M -Xmx1024M -DloggerPath=conf/log4j.properties"
ags="$@ generate -i squashed_swagger.json -l csharp -c bin/client-csharp4.json -o \
dist/clients/csharp -DhideGenerationTimestamp=true"

echo "Removing files and folders under api-clients/csharp4"
#rm -rf api-clients/java/jersey2/src/main
#find api-clients/java/jersey2 -maxdepth 1 -type f ! -name "README.md" -exec rm {} +
java $JAVA_OPTS -jar $executable $ags
