FROM openjdk:11-jdk

VOLUME /tmp

ARG JAR_FILE=./art-api/build/libs/art-api-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

# 운영
ENTRYPOINT ["nohup","java","-jar",\
"-javaagent:./pinpoint/pinpoint-bootstrap-2.2.2.jar",\
"-Dpinpoint.agentId=gjgs01","-Dpinpoint.applicationName=gjgs",\
"-Dpinpoint.config=./pinpoint/pinpoint-root.config"\
,"-Dspring.profiles.active=prod","app.jar","2>&1","&"]
