@echo off
if exist target rd /S/Q target
java -DhideGenerationTimestamp=true -Dmodels -Dapis -DsupportingFiles -XX:MaxPermSize=256M -Xmx1024M -DloggerPath=conf\log4j.properties -jar ..\bin\swagger-codegen-cli.jar generate -i ..\openapi\retail-services-openapi.json -l csharp -c client\bin\generate.json -o target\generated-sources

@echo ******************************
@echo * Source generated in target *
@echo ******************************