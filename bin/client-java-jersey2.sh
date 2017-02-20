#!/bin/sh

. bin/utils/base.sh

# if you've executed sbt assembly previously it will use that instead.
export JAVA_OPTS="${JAVA_OPTS} -XX:MaxPermSize=256M -Xmx1024M -DloggerPath=conf/log4j.properties"
ags="$@ generate -i squashed_swagger.json -l java -c bin/client-java-jersey2.json \
-o dist/clients/java/jersey2 -DhideGenerationTimestamp=true "
#-t java/client/templates/jersey2 "


echo "Removing files and folders under api-clients/java/jersey2/src/main"
rm -rf dist/clients/java/jersey2
find dist/clients/java/jersey2 -maxdepth 1 -type f ! -name "README.md" -exec rm {} +
java $JAVA_OPTS -jar $executable $ags
