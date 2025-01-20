import { Component } from '@angular/core';

import { Router } from '@angular/router';
import {AuthService} from './auth.service';
import {FormsModule} from '@angular/forms';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [
    FormsModule,
    NgIf
  ]
})
export class LoginComponent {
  credentials = {
    username: '',
    password: '',
  };
  errorMessage: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.authService.login(this.credentials).subscribe(
      (response: { token: string; }) => {
        localStorage.setItem('token', response.token); // Stocker le token JWT
        this.router.navigate(['/']); // Rediriger vers la page d'accueil ou une autre page
      },
      (error) => {
        this.errorMessage = 'Nom d’utilisateur ou mot de passe incorrect.';
      }
    );
  }
}
