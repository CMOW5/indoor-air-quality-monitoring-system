import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IMqttMessage, MqttService } from "ngx-mqtt";
import { DataPoint } from './datapoint.interface';

@Injectable({
  providedIn: 'root'
})
export class MqttSubscriberService {

  //private topicUrl = 'sensor/temperature';
  private topicUrl = 'sensor';

  constructor(private _mqttService: MqttService) {}

  createSensorSubscription(sensorId: string): Observable<IMqttMessage> {     
    return new Observable((observer) => {
      
      this._mqttService.observe(this.topicUrl).subscribe((message: IMqttMessage) => {
        const tempDataPoint = JSON.parse(message.payload.toString())
        if (this.isDataForStationId(sensorId, tempDataPoint)) {
          observer.next(tempDataPoint);
        }
      }); 
      
      // When the consumer unsubscribes, clean up data ready for next subscription.
      return () => {
        console.log('closing mqtt connection');
        this._mqttService.disconnect();
      };
    });
  }

  createSubscription(metric:string, stationId: string): Observable<DataPoint> {     
    return new Observable((observer) => {
      const topic = `${this.topicUrl}/${metric}`;
      console.log(`subscribed to topic ${topic}, for station = ${stationId}`);
      const subscription = this._mqttService.observe(topic).subscribe((message: IMqttMessage) => {
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

  // todo: type this
  private isDataForStationId(stationId: String, datapoint: DataPoint): boolean {
    return datapoint.stationId === stationId;
  }
}
