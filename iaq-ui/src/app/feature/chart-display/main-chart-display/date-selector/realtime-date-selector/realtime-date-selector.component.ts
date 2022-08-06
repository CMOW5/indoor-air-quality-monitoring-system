import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { DateRange } from '../date-range.interface';
import { RealTimeDateUnit } from './real-time-unit.enum';

@Component({
  selector: 'app-realtime-date-selector',
  templateUrl: './realtime-date-selector.component.html',
  styleUrls: ['./realtime-date-selector.component.sass']
})
export class RealtimeDateSelectorComponent implements OnInit {

  // todo: fetch this
  DEFAULT_REALTIME_TIME_MINUTES = 1

  // todo: fetch this
  realTimeTypes = [
    RealTimeDateUnit.MINUTES, 
    RealTimeDateUnit.HOURS, 
    RealTimeDateUnit.DAYS
  ];

  timeTypeFormControl = new FormControl('', [Validators.required]);

  timeFormControl = new FormControl('', [Validators.required]);

  @Output() timeSelected = new EventEmitter<DateRange>();

  constructor() { }

  ngOnInit(): void {
    this.timeFormControl.valueChanges.subscribe(() => {
      this.emitOnTimeSelected();
    })

    this.timeTypeFormControl.valueChanges.subscribe(() => {
      this.emitOnTimeSelected();
    })
    
    this.timeTypeFormControl.setValue(RealTimeDateUnit.MINUTES);
    this.timeFormControl.setValue(this.DEFAULT_REALTIME_TIME_MINUTES);
  }

  emitOnTimeSelected() {
    let start = new Date();
    let end = new Date();
    
    // substract the input value from the start date using the proper time unit
    if (this.timeTypeFormControl.value === RealTimeDateUnit.MINUTES) {
      start.setMinutes(start.getMinutes() - this.timeFormControl.value);
    } else if (this.timeTypeFormControl.value === RealTimeDateUnit.HOURS) {
      start.setHours(start.getHours() - this.timeFormControl.value);
    } else if (this.timeTypeFormControl.value === RealTimeDateUnit.DAYS) {
      start.setDate(start.getDate() - this.timeFormControl.value);
    }
    
    this.timeSelected.emit({
      start: start,
      end: end,
      realtime: true,
      realtimeData: {
        unit: this.timeTypeFormControl.value,
        time: this.timeFormControl.value
      }
    });
  }
}
