import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Formation, absences, User } from '../employer.model';

@Injectable({
  providedIn: 'root',
})
export class RhManagementService {
  private apiUrl = 'http://localhost:9002/USERR-SERVICE/api/Rh';

  constructor(private http: HttpClient) {}



  getAbsences(): Observable<absences[]> {
    const username = localStorage.getItem('username');
    return this.http.get<absences[]>(`${this.apiUrl}/${username}/absences`);
  }


  getFormations(): Observable<Formation[]> {
    const username = localStorage.getItem('username');
    return this.http.get<Formation[]>(`${this.apiUrl}/${username}/formations`);
  }


  getDepartement(): Observable<any> {
    const username = localStorage.getItem('username');
    return this.http.get<any>(`${this.apiUrl}/${username}/departement`);
  }

}
