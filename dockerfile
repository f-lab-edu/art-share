FROM openjdk:11-jdk

VOLUME /tmp

ARG JAR_FILE=./art-api/build/libs/art-api-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

# Set environment variables
ENV JAVA_AGENT=""
ENV PINPOINT_AGENT_ID=""
ENV PINPOINT_APP_NAME=""

# CMD command
CMD nohup java -jar $JAVA_AGENT -Dpinpoint.agentId=$PINPOINT_AGENT_ID -Dpinpoint.applicationName=$PINPOINT_APP_NAME app.jar 2>&1 &
