FROM java:8
VOLUME /tmp
EXPOSE 8095
ADD target/employee-management-0.0.1-SNAPSHOT.jar employee-management-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","employee-management-0.0.1-SNAPSHOT.jar"]