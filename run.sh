(sleep 30; ./gradlew buildAndReload --continuous -PskipDownload=true -x Test)&
# src/main/resources/application-development.yml을 실행
./gradlew bootRun -PskipDownload=true -Dspring.profiles.active=development