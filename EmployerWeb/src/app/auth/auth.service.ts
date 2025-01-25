import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuthenticated :boolean = false;
  token! :any;
  profile : any;
  private apiUrl = 'http://localhost:9002/USERR-SERVICE/auth';


  constructor(private http: HttpClient,private router: Router) { }

  // Login method
  login(username: string, password: string): Observable<string> {
    const body = { username, password };
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.post(`${this.apiUrl}/token`, body, { headers, responseType: 'text' });
  }

  // Validate token method
  validateToken(token: string): Observable<{ role: string }> {
    return this.http.get<{ role: string }>(`${this.apiUrl}/role`, {
      params: { token }
    });
  }

  // Save user data to localStorage
  saveUserData(username: string, role: string, token: string): void {
    localStorage.setItem('username', username);
    localStorage.setItem('role', role);
    localStorage.setItem('token', token);
  }
  // logout(): void {
  //   // Clear user data from localStorage
  //   this.clearUserData();
  //
  //   // Redirect to the login page
  //   this.router.navigate(['/login']);
  // }

  // Get user data from localStorage
  getUserData(): { username: string, role: string, token: string } | null {
    const username = localStorage.getItem('username');
    const role = localStorage.getItem('role');
    const token = localStorage.getItem('token');
    return username && role && token ? { username, role, token } : null;
  }

  // Clear user data from localStorage
  clearUserData(): void {
    localStorage.removeItem('username');
    localStorage.removeItem('role');
    localStorage.removeItem('token');
  }

  // Get token from localStorage
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  loadProfile() {
    return this.http.get("http://localhost:9010/auth/profile");
  }

  logout() {
    this.profile=undefined;
    this.token=undefined;
  }
  hasRole(role : string):boolean{
    let filter = this.profile.authorities.filter((a:any)=>a.authority==role);
    return filter.length>0;
  }


  getUserProfile(token: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/profile?token=${token}`);
  }
  getRoleFromLocalStorage(): string {
    return localStorage.getItem('role') || '';
  }

}
