import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {User} from './employer.model';


@Injectable({
  providedIn: 'root'
})
export class EmployerService {
  private apiUrl = 'http://localhost:9002/USERR-SERVICE/api/employer';



  constructor(private http: HttpClient) {}

  addEmployer(employer: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/add`, employer, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }

  getAllEmployers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }


  getEmployerById(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }


  updateEmployer(id: number, employer: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, employer, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }


  deleteEmployer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getEmployerDetails(id: number | undefined): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}/details`);
  }


  addFormationToEmployer(employerId: number | undefined, formationId: number): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${employerId}/formations/${formationId}`, {});
  }
  addAbsencesToEmployer(employerId: number | undefined, absencesId: number): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${employerId}/absences/${absencesId}`, {});
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
  register(user: User): Observable<User> {
    return this.http.post<User>(`http://localhost:9002/USERR-SERVICE/auth/register`, user).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('Error Status:', error.status);
        console.error('Error Message:', error.message);
        return throwError(() => new Error('An error occurred while adding the employer'));
      })
    );
  }

}
