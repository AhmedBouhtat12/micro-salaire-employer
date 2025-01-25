import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Formation, absences, User } from '../employer.model'; // Assurez-vous que vos modèles sont bien définis

@Injectable({
  providedIn: 'root',
})
export class RhManagementService {
  private apiUrl = 'http://localhost:9002/USERR-SERVICE/api/Rh'; // URL du service

  constructor(private http: HttpClient) {}

  // Obtenir les absences d'un employé
  getAbsences(): Observable<absences[]> {
    return this.http.get<absences[]>(`${this.apiUrl}/20/absences`);
  }

  // Obtenir les formations d'un employé
  getFormations(): Observable<Formation[]> {
    return this.http.get<Formation[]>(`${this.apiUrl}/20/formations`);
  }

  // Obtenir le département d'un employé
  getDepartement(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/20/departement`);
  }
}
