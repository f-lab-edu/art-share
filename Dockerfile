FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . /app

RUN chmod +x run.sh && ./gradlew updateLib

EXPOSE 8080

CMD [ "sh" , "run.sh" ]
