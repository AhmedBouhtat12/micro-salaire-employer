import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FormationService {
  private apiUrl = 'http://localhost:9002/SERVICE-FORMATION/api/formations';

  constructor(private http: HttpClient) {}

  getAllFormations(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  createFormation(formation: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, formation);
  }

  updateFormation(id: number, formation: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, formation);
  }

  deleteFormation(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
