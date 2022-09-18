import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SensorHistoricDataService {

  constructor(private httpClient: HttpClient) { }

  public getStationDataBetweenDates(station: string, metric: string, start: Date, end: Date): Observable<any> {
    let params = new HttpParams()
                      .set('start', start.toISOString())
                      .set('end', end.toISOString())
                      .set('sort', 'ASC');

    const url = environment.buildHistoricUrl(station, metric); 

    return new Observable((observer) => {
      this.httpClient.get<Array<any>>(url, {params: params}).subscribe((response) => {
        console.log('response historic data from server =', response);
        observer.next(response);
      });      
    });
  }
}
