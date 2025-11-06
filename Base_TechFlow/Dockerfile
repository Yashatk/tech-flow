FROM alpine:3.20

RUN apk update
RUN apk add --no-cache java-cacerts openjdk17-jdk

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV PATH=$JAVA_HOME/bin:$PATH

VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
#COPY application.yml application.yml
ENTRYPOINT ["java","-jar","/app.jar"]
