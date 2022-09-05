import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { DateRange } from '../date-range.interface';
import { RealTimeDateUnit } from './real-time-unit.enum';

@Component({
  selector: 'app-realtime-date-selector',
  templateUrl: './realtime-date-selector.component.html',
  styleUrls: ['./realtime-date-selector.component.sass']
})
export class RealtimeDateSelectorComponent implements OnInit {

  // todo: fetch this from backend
  DEFAULT_REALTIME_TIME_MINUTES = 1

  // todo: fetch this from backend
  realTimeTypes = [
    RealTimeDateUnit.MINUTES, 
    RealTimeDateUnit.HOURS, 
    RealTimeDateUnit.DAYS
  ];

  realtimeForm = new FormGroup({
    timeUnitFormControl: new FormControl('', [Validators.required]),
    timeFormControl: new FormControl('', [Validators.required, Validators.min(1)])
  });

  @Output() timeSelected = new EventEmitter<DateRange>();

  constructor() { }

  ngOnInit(): void {
    this.realtimeForm.valueChanges.subscribe(() => {
      this.emitOnTimeSelected();
    });
  }

  public ngAfterViewInit(): void {
    // hacky call here!!
    // Wait a tick first so the parent 'date-selector-component' can get a valid reference
    // to the ViewChild components. If we do this without setTimeout, 'date-selector-component' will get an undefined reference
    // for this component, causing the isValid function to fail
    setTimeout(() => {
      this.realtimeForm.patchValue({
        timeFormControl: this.DEFAULT_REALTIME_TIME_MINUTES,
        timeUnitFormControl: RealTimeDateUnit.MINUTES,
      });
    }, 0);
  }
  

  get timeUnitFormControl() {
    return this.realtimeForm.get('timeUnitFormControl');
  }

  get timeFormControl() {
    return this.realtimeForm.get('timeFormControl');
  }

  public get isValid() {
    return this.realtimeForm.valid;
  }

  emitOnTimeSelected() {
    if (!this.isValid) return;

    let start = new Date();
    let end = new Date();
    
    // substract the input value from the start date using the proper time unit
    if (this.timeUnitFormControl?.value === RealTimeDateUnit.MINUTES) {
      start.setMinutes(start.getMinutes() - this.timeFormControl?.value);
    } else if (this.timeUnitFormControl?.value === RealTimeDateUnit.HOURS) {
      start.setHours(start.getHours() - this.timeFormControl?.value);
    } else if (this.timeUnitFormControl?.value === RealTimeDateUnit.DAYS) {
      start.setDate(start.getDate() - this.timeFormControl?.value);
    }
    
    this.timeSelected.emit({
      start: start,
      end: end,
      realtime: true,
      realtimeData: {
        unit: this.timeUnitFormControl?.value,
        time: this.timeFormControl?.value
      }
    });
  }
}
