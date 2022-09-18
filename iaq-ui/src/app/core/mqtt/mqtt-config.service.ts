import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MqttConfigService {

  mqttConfigs!: MqttConfigsDto;

  constructor(private httpClient: HttpClient) {}

  public getMqttConfigs(): Observable<MqttConfigsDto> {
    if (this.mqttConfigs) {
      return of(this.mqttConfigs);
    }

    const url = environment.MQTT_CONFIGS_PATH; 

    return new Observable((observer) => {
      this.httpClient.get<MqttConfigsDto>(url).subscribe((response) => {
        observer.next(response);
      });      
    });
  }
}

export interface MqttConfigsDto {
  endpoint: string,
  awsRegion: string,
  identityPoolId: string;
}
