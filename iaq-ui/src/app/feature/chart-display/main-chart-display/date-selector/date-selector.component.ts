import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { DateRange } from './date-range.interface';

@Component({
  selector: 'app-date-selector',
  templateUrl: './date-selector.component.html',
  styleUrls: ['./date-selector.component.sass']
})
export class DateSelectorComponent implements OnInit {

  DEFAULT_DATE_TYPE = 'realtime';
 
  dateTypes = ['realtime', 'historic'];

  dateTypeForm = new FormControl('', Validators.required);

  @Output() timeSelected = new EventEmitter<DateRange>();

  constructor() { }

  ngOnInit(): void {
    this.dateTypeForm.setValue(this.DEFAULT_DATE_TYPE);
  }


  onTimeSelected(dateRange: DateRange) {
    this.timeSelected.emit(dateRange);
  }

}
