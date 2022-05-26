FROM openjdk:8-jre
ENV APP_PATH=/apps
WORKDIR $APP_PATH
COPY oj_service-1.0-SNAPSHOT.jar $APP_PATH/app.jar
EXPOSE 9991
ENTRYPOINT ["java", "-jar"]
CMD ["app.jar", "--spring.profiles.active=prod"]
