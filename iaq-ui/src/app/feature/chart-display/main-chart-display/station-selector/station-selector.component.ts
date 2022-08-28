import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { StationService } from 'src/app/core/station/station.service';
import { Station } from '../../../../core/station/station.interface';

@Component({
  selector: 'app-station-selector',
  templateUrl: './station-selector.component.html',
  styleUrls: ['./station-selector.component.sass']
})
export class StationSelectorComponent implements OnInit {

  stationForm = new FormControl('', [Validators.required]);

  stations: Station[] = []; 

  @Output() stationMetricSelected = new EventEmitter<Array<Station>>();

  constructor(private stationService: StationService) { }

  ngOnInit(): void {
    this.stationForm.valueChanges.subscribe(() => {
      this.emitOnStationMetricSelected();
    });

    this.getStations();
  }

  getStations() {
    this.stationService.getStations().subscribe(stations => {
      this.stations = stations;
      const principalStation = this.stations.find(station => station.metadata.principal === true);
      this.stationForm.setValue([principalStation]);
    })
  }

  get isValid() {
    return this.stationForm.valid;
  }

  emitOnStationMetricSelected() {
    if (!this.isValid) return;
    
    this.stationMetricSelected.emit(this.stationForm.value);
  }

}
