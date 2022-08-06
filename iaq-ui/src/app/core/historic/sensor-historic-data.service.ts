import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SensorHistoricDataService {

  constructor(private httpClient: HttpClient) { }

  public getStationDataBetweenDates(station: string, metric: string, start: Date, end: Date): Observable<any> {
    let params = new HttpParams();
    params = params.set('start', start.toISOString());
    params = params.set('end', end.toISOString());
    const url = `http://localhost:8080/station/${station}/metric/${metric}/historic`

    return new Observable((observer) => {
      this.httpClient.get<Array<any>>(url, {params: params}).subscribe((response) => {
        console.log('response historic data from server =', response);
        observer.next(response);
      });      
    });
  }

  private convertToDto() {

  }
}
