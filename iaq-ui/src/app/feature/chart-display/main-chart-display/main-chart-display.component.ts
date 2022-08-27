import { Component, ViewChild, OnInit, OnDestroy } from '@angular/core';
import { BaseChartDirective } from 'ng2-charts';
import { AppComponent } from 'src/app/app.component';
import { DateRange } from './date-selector/date-range.interface';
import { Metric } from '../../../core/metric/metric.interface';
import { Station } from '../../../core/station/station.interface';


@Component({
  selector: 'app-main-chart-display',
  templateUrl: './main-chart-display.component.html',
  styleUrls: ['./main-chart-display.component.sass']
})
export class MainChartDisplayComponent implements OnInit, OnDestroy {

  public unique_key= 0;
  public parentRef!: AppComponent;

  @ViewChild(BaseChartDirective) chart?: BaseChartDirective;

  // form data
  public selectedMetrics: Array<Metric> = [];

  public selectedStations: Array<Station> = [];

  public selectedDateRange!: DateRange;


  constructor() { }

  ngOnInit(): void {
  }

  onTimeSelected(dateRange: DateRange) {
    console.log('onTimeSelected = ', dateRange);
    this.selectedDateRange = dateRange;
  }

  onStationSelected(stations: Array<Station>) {
    console.log('onStationSelected = ', event);
    this.selectedStations = stations;
  }

  onMetricsSelected(metrics: Array<Metric>) {
    console.log('onMetricSelected = ', metrics);
    this.selectedMetrics = metrics;
  }

  removeChart() {
    this.parentRef.removeSensorChart(this.unique_key)
  }


  ngOnDestroy(): void {
   
  }

  onSend() {

  }

}
