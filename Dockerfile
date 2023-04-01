# 베이스 이미지를 선택합니다.
FROM openjdk:17-jdk-slim AS build
LABEL org.opencontainers.image.source="https://github.com/f-lab-edu/art-share"

# 작업 디렉토리를 설정합니다.
WORKDIR /app

# 종속성 관련 파일들만 먼저 복사합니다.
COPY build.gradle.kts settings.gradle.kts ./

# Gradle Wrapper 파일 및 디렉터리를 복사합니다.
COPY gradlew ./
COPY gradle ./gradle

# 종속성을 설치합니다. 이를 통해 변경되지 않은 종속성은 캐시에서 가져올 수 있습니다.
RUN ./gradlew dependencies

# 프로젝트 파일을 복사합니다.
COPY src ./src
COPY run.sh ./

# 프로젝트를 빌드하고 필요한 파일을 생성합니다.
RUN chmod +x run.sh && ./gradlew updateLib

# 런타임 이미지를 위한 베이스 이미지를 선택합니다.
FROM openjdk:17-slim

# 작업 디렉토리를 설정합니다.
WORKDIR /app

# 빌드 결과물 및 필요한 파일을 런타임 이미지로 복사합니다.
COPY --from=build /app/libs ./libs
COPY --from=build /app/run.sh ./run.sh
COPY --from=build /app/build.gradle.kts ./
COPY --from=build /app/settings.gradle.kts ./
COPY --from=build /app/gradlew ./
COPY --from=build /app/gradle ./gradle
COPY --from=build /app/src ./src

# 애플리케이션을 실행합니다.
CMD ["sh", "./run.sh"]
