#!/bin/sh

. bin/utils/base.sh

# if you've executed sbt assembly previously it will use that instead.
export JAVA_OPTS="${JAVA_OPTS} -XX:MaxPermSize=256M -Xmx1024M -DloggerPath=conf/log4j.properties"
ags="$@ generate -i swagger/idcloud-swagger.json -l python -c bin/client-python.json \
 -o ${CLIENT_TARGET}/python -DhideGenerationTimestamp=true"
java $JAVA_OPTS -jar $executable $ags
cp -f python/example/*.py "${CLIENT_TARGET}"/python
# if you've executed sbt assembly previously it will use that instead.
export JAVA_OPTS="${JAVA_OPTS} -XX:MaxPermSize=256M -Xmx1024M -DloggerPath=conf/log4j.properties"
ags="$@ generate -i swagger/oauth-swagger.json -l python -c bin/oauth-python.json \
 -o ${CLIENT_TARGET}/python -DhideGenerationTimestamp=true"
java $JAVA_OPTS -jar $executable $ags