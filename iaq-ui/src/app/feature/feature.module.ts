import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartDisplayModule } from './chart-display/chart-display.module';
import { DashboardModule } from './dashboard/dashboard.module';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ChartDisplayModule,
    DashboardModule
  ]
})
export class FeatureModule { }
