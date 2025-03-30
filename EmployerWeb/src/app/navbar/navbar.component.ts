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
    const role = localStorage.getItem('role');
    if (role === 'RESPONSABLE') {
      this.isResponsable = true;
      this.isEmployer = false;
    } else if (role === 'EMPLOYER') {
      this.isEmployer = true;
      this.isResponsable = false;
    }
  }

  logout(): void {
    this.authService.clearUserData();
    this.router.navigate(['/login']);
  }
}
