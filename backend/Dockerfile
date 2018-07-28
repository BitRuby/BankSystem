FROM java:10
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} bts-demo.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/bts-demo.jar"]