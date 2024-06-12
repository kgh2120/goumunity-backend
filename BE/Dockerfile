# APP
FROM openjdk:21-slim
WORKDIR /app

# 빌더 이미지에서 jar 파일만 복사
COPY /build/libs/*-SNAPSHOT.jar ./app.jar


EXPOSE 8080

ENTRYPOINT [                                                \
    "java",                                                 \
    "-jar",                                                 \
    "-Duser.timezone=Asia/Seoul",                          \
    "-Dspring.profiles.active=perf",                      \
    "app.jar"                                               \
]