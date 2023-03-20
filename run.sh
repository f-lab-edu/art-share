(sleep 30; ./gradlew buildAndReload)&
# src/main/resources/application-development.yml을 실행
./gradlew bootRun -PskipDownload=true -Dspring.profiles.active=dev
