import { Component, ElementRef, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MetricService } from 'src/app/core/metric/metric.service';
import { Metric } from '../../../../core/metric/metric.interface';
import { map, Observable, startWith } from 'rxjs';

@Component({
  selector: 'app-metrics-selector',
  templateUrl: './metrics-selector.component.html',
  styleUrls: ['./metrics-selector.component.sass']
})
export class MetricsSelectorComponent implements OnInit {

  separatorKeysCodes: number[] = [ENTER, COMMA];

  metricsFormControl = new FormControl('');
  
  filteredMetrics!: Observable<string[]>;
  
  selectedMetrics: Metric[] = [];
    
  allMetrics: Metric[] = [];

  @ViewChild('metricInput') metricInput!: ElementRef<HTMLInputElement>;

  @Output() metricsSelected = new EventEmitter<Array<Metric>>();

  constructor(private metricService: MetricService) { 
   this.initFilteredMetrics();
  }

  initFilteredMetrics() {
    this.filteredMetrics = this.metricsFormControl.valueChanges.pipe(
      startWith(null),
      map((metric: string | null) => (metric ? 
        this._filter(metric) : 
        this.allMetrics.filter(metric => !this.selectedMetrics.includes(metric)).map(metric => metric.name).slice())),
    );
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.allMetrics.filter(metric => !this.selectedMetrics.includes(metric)) 
                          .map(metric => metric.name)
                          .filter(metric => metric.toLowerCase().includes(filterValue));
  }


  ngOnInit(): void {
    this.metricService.getMetrics().subscribe(metrics => {
      this.allMetrics = metrics
      this.initFilteredMetrics();
      // todo: set a conditional for this maybe? (to pre-select all the metrics for station x)
      this.selectedMetrics = this.allMetrics.slice();
      this.emitOnStationMetricSelected();
    });
  }

  addNewMetricByName(name = '') {
    const metric = this.allMetrics.find(metric => metric.name.toLowerCase() === name.toLowerCase());
    if (metric && (this.selectedMetrics.indexOf(metric) < 0)) {
      this.selectedMetrics = this.selectedMetrics.concat(metric);
    }
  }

  addByKeyboardInput(event: MatChipInputEvent): void {
    const name = (event.value || '').trim();

    this.addNewMetricByName(name);

    // Clear the input value
    event.chipInput!.clear();
    this.clearInputs();
    this.emitOnStationMetricSelected();
    
  }

  remove(metric: Metric): void {
    const index = this.selectedMetrics.indexOf(metric);

    if (index >= 0) {
      this.selectedMetrics.splice(index, 1);
    }

    this.clearInputs();
    this.emitOnStationMetricSelected();
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const name = event.option.value;
    this.addNewMetricByName(name);
    this.clearInputs();
    this.emitOnStationMetricSelected();
  }

  clearInputs() {
    this.metricsFormControl.setValue(null);
    this.metricInput.nativeElement.value = '';
  }

  emitOnStationMetricSelected() {
   this.metricsSelected.emit(this.selectedMetrics);
  }

}
