FROM openjdk:17
ADD ./DMFlowManager-app-0.0.1-SNAPSHOT.jar /usr/src/myapp/app.jar
WORKDIR /usr/src/myapp
ENTRYPOINT ["java","-jar","./app.jar"]