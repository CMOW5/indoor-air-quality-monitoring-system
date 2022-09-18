import { Component, Input, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { ChartEvent, ChartOptions } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { Subscription } from 'rxjs';
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

  public datasets: Array<ChartDataSet> = [
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
        radius: 1
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
            'hour': 'MMM DD HH',
            'day': 'MMM DD HH',
            'week': 'MMM DD HH',
            'month': 'MMM DD HH',
            'quarter': 'MMM DD HH',
            'year': 'MMM DD HH',
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
    this.removeFirstDataPoints(station);        
    this.chart?.update();
  }

  /**
   * removes the first datapoints in the graph for realtime graphs only.
   * With realtime, we only want to show the last X seconds/minutes/hours of data, so we need
   * to delete the data that's older than the the last X seconds/minutes/hours 
   */
  removeFirstDataPoints(station: Station) {
    this.datasets
          .filter(dataset => dataset.label == station.id)
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

    this.stations.forEach((station) => {
      const randomColor = this.randomRgba();
      const backgroundColor = station.metadata.principal ? this.metric.metadata.backgroundColor : this.setRgbaAlpha(randomColor, 0.5);
      const borderColor = station.metadata.principal ? this.metric.metadata.color : this.setRgbaAlpha(randomColor, 1);

      const dataset = { 
        label: station.name, 
        data: [], 
        backgroundColor: backgroundColor,
        borderColor: borderColor
      };
      this.datasets.push(dataset);
    });

    this.initChart();

    // clean up previous subscriptions

    // reinitialize dates here for realtime

    this.stations.forEach(station => {
      this.sensorHistoryDataService.getStationDataBetweenDates(station.id, this.metric.id, this.dateRange.start, this.dateRange.end)
          .subscribe(response => {
            const stationId = response.stationId;
            const datapoints = response.data;
            this.addDataPointsByStation(stationId, datapoints);

            if (this.dateRange.realtime) {
              const mqttSubscription = this.mqttSubscriber.createSubscription(this.metric.id, station.id).subscribe((datapoint) => {
                this.addRealtimeDataPointByStation(station, datapoint);
              });
              this.mqttSubscriptions.push(mqttSubscription);
            }

          });
    });
  }

  initChart() {
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

  // TODO: improve this, maybe set the color by station
  /**
   * a hacky way of generating random colors
   * @returns 
   */
  private randomRgba() {
    const s = 255;
    return 'rgba(' + Math.round(Math.random()*s) + ',' 
                   + Math.round(Math.random()*s) + ',' 
                   + Math.round(Math.random()*s) + ',' 
                   + Math.random().toFixed(1) + ')';
  }
  
  /**
   * set the alpha value for the given rgba
   * @param rgba the rgba to be changed
   * @param alpha the new alpha to be applied
   * @returns a new rgba with the original rgb and the new alpha
   */
  private setRgbaAlpha(rgba: string, alpha: number) {
    const index = rgba.lastIndexOf(',');
    return rgba.substring(0, index) + `, ${alpha})`;
  }                 
}

interface DataPoint {
  x: Date,
  y: number
}

interface ChartDataSet  {
  data: Array<any>,
  label: string
  backgroundColor?: string
}