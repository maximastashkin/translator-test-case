FROM maven:3.8.6-jdk-8 as builder

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:8-jdk-alpine

COPY --from=builder /usr/src/app/target/translator-test-case-0.0.1-SNAPSHOT.jar /usr/app/translator-test-case-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /usr/app/translator-test-case-0.0.1-SNAPSHOT.jar ${@}"]