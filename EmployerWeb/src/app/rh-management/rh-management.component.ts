import { Component, OnInit } from '@angular/core';
import { RhManagementService } from './rh-management.service';
import { Formation, absences } from '../employer.model';
import { DatePipe, NgClass, NgForOf } from '@angular/common';

@Component({
  selector: 'app-rh-management',
  templateUrl: './rh-management.component.html',
  imports: [DatePipe, NgForOf],
  styleUrls: ['./rh-management.component.css'],
})
export class RhManagementComponent implements OnInit {
  absences: absences[] = [];
  formations: Formation[] = [];
  departement: any;
  userProfile: any;

  constructor(private rhManagementService: RhManagementService) {}

  ngOnInit(): void {
    // Récupérer les absences
    this.rhManagementService.getAbsences().subscribe({
      next: (data) => (this.absences = data),
      error: (error) => console.error('Erreur lors de la récupération des absences:', error),
    });

    // Récupérer les formations
    this.rhManagementService.getFormations().subscribe({
      next: (data) => (this.formations = data),
      error: (error) => console.error('Erreur lors de la récupération des formations:', error),
    });

    // Récupérer le département
    this.rhManagementService.getDepartement().subscribe({
      next: (data) => (this.departement = data),
      error: (error) => console.error('Erreur lors de la récupération du département:', error),
    });

  }
}
