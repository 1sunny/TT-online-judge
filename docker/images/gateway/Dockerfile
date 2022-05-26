FROM openjdk:8-jre
ENV APP_PATH=/apps
WORKDIR $APP_PATH
COPY api_gateway-1.0-SNAPSHOT.jar $APP_PATH/app.jar
EXPOSE 9990
ENTRYPOINT ["java", "-jar"]
CMD ["app.jar", "--spring.profiles.active=prod"]
