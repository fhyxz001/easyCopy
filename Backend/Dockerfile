FROM java:11

COPY target/*.jar /app.jar

CMD ["--service.port=9197"]

EXPOSE 9197

ENTRYPOINT ["java","-jar","/app.jar"]