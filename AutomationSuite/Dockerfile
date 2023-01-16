FROM selenium/standalone-chrome:latest as run
#FROM alpine:3.16.0 as run
USER root
WORKDIR /app
RUN apt-get update && apt-get install -y maven
RUN mvn -version

COPY setting.xml setting.xml
COPY pom.xml pom.xml
COPY locallib locallib
# RUN mkdir -p src
COPY src src

#Install lib
RUN mvn clean install -Dmaven.test.skip -s setting.xml

# RUN mkdir -p src/main/java/com/qa/config/
#COPY ./src/test/com/qa/config/ src/test/java/com/qa/config/

#COPY ./src/test/java/com/qa/testdata/ src/test/java/com/qa/testdata/
#COPY ./src/test/java/com/qa/testscripts src/test/java/com/qa/testscripts
COPY entrypoint.sh .
CMD ["/bin/sh", "/app/entrypoint.sh"]
