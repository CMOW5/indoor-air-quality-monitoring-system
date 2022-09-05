FROM amazoncorretto:11
#VOLUME /tmp
WORKDIR /app
COPY run.sh /app
COPY target/*.jar app.jar
ENTRYPOINT ["sh", "/app/run.sh"]

# build with
# docker build -t cmow5/iaq-api .

# run with
#  docker run -p 8080:8080 --network=host cmow5/iaq-api

# run the container using the local AWS credentials
# docker run -p 8080:8080 --network=host -v /home/bit5/.aws/credentials:/root/.aws/credentials:ro cmow5/iaq-api
# docker run -p 8080:8080 --network=host -v /home/bit5/.aws/credentials:/root/.aws/credentials:ro 354562611480.dkr.ecr.us-east-1.amazonaws.com/iaq-api:latest


#docker run -p 8080:8080 --network=host -v /home/bit5/.aws/credentials:/root/.aws/credentials:ro -v /home/bit5/Desktop/test-key/iaqapi.p12:/root/app/iaqapi.p12:ro cmow5/iaq-api
