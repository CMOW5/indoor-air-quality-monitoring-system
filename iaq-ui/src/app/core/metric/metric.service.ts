import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Metric } from './metric.interface';

@Injectable({
  providedIn: 'root'
})
export class MetricService {

  constructor(private httpClient: HttpClient) { }

  public getMetrics(): Observable<Array<Metric>> {
    const url = environment.API_METRIC_PATH;

    return new Observable((observer) => {
      this.httpClient.get<Array<any>>(url).subscribe((response) => {
        observer.next(response);
      });      
    });
  }
}
