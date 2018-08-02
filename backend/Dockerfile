FROM openjdk:10

VOLUME /tmp

ARG JAR_FILE=target/bts-demo.jar

ADD ${JAR_FILE} bts-demo.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=container","-jar","/bts-demo.jar"]