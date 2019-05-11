FROM openjdk:8-jre-alpine

COPY target/spring-petclinic-2.1.0.BUILD-SNAPSHOT.jar /app/

EXPOSE 8080

CMD java -jar /app/spring-petclinic-2.1.0.BUILD-SNAPSHOT.jar
