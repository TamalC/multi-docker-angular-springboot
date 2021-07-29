FROM openjdk:8-jdk-alpine
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod a+x mvnw
RUN ./mvnw install -DskipTests
# RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# FROM openjdk:8-jdk-alpine
# VOLUME /tmp
# ARG DEPENDENCY=/workspace/app/target/dependency
# COPY --from=0 ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY --from=0 ${DEPENDENCY}/META-INF /app/META-INF
# COPY --from=0 ${DEPENDENCY}/BOOT-INF/classes /app
# ENTRYPOINT ["java","-cp","app:app/lib/*","SpringSecurityJpaApplication"]

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=/workspace/app/target/*.jar
COPY --from=0 ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]