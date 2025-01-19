import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DepartementService {
  private apiUrl = 'http://localhost:9002/SERVICE-DEPARTEMENT/api/departements';

  constructor(private http: HttpClient) { }

  // Fetch all departements
  getAllDepartements(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Fetch departement details by ID
  getDepartementDetails(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}/details`);
  }

  // Create or update a departement
  saveDepartement(departement: any): Observable<any> {
    return this.http.post(this.apiUrl, departement);
  }

  // Delete a departement by ID
  deleteDepartement(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
