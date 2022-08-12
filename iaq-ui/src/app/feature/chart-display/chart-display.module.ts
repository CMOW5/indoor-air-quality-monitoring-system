import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SingleChartDisplayComponent } from './single-chart-display/single-chart-display.component';
import { MqttModule, IMqttServiceOptions} from 'ngx-mqtt';
import { NgChartsModule } from 'ng2-charts';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from 'src/app/shared/shared.module';
import { MainChartDisplayComponent } from './main-chart-display/main-chart-display.component';
import { StationSelectorComponent } from './main-chart-display/station-selector/station-selector.component';
import { MetricsSelectorComponent } from './main-chart-display/metrics-selector/metrics-selector.component';
import { DateSelectorComponent } from './main-chart-display/date-selector/date-selector.component';
import { RealtimeDateSelectorComponent } from './main-chart-display/date-selector/realtime-date-selector/realtime-date-selector.component';
import { HistoricDateSelectorComponent } from './main-chart-display/date-selector/historic-date-selector/historic-date-selector.component';

/*
{
  "stationId": "test-station-pc-1",
  "value": 16,
  "timestamp": "2022-08-12T01:47:49Z"
}
*/

// MQTT configuration
export const MQTT_SERVICE_OPTIONS: IMqttServiceOptions = {
  hostname: 'AWS-HOST',
  port: 443,
  //protocol: 'ws',
  path: '/mqtt',
  // todo: mqtt cert
  ca: `
-----BEGIN CERTIFICATE-----
<CA>
-----END CERTIFICATE-----  
  `,
  key: `
  -----BEGIN RSA PRIVATE KEY-----
<KEY>
-----END RSA PRIVATE KEY-----
  `,
  cert: `
-----BEGIN CERTIFICATE-----
  <CERT>
-----END CERTIFICATE-----
  `,
  clientId: 'client-12'
};

@NgModule({
  declarations: [
    SingleChartDisplayComponent,
    MainChartDisplayComponent,
    StationSelectorComponent,
    MetricsSelectorComponent,
    DateSelectorComponent,
    RealtimeDateSelectorComponent,
    HistoricDateSelectorComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule, // todo: move this to shared?
    FormsModule, // todo: move this to shared?
    ReactiveFormsModule, // todo: move this to shared?
    SharedModule,
    NgChartsModule,
    MqttModule.forRoot(MQTT_SERVICE_OPTIONS)
  ]
})
export class ChartDisplayModule { }
