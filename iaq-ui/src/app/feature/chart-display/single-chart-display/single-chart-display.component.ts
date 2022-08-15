import { AfterViewInit, Component, Input, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { ChartEvent, ChartOptions } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { from, mergeMap, Subscription, tap } from 'rxjs';
import { SensorHistoricDataService } from 'src/app/core/historic/sensor-historic-data.service';
import { MqttSubscriberService } from 'src/app/core/mqtt/mqtt-subscriber.service';
import { Metric } from '../../../core/metric/metric.interface';
import { Station } from '../../../core/station/station.interface';
import { DateRange } from '../main-chart-display/date-selector/date-range.interface';
import 'chartjs-adapter-moment';
import { RealTimeDateUnit } from '../main-chart-display/date-selector/realtime-date-selector/real-time-unit.enum';


@Component({
  selector: 'app-single-chart-display',
  templateUrl: './single-chart-display.component.html',
  styleUrls: ['./single-chart-display.component.sass']
})
export class SingleChartDisplayComponent implements OnInit {

  @Input()
  public metric!: Metric;

  @Input()
  public stations: Array<Station> = [];
  
  @Input()
  public dateRange!: DateRange;

  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  private mqttSubscriptions: Array<Subscription> = [];

  public datasets = [
    {
      data: [
       {}
      ],
      label: ''
  }]

  chartOptions: ChartOptions = {
    maintainAspectRatio: false,
    responsive: true,
    elements: {
      line: {
        fill: true
      },
      point: {
        radius: 0
      }
      /*
      line: {
        tension: 0.5
      }
      */
    },
    /*
    animation: {
      duration: 0
    },
    */
    scales: {
      x: {
        //suggestedMax: "2022-08-06T04:33:17Z",
        type: 'time',
        
        ticks: {
          /*
          callback: function(val, index) {
            
            console.log('val , index = ', val, )
            //return index % 2 === 0 ? val : '';
            return val;
          },
          */
         autoSkip: true
        },
        /*
        ticks: {
          source: 'auto'
        },
        */
        // MMSS
        time: {
          /*
           displayFormats: {
                  second: "HH:mm",
                  minute: "HH:mm",
                  hour: "HH:mm",
                  day: "MMM d",
                },
          */
          //unit: 'minute', // works kinda well??
          displayFormats: {
            'millisecond': 'HH:mm:ss',
            'second': 'HH:mm:ss',
            'minute': 'HH:mm',
            'hour': 'HH',
            'day': 'MM DD HH',
            'week': 'MM DD HH',
            'month': 'MM DD HH',
            'quarter': 'MM DD HH',
            'year': 'MM DD HH',
         }
        }
      },
      y: {
        title: {
          display: true,
          text: ' ' // needs to be something. Otherwise, it will not render updates (even an empty string won't work)
        },
        min: 0
      }
    }
  };

  constructor(private mqttSubscriber: MqttSubscriberService, private sensorHistoryDataService: SensorHistoricDataService) { }

  ngOnInit(): void {

  }

  addRealtimeDataPointByStation(station: Station, datapoint: any) {
    this.datasets
            .filter(dataset => dataset.label == station.id)
            .forEach((dataset) => {
              dataset.data.push({ x: datapoint.timestamp, y: datapoint.value });
            });
    this.chart?.update(); // updating the chart here before removing the first datapoint
                          // to make the chart visualization smoother         
    this.removeFirstDataPoints();        
    this.chart?.update();
  }

  /**
   * removes the first datapoints in the graph for realtime graphs only.
   * With realtime, we only want to show the last X seconds/minutes/hours of data, so we need
   * to delete the data that's older than the the last X seconds/minutes/hours 
   */
  removeFirstDataPoints() {
    this.datasets
          .forEach((dataset) => {
            const firstDatapoint = dataset.data[0] as DataPoint;
            const lastDatapoint = dataset.data[dataset.data.length - 1] as DataPoint;
            const offset = this.calculateTimeOffsetForDataPoint(lastDatapoint.x);
            if (new Date(firstDatapoint.x) < offset) {
              dataset.data.shift();
            }
          });
  }

  /**
   * calculates the time offset between the provided date and the selected realtime data value
   * For example, if the last date is 2022-07-24T14:00:00 and the realtime data is 10 MINUTES, then
   * the function should return the lastDate - 10 MINUTES =  2022-07-24T13:50:00
   * 
   * @param lastDate 
   * @returns 
   */
  calculateTimeOffsetForDataPoint(lastDate: Date): Date {
    const offset = new Date(lastDate);
    
    if (this.dateRange.realtimeData?.unit === RealTimeDateUnit.MINUTES) {
      offset.setMinutes(offset.getMinutes() - this.dateRange.realtimeData?.time);
    } else if (this.dateRange.realtimeData?.unit === RealTimeDateUnit.HOURS) {
      offset.setHours(offset.getHours() - this.dateRange.realtimeData?.time);
    } else if (this.dateRange.realtimeData?.unit === RealTimeDateUnit.DAYS) {
      offset.setDate(offset.getDate() - this.dateRange.realtimeData?.time);
    }

    return offset
  }


  addDataPointsByStation(stationId: any, datapoints: Array<any>) {
    const grapDataPoints = datapoints.map(datapoint => {
      return { x: datapoint.timestamp, y: datapoint.value }
    });

    this.datasets
            .filter(dataset => dataset.label == stationId)
            .forEach((dataset) => {
              dataset.data = dataset.data.concat(grapDataPoints);
            });
    this.chart?.update();
  }

  // events
  public chartClicked({ event, active }: { event?: ChartEvent, active?: {}[] }): void {
    
  }

  public chartHovered({ event, active }: { event?: ChartEvent, active?: {}[] }): void {

  }

  ngOnChanges(changes: SimpleChanges) {
    for (const propName in changes) {
      const chng = changes[propName];
      const cur  = JSON.stringify(chng.currentValue);
      const prev = JSON.stringify(chng.previousValue);
    }
    if (this.validateInput()) {
      this.onSend()
    }
  }

  validateInput() {
    return (this.dateRange) && (this.metric) && (this.stations.length > 0);
  }

  public onSend() {
    this.cleanUpData();
  
    console.log('onSend!!');
    console.log('selected dates = ', this.dateRange);
    console.log('selected stations = ', this.stations);
    console.log('selected metrics = ', this.metric);

    this.stations.forEach((station) => {
      const dataset = { label: station.name, data: []};
      this.datasets.push(dataset);
    });

    this.initChart();

    // clean up previous subscriptions

    // reinitialize dates here for realtime

    from(this.stations).pipe(
      mergeMap((station) => this.sensorHistoryDataService.getStationDataBetweenDates(station.id, this.metric.id, this.dateRange.start, this.dateRange.end)),
      tap((response) => {
        const stationId = response.stationId;
        const datapoints = response.data;
        this.addDataPointsByStation(stationId, datapoints);
      }),
      tap(() => {
        // only if realtime is selected
        if (this.dateRange.realtime) {
          this.stations.forEach((station) => {
            console.log('create subscription');
            const mqttSubscription = this.mqttSubscriber.createSubscription(this.metric.id, station.id).subscribe((datapoint) => {
              this.addRealtimeDataPointByStation(station, datapoint);
            });
            this.mqttSubscriptions.push(mqttSubscription);
          })
        }
      })
    ).subscribe();
  }

  initChart() {
    console.log('ninit char')
    this.updateChartYLabel();
    this.updateChartYMinValue();
    this.chart?.update();
  }

  /**
   * updates the chart y label with the metric's unit
   */
  updateChartYLabel(): void {
    const chartOptions = this.chartOptions as any; // need to do this to access scales.y
    if (chartOptions.scales.y.title.text) {
      chartOptions.scales.y.title.text = this.metric.unit
    }
  }

  /**
   * 
   */
  updateChartYMinValue(): void {
    const chartOptions = this.chartOptions as any; // need to do this to access scales.y yikes!!
    chartOptions.scales.y.min = this.metric.metadata.min;
  }

  ngOnDestroy(): void {
    this.cleanUpMqttSubscriptions();
  }

  private cleanUpData() {
    this.cleanUpMqttSubscriptions();
    this.datasets = [];
  }

  private cleanUpMqttSubscriptions() {
    this.mqttSubscriptions.forEach(subscription => subscription.unsubscribe());
    this.mqttSubscriptions = [];
  }
  

}

interface DataPoint {
  x: Date,
  y: number
}
