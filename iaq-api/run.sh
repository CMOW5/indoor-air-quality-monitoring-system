#!/bin/sh
echo "creating self-signed cert"
keytool -genkeypair -alias iaqapi -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore iaqapi.p12 -storepass password -keypass password -validity 3650 -dname "CN=IAQ_Tomcat, OU=iaqapi, O=iaqapi, C=US"

exec java -jar app.jar