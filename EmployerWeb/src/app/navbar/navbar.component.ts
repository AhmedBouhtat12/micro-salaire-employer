import { Component, OnInit } from '@angular/core';
import {Router, NavigationEnd, RouterLink} from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { filter } from 'rxjs/operators';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  imports: [
    NgIf,
    RouterLink
  ]
})
export class NavbarComponent implements OnInit {
  isResponsable: boolean = false;
  isEmployer: boolean = false;
  isLoginPage: boolean = false; // Variable to track if the user is on the login page

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Ecoute des changements de route
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.isLoginPage = event.urlAfterRedirects === '/login'; // Vérifier si on est sur la page de connexion
      });

    const role = localStorage.getItem('role');
    if (role) {
      this.isResponsable = role === 'RESPONSABLE';
      this.isEmployer = role === 'EMPLOYER';
    }
  }

  logout(): void {
    this.authService.clearUserData();
    this.router.navigate(['/login']);
  }
}
