import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DateRange } from '../date-range.interface';

@Component({
  selector: 'app-historic-date-selector',
  templateUrl: './historic-date-selector.component.html',
  styleUrls: ['./historic-date-selector.component.sass']
})
export class HistoricDateSelectorComponent implements OnInit {

  dateRangeForm = new FormGroup({
    start: new FormControl('', Validators.required),
    end: new FormControl('', Validators.required),
  });

  @Output() timeSelected = new EventEmitter<DateRange>();

  constructor() { }

  ngOnInit(): void {
    this.dateRangeForm.valueChanges.subscribe(() => {
      this.emitOnTimeSelected();
    });
  }

  emitOnTimeSelected() {
    if (!this.isValid) return;

    this.timeSelected.emit({
      ...this.dateRangeForm.value,
      realtime: false
    });
  }

  get isValid(): boolean {
    return this.dateRangeForm.valid;
  }

}