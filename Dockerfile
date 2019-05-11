FROM tomcat

COPY gwt/target/pacman-gwt-1.0-SNAPSHOT.war /app/

EXPOSE 8888

CMD java -jar /app/pacman-gwt-1.0-SNAPSHOT.war
