import { Component, OnInit } from '@angular/core';
import { EmployerService } from '../employer.service';

import { AuthService } from '../auth/auth.service';
import {AbsenceService} from '../absence/absence.service';
import {DepartementService} from '../departement/departement.service';
import {FormationService} from '../formation/formation.service';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-hr-dashboard',
  templateUrl: './hr-dashboard.component.html',
  imports: [
    RouterLink
  ],
  styleUrls: ['./hr-dashboard.component.css']
})
export class HrDashboardComponent implements OnInit {
  totalEmployees = 0;
  totalAbsences = 0;
  totalDepartements = 0;
  totalFormations = 0;
  userRole: string = '';

  constructor(
    private employeeService: EmployerService,
    private absenceService: AbsenceService,
    private departementService: DepartementService,
    private formationService: FormationService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.loadDashboardData();
    this.userRole = this.authService.getRoleFromLocalStorage();
  }

  private loadDashboardData() {
    // Compter les employés ayant le rôle 'EMPLOYER'
    this.employeeService.getAllEmployers().subscribe(employees => {
      const employers = employees.filter(employee => employee.role === 'EMPLOYER');
      this.totalEmployees = employers.length;
    });

    // Compter le nombre total d'absences
    this.absenceService.getAllAbsences().subscribe(absences => {
      this.totalAbsences = absences.length;
    });

    // Compter le nombre total de départements
    this.departementService.getAllDepartements().subscribe(departments => {
      this.totalDepartements = departments.length;
    });

    // Compter le nombre total de formations
    this.formationService.getAllFormations().subscribe(formations => {
      this.totalFormations = formations.length;
    });
  }
}
