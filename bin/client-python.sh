#!/bin/sh

. bin/utils/base.sh

# if you've executed sbt assembly previously it will use that instead.
export JAVA_OPTS="${JAVA_OPTS} -XX:MaxPermSize=256M -Xmx1024M -DloggerPath=conf/log4j.properties"
ags="$@ generate -i squashed_swagger.json -l python -c bin/client-python.json \
 -o ${CLIENT_TARGET}/python -DhideGenerationTimestamp=true"
java $JAVA_OPTS -jar $executable $ags
cp -ruav python/example/*.py "${CLIENT_TARGET}"/python
