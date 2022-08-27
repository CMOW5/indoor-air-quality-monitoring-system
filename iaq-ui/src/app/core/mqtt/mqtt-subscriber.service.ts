import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DataPoint } from './datapoint.interface';
import { Amplify } from 'aws-amplify';
import { AWSIoTProvider } from '@aws-amplify/pubsub/lib/Providers';
import { MqttOverWSProvider } from "@aws-amplify/pubsub/lib/Providers";
import { MqttConfigsDto, MqttConfigService } from './mqtt-config.service';

@Injectable({
  providedIn: 'root'
})
export class MqttSubscriberService {

  private topicUrl = 'sensor';

  constructor(private mqttConfig: MqttConfigService) {
    this.initMqtt();
  }

  createSubscription(metric:string, stationId: string): Observable<DataPoint> {  
    return new Observable((observer) => {
      const topic = `${this.topicUrl}/${metric}`;
      console.log(`subscribed to topic ${topic}, for station = ${stationId}`);
      const subscription = Amplify.PubSub.subscribe(topic).subscribe({
        next: (data: MqttMessage) => {
          const datapoint = data.value;
          if (this.isDataForStationId(stationId, datapoint)) {
            observer.next(datapoint);
          }
        },
        error: (error: any) => console.error(error),
        close: () => console.log('Done'),
      });
  
      // When the consumer unsubscribes, clean up data ready for next subscription.
      return () => {
        console.log('closing mqtt connection');
        subscription.unsubscribe();
      };
    });
  }

  initMqtt() {
    this.mqttConfig.getMqttConfigs().subscribe((config: MqttConfigsDto) => {
        
        Amplify.configure({
          Auth: {
            identityPoolId: config.identityPoolId,
            region: config.awsRegion,
            //userPoolId: APP_USER_POOL_ID,
            //userPoolWebClientId: APP_USER_POOL_WEB_CLIENT_ID
          }
        });
        

        // AWS IoT Provider
        
        Amplify.addPluggable(new AWSIoTProvider({
          aws_pubsub_region: config.awsRegion,
          aws_pubsub_endpoint: config.endpoint,
        }));
          
      /*  
      Amplify.addPluggable(new MqttOverWSProvider({
        //aws_pubsub_endpoint: 'wss://localhost:9001/mqtt',
        aws_pubsub_endpoint: config.endpoint,
        // disable SSL
        aws_appsync_dangerously_connect_to_http_endpoint_for_testing: true
        }));
      */
    })
  }

  // todo: type this
  private isDataForStationId(stationId: String, datapoint: DataPoint): boolean {
    return datapoint.stationId === stationId;
  }
}

interface MqttMessage {
  value: DataPoint
}
