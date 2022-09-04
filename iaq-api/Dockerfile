FROM amazoncorretto:11
#VOLUME /tmp
WORKDIR /app
COPY run.sh /app
COPY target/*.jar app.jar
ENTRYPOINT ["sh", "/app/run.sh"]

# build with
# docker build -t cmow5/iaq-api .
#  docker run -p 8080:8080 --network=host cmow5/iaq-api