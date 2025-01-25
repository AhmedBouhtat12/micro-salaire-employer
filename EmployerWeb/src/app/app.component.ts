import { Component } from '@angular/core';
import {NavigationEnd, Router, RouterModule} from '@angular/router';
import { NavbarComponent } from './navbar/navbar.component';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [RouterModule, NavbarComponent, NgIf],
})
export class AppComponent {

  showNavbar: boolean = true;
  constructor(private router: Router) {

    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {

        this.showNavbar = !event.url.includes('/login');
      }
    });
  }
}


