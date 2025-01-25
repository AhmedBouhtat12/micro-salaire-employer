import { Component, OnInit } from '@angular/core';
import { RhManagementService } from './rh-management.service';
import { Formation, absences, User } from '../employer.model';
import {DatePipe, NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-rh-management',
  templateUrl: './rh-management.component.html',
  styleUrls: ['./rh-management.component.css'],
  imports: [
    NgIf,
    NgForOf,
    DatePipe
  ],
})
export class RhManagementComponent implements OnInit {
  formations: Formation[] = [];
  absences: absences[] = [];
  departement: any = null;
  profile: User | null = null;
  error: string | null = null;

  constructor(private rhManagementService: RhManagementService) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    // Obtenez les absences
    this.rhManagementService.getAbsences().subscribe(
      (data) => {
        this.absences = data;
      },
      (error) => {
        this.error = 'Erreur lors de la récupération des absences';
      }
    );

    // Obtenez les formations
    this.rhManagementService.getFormations().subscribe(
      (data) => {
        this.formations = data;
      },
      (error) => {
        this.error = 'Erreur lors de la récupération des formations';
      }
    );

    // Obtenez le département
    this.rhManagementService.getDepartement().subscribe(
      (data) => {
        this.departement = data;
      },
      (error) => {
        this.error = 'Erreur lors de la récupération du département';
      }
    );
  }
}
