FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /workspace

COPY pom.xml ./
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B -q clean package -DskipTests -Dspotless.check.skip=true

FROM eclipse-temurin:25-jre AS runtime
WORKDIR /app

LABEL org.opencontainers.image.title="trackcrud-backend" \
      org.opencontainers.image.description="Backend do TrackCrud"

RUN apt-get update \
  && groupadd --system --gid 982 user \
  && useradd --system --no-log-init --uid 982 --gid 982 user
USER user

COPY --from=build /workspace/target/trackcrud-*.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=dev \
    JAVA_TOOL_OPTIONS=""

ENTRYPOINT ["java", "-jar", "/app/app.jar"]