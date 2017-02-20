#!/bin/sh

. bin/utils/base.sh

# if you've executed sbt assembly previously it will use that instead.
export JAVA_OPTS="${JAVA_OPTS} -XX:MaxPermSize=256M -Xmx1024M -DloggerPath=conf/log4j.properties"
ags="$@ generate -i squashed_swagger.json -l go -c bin/client-go.json \
-o ${CLIENT_TARGET}/go/src/nedap.com/retail/api -DhideGenerationTimestamp=true "

echo "Removing files and folders under api-clients/go/src/main"
#rm -rf api-clients/java/jersey2/src/main
#find api-clients/java/jersey2 -maxdepth 1 -type f ! -name "README.md" -exec rm {} +
java $JAVA_OPTS -jar $executable $ags
