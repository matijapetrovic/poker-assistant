import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PokerBotParams } from './PokerBotParams';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class PokerBotService {
  //url = `${environment.apiUrl}/api`;
  url = '';

  constructor(private http: HttpClient) { }

  add(params: PokerBotParams): Observable<void[]> {
    return this.http.post<void[]>(`${this.url}/`, params);
  }

}