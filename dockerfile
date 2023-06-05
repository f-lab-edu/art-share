FROM openjdk:11-jdk

VOLUME /tmp

ARG JAR_FILE=./art-api/build/libs/art-api-0.0.1-SNAPSHOT.jar

COPY firebase/firebase-admin.json firebase/firebase-admin.json

COPY ${JAR_FILE} app.jar

# Update and Install curl
RUN apt-get update && apt-get install -y wget

# Download the agent.tar.gz file
RUN wget https://ncloud-pinpoint.com/agent.tar.gz

# Unzip the agent.tar.gz file
RUN tar xvf agent.tar.gz

RUN touch ./pinpoint-agent-2.2.3-NCP-RC1/pinpoint.license

RUN echo "d3e5add9350446779b47ec248b12f2ba" > ./pinpoint-agent-2.2.3-NCP-RC1/pinpoint.license

# Set environment variables
ENV JAVA_AGENT="./pinpoint-agent-2.2.3-NCP-RC1/pinpoint-bootstrap-2.2.3-NCP-RC1.jar"
ENV PINPOINT_AGENT_ID="Agent"
ENV PINPOINT_APP_NAME="art-share"

# CMD command
CMD java -jar \
    -javaagent:$JAVA_AGENT \
    -Dpinpoint.agentId=$PINPOINT_AGENT_ID \
    -Dpinpoint.applicationName=$PINPOINT_APP_NAME \
    -Dspring.profiles.active=prod \
    app.jar
