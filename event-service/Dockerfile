FROM java:8
VOLUME /temp
EXPOSE 8099
ADD /target/event-service-0.0.1-SNAPSHOT.jar event-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","event-service-0.0.1-SNAPSHOT.jar"]