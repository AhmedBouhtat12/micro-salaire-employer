import { Component } from '@angular/core';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  styleUrls: ['./auth.component.css']
})
export class AuthComponent {
  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;

      // Call the login method
      this.authService.login(username, password).subscribe({
        next: (token: string) => {
          if (token) {
            // Validate the token and get the role
            this.authService.validateToken(token).subscribe({
              next: (response: { role: string }) => {
                console.log('Role:', response.role);

                // Save user data and token to localStorage
                this.authService.saveUserData(username, response.role, token);

                // Redirect based on role
                if (response.role === 'RESPONSABLE') {
                  this.router.navigate(['/RESPONSABLE/dashboard']);
                } else if (response.role === 'EMPLOYER') {
                  this.router.navigate(['/EMPLOYER/rh-management']);
                }
              },
              error: (error) => {
                console.error('Error validating token:', error);
              }
            });
          }
        },
        error: (error) => {
          console.error('Login failed:', error);
        }
      });
    }
  }
}
