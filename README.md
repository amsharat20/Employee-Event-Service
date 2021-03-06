# Employee-Event-Service


Steps:

1. Pull Docker Images.
2. Run Images
3. Open swagger to check APIs.



STEP 1: 

          docker pull mysql
          docker pull mongo
          docker pull spotify/kafka
          docker pull php

STEP 2: Run Images:

MYSQL: 

          docker run --name mysqlcontainer -e MYSQL_ROOT_PASSWORD=PASSWORD -e MYSQL_DATABASE=employee_management -p 3307:3307 -d mysql:latest
          docker exec -it mysqlcontainer bash
          mysql -u root -pPASSWORD
          ALTER USER root IDENTIFIED WITH mysql_native_password BY 'PASSWORD';
          ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'PASSWORD';
          exit
          exit

TO CHECK MYSQL in WEBINTERFACE:

          docker run --name phpmyadmin -d --link mysqlcontainer:db -p 8080:80 phpmyadmin/phpmyadmin:latest


MONGO:

          mkdir data
          docker run --name mongo  -p 27017:27017  -d  -v  data:/data/db mongo


KAFKA:

          docker run -d -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=kafka --env ADVERTISED_PORT=9092 --name kafka -h kafka spotify/kafka


SPRING: EMPLOYEE-MANAGEMENT:

          mvn clean install
          docker build -f Dockerfile -t employeecontainer .
          docker run -d -t --name employeecontainer --link mysqlcontainer:mysql --link kafka:kafka  -p 8099:8099 employeecontainer

SPRING:  EVENT-SERVICE:

        mvn clean install
        docker build -f Dockerfile -t eventcontainer .
        docker run -d -t --name eventcontainer --link mongo:mongo --link kafka:kafka  -p 8107:8107 eventcontainer

Swagger API:

        http://localhost:8099/swagger-ui.html#/  (Employee service) (admin/admin) Open in Incognito window.  *Authenticated.
        http://localhost:8107/swagger-ui.html#/  (Event Service)

MySQL USER INTERFACE:
       
        http://localhost:8080/index.php (root/PASSWORD)

Authentication for Swagger APIs:

          username: admin / password: admin

Topic Name Created:  employee-topic
