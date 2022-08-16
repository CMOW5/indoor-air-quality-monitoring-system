FROM amazoncorretto:11
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# build with
# docker build -t cmow5/iaq-api .
#  docker run -p 8080:8080 --network=host cmow5/iaq-api