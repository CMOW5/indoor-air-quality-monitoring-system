import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Station } from 'src/app/core/station/station.interface';

@Injectable({
  providedIn: 'root'
})
export class StationService {

  constructor(private httpClient: HttpClient) { }

  public getStations(): Observable<Array<Station>> {
    const url = `http://localhost:8080/station`

    return new Observable((observer) => {
      this.httpClient.get<Array<any>>(url).subscribe((response) => {
        observer.next(response);
      });      
    });
  }
}
