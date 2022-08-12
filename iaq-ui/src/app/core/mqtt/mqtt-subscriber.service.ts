import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IMqttMessage, MqttService } from "ngx-mqtt";
import { DataPoint } from './datapoint.interface';
//import { mqtt, iot, auth } from "aws-iot-device-sdk-v2";
import { mqtt, http, io, iot, auth } from 'aws-crt/dist.browser/browser';
import * as AWS from "aws-sdk";

@Injectable({
  providedIn: 'root'
})
export class MqttSubscriberService {

  //private topicUrl = 'sensor/temperature';
  private topicUrl = 'sensor';

  settings = {
    AWS_REGION: "<REGION>",
    AWS_IOT_ENDPOINT: "<IOT_ENDPOINT>",
    AWS_COGNITO_IDENTITY_POOL_ID: "<COGNITO-POOL-ID>",
  }

  provider:any

  constructor(private _mqttService: MqttService) {
    this.provider = new AWSCognitoCredentialsProvider({
      IdentityPoolId: this.settings.AWS_COGNITO_IDENTITY_POOL_ID, 
      Region: this.settings.AWS_REGION});
    // Make sure the credential provider fetched before setup the connection
    this.provider.refreshCredentialAsync().then(() => {
      this.connect_websocket(this.provider).then((connection) => {
        connection
          .subscribe(
            "sensor/temperature",
            mqtt.QoS.AtLeastOnce,
            (topic, payload, dup, qos, retain) => {
              const decoder = new TextDecoder("utf8");
              let message = decoder.decode(new Uint8Array(payload));
              console.log(`Message received: topic=${topic} message=${message}`);
              /** The sample is used to demo long-running web service. 
               * Uncomment the following line to see how disconnect behaves.*/
              // connection.disconnect();
            }
          )
          .then((subscription) => {
            console.log(`start publish`)
            var msg_count = 0;
            connection.publish(subscription.topic, `NOTICE ME {${msg_count}}`, subscription.qos);
            /** The sample is used to demo long-running web service. The sample will keep publishing the message every minute.*/
            setInterval( ()=>{
              msg_count++;
              const msg = `NOTICE ME {${msg_count}}`;
              connection.publish(subscription.topic, msg, subscription.qos);
            }, 60000);
          });
      })
      .catch((reason) => {
        console.log(`Error while connecting: ${reason}`);
      });
    })
  }


  createSubscription(metric:string, stationId: string): Observable<DataPoint> {  
    
    
    return new Observable((observer) => {
      console.log('CMEX CLIENT ID = ', this._mqttService.clientId);
      console.log('CMEX state = ', this._mqttService.state);

      this._mqttService.onMessage.subscribe((msg) => {
        console.log('CMEX got a message2 = ', msg);
      })  


      this._mqttService.onConnect.subscribe((msg) => {
        console.log('CMEX CONNECT = ', msg);
      })  

      

      console.log('CMEX MESSAGE = ', this._mqttService.onMessage);
      console.log('CMEX _MQTT = ', this._mqttService);

      const topic = `${this.topicUrl}/${metric}`;
      console.log(`subscribed to topic ${topic}, for station = ${stationId}`);
      const subscription = this._mqttService.observe(topic).subscribe((message: IMqttMessage) => {
        console.log('CMEX got a message = ', message);
        const datapoint = JSON.parse(message.payload.toString())
        if (this.isDataForStationId(stationId, datapoint)) {
          observer.next(datapoint);
        }
      }); 
      
      // When the consumer unsubscribes, clean up data ready for next subscription.
      return () => {
        console.log('closing mqtt connection');
        subscription.unsubscribe();
        //this._mqttService.disconnect();
      };
    });
  }

  connect_websocket(provider: auth.CredentialsProvider) {

    return new Promise<mqtt.MqttClientConnection>((resolve, reject) => {
      let config = iot.AwsIotMqttConnectionConfigBuilder.new_builder_for_websocket()
          .with_clean_session(true)
          .with_client_id(`client-123`)
          .with_endpoint(this.settings.AWS_IOT_ENDPOINT)
          .with_credential_provider(provider)
          .with_use_websockets()
          .with_keep_alive_seconds(30)
          .build();
  
      console.log("Connecting websocket...");
      const client = new mqtt.MqttClient();
  
      const connection = client.new_connection(config);
      connection.on("interrupt", (error) => {
        console.log(`Connection interrupted: error=${error}`);
      });
      connection.on("resume", (return_code, session_present) => {
        console.log(`Resumed: rc: ${return_code} existing session: ${session_present}`);
      });
      connection.on("disconnect", () => {
        console.log("Disconnected");
      });
      connection.on("error", (error) => {
        reject(error);
      });
      connection.on("connect", (session_present) => {
        resolve(connection);
      });
  
      connection.connect();
    });
  }

  // todo: type this
  private isDataForStationId(stationId: String, datapoint: DataPoint): boolean {
    return datapoint.stationId === stationId;
  }
}


class AWSCognitoCredentialsProvider extends auth.CredentialsProvider{
  private options: AWSCognitoCredentialOptions;
  private source_provider : AWS.CognitoIdentityCredentials;
  private aws_credentials : auth.AWSCredentials;
  constructor(options: AWSCognitoCredentialOptions, expire_interval_in_ms? : number)
  {
    super();
    this.options = options;
    AWS.config.region = options.Region;
    this.source_provider = new AWS.CognitoIdentityCredentials({
        IdentityPoolId: options.IdentityPoolId
    });
    this.aws_credentials = 
    {
        aws_region: options.Region,
        aws_access_id : this.source_provider.accessKeyId,
        aws_secret_key: this.source_provider.secretAccessKey,
        aws_sts_token: this.source_provider.sessionToken
    }

    setInterval(async ()=>{
        await this.refreshCredentialAsync();
    },expire_interval_in_ms?? 3600*1000);
  }

  override getCredentials(){
      return this.aws_credentials;
  }

  public refreshCredentialAsync(): Promise<AWSCognitoCredentialsProvider>
  {
    console.log('GHE REFRESS');
    return new Promise<AWSCognitoCredentialsProvider>((resolve, reject) => {
        this.source_provider.get((err)=>{
            if(err)
            {
                reject("Failed to get cognito credentials.")
            }
            else
            {
                this.aws_credentials.aws_access_id = this.source_provider.accessKeyId;
                this.aws_credentials.aws_secret_key = this.source_provider.secretAccessKey;
                this.aws_credentials.aws_sts_token = this.source_provider.sessionToken;
                this.aws_credentials.aws_region = this.options.Region;
                resolve(this);
            }
        });
    });
  }
}

interface AWSCognitoCredentialOptions
{
  IdentityPoolId : string,
  Region: string
}