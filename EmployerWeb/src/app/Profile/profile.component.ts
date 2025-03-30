import { Component, OnInit } from '@angular/core';
import { ProfileService } from './profile.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-employer-form',
  templateUrl: './profile.component.html',
  imports: [
    NgIf
  ],
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  username: string | undefined;
  nom: string | undefined;
  prenom: string | undefined;
  email: string | undefined;
  password: string | undefined;
  address: string | undefined;
  telephone: string | undefined;
  contractType: string | undefined;

  constructor(private employerFormService: ProfileService) {}

  ngOnInit(): void {
    // Get username from localStorage
    const username = localStorage.getItem('username');
    if (username) {
      this.username = username;  // Assign to component property
      this.getUserProfile();
    } else {
      console.error('Username is not available.');
    }
  }

  getUserProfile(): void {
    this.employerFormService.getUserProfile(this.username!).subscribe(profile => {
      if (profile) {
        this.nom = profile.nom;
        this.prenom = profile.prenom;
        this.email = profile.email;
        this.password = profile.password;
        this.address = profile.address;
        this.telephone = profile.telephone;
        this.contractType = profile.contractType;
      } else {
        console.error('Profile data is not available.');
      }
    });
  }
}
