CERT_FILE=iaqapi.p12

echo "creating self-signed cert"

if [ -f "$CERT_FILE" ]
then
    echo "self signed cert provided"
else
    echo "self signed cert not provided. Creating one ..."
    keytool -genkeypair -alias iaqapi -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore iaqapi.p12 -storepass password -keypass password -validity 3650 -dname "CN=IAQ_Tomcat, OU=iaqapi, O=iaqapi, C=US"
fi

exec java -jar app.jar