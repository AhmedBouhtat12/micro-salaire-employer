import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employer } from './employer.model';

@Injectable({
  providedIn: 'root'
})
export class EmployerService {
  private apiUrl = 'http://localhost:9002/SERVICE-EMPLOYER/api/employers';

  constructor(private http: HttpClient) {}

  addEmployer(employer: Employer): Observable<Employer> {
    return this.http.post<Employer>(`${this.apiUrl}/add`, employer, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }


  getAllEmployers(): Observable<Employer[]> {
    return this.http.get<Employer[]>(this.apiUrl);
  }


  getEmployerById(id: number): Observable<Employer> {
    return this.http.get<Employer>(`${this.apiUrl}/${id}`);
  }


  updateEmployer(id: number, employer: Employer): Observable<Employer> {
    return this.http.put<Employer>(`${this.apiUrl}/${id}`, employer, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }


  deleteEmployer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getEmployerDetails(id: number | undefined): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}/details`);
  }


  addFormationToEmployer(employerId: number | undefined, formationId: number): Observable<Employer> {
    return this.http.put<Employer>(`${this.apiUrl}/${employerId}/formations/${formationId}`, {});
  }
  addAbsencesToEmployer(employerId: number | undefined, absencesId: number): Observable<Employer> {
    return this.http.put<Employer>(`${this.apiUrl}/${employerId}/absences/${absencesId}`, {});
  }

  getAllEmployersDetails(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/details`);
  }


  getFormationsByEmployer(employerId: number | undefined): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${employerId}/formations`);
  }

  getAbsencesByEmployer(employerId: number | undefined): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${employerId}/absences`);
  }
  getDepartementByEmployer(employerId: number | undefined): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${employerId}/departement`);
  }

  getAllFormations(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:9002/SERVICE-FORMATION/api/formations');
  }
  getAllAbsences(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:9002/SERVICE-ABSENCES/api/absences');
  }
  getDepartementById(id: number): Observable<{ nom: string }> {
    return this.http.get<{ nom: string }>(`http://localhost:9002/SERVICE-DEPARTEMENT/api/departements/${id}`);
  }

  removeFormationFromEmployer(employerId: number | undefined, formationId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${employerId}/formations/${formationId}`);
  }
  removeAbsenceFromEmployer(employerId: number | undefined, absenceId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${employerId}/Absence/${absenceId}`);
  }

}
