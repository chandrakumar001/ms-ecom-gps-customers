# base image from adopt openjdk
FROM adoptopenjdk/openjdk11:alpine-jre
# Refer to Maven build -> finalName
ARG JAR_FILE=target/*.jar
# cd /opt/app
WORKDIR /opt/app
COPY ${JAR_FILE} ecom-gps-customers.jar
ENTRYPOINT ["java","-jar","ecom-gps-customers.jar"]