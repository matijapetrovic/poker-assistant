import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { BotOptions } from './bot-options';

@Injectable({
  providedIn: 'root'
})
export class PokerBotService {
  private apiUrl = `${environment.apiUrl}/game`
  constructor(private http: HttpClient) { }

  startGame(botOptions: BotOptions): Observable<Blob> {
    return this.http.post(this.apiUrl, botOptions, {responseType: 'blob'});
  }
}
