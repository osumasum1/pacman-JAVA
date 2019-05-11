#FROM tomcat

#COPY gwt/target/pacman-gwt-1.0-SNAPSHOT.war /app/

#EXPOSE 8888

#CMD java -jar /app/pacman-gwt-1.0-SNAPSHOT.war

# Init
FROM thonatos/openjdk-tomcat-maven:jdk8-maven3

RUN mkdir /app
WORKDIR /app
COPY gwt/target/pacman-gwt-1.0-SNAPSHOT.war /app/
#COPY ./ /app

RUN chmod a+x /app/compile.sh
RUN /app/compile.sh
