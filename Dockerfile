FROM anapsix/alpine-java

COPY weather.jar /app/weather.jar

CMD ["java","-jar","/app/weather.jar"]