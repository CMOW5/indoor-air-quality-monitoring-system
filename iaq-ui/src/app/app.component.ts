import { AfterViewInit, Component, ComponentRef, ViewChild, ViewContainerRef } from '@angular/core';
import { MainChartDisplayComponent } from './feature/chart-display/main-chart-display/main-chart-display.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements AfterViewInit {
  
  

  @ViewChild("viewContainerRef", { read: ViewContainerRef }) viewContainerRef!: ViewContainerRef;

  chartUniqueKey: number = 0;
  chartReferences = Array<ComponentRef<MainChartDisplayComponent>>()

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.addSensorChart();
    }, 0)
  }

  addSensorChart() {
    let childComponentRef = this.viewContainerRef.createComponent(MainChartDisplayComponent);

    let childComponent = childComponentRef.instance;
    childComponent.unique_key = ++this.chartUniqueKey;
    childComponent.parentRef = this;

    // add reference for newly created component
    this.chartReferences.push(childComponentRef);
  }

  removeSensorChart(key: number) {
    if (this.viewContainerRef.length < 1) return;

    let componentRef = this.chartReferences.filter(
      x => x.instance.unique_key == key
    )[0];


    let vcrIndex: number = this.viewContainerRef.indexOf(componentRef.hostView);

    // removing component from container
    this.viewContainerRef.remove(vcrIndex);

    // removing component from the list
    this.chartReferences = this.chartReferences.filter(
      x => x.instance.unique_key !== key
    );
  }
}