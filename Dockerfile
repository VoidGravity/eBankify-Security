FROM openjdk:17-jdk-slim

VOLUME /tmp

COPY target/ebankify-1-0.0.1-SNAPSHOT.jar app.jar
#COPY target/eBankify-p1-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]



#
#FROM openjdk:17-jdk-slim
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
