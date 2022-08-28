import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { DateRange } from './date-range.interface';
import { HistoricDateSelectorComponent } from './historic-date-selector/historic-date-selector.component';
import { RealtimeDateSelectorComponent } from './realtime-date-selector/realtime-date-selector.component';

@Component({
  selector: 'app-date-selector',
  templateUrl: './date-selector.component.html',
  styleUrls: ['./date-selector.component.sass']
})
export class DateSelectorComponent implements OnInit {

  DEFAULT_DATE_TYPE = 'realtime';
  
  // todo: type this
  dateTypes = ['realtime', 'historic'];

  dateTypeForm = new FormControl('', Validators.required);

  @ViewChild('realtimeDateSelector')
  private realtimeDateSelectorComponent!: RealtimeDateSelectorComponent;

  @ViewChild('historicDateSelector')
  private historicDateSelectorComponent!: HistoricDateSelectorComponent;

  @Output() timeSelected = new EventEmitter<DateRange>();

  constructor() { }

  ngOnInit(): void {
    this.dateTypeForm.setValue(this.DEFAULT_DATE_TYPE);
  }
  
  
  onTimeSelected(dateRange: DateRange) {
    if (!this.isValid()) return;

     this.timeSelected.emit(dateRange);
  }

  public isValid(): boolean {
    return  this.dateTypeForm.valid && 
            (this.dateTypeForm.value === 'realtime' ? this.realtimeDateSelectorComponent?.isValid : 
                                                      this.historicDateSelectorComponent?.isValid);
  }

}
