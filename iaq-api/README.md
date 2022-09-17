
# build docker image 
sh build_docker.sh

# run it locally (local mongo and mqtt)
docker run -p 8080:8080 --network=host iaq-api

# run it locally using aws credentials (aws-iot-core)
docker run -p 8080:8080 --network=host -v /home/bit5/.aws/credentials:/root/.aws/credentials:ro iaq-api