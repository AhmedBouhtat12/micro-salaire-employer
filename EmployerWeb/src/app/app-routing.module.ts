import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { EmployersComponent } from './employers/employers.component';
import { FormationComponent } from './formation/formation.component';

import {AbsenceComponent} from './absence/absence.component';
import {DepartementComponent} from './departement/departement.component';
import {AuthComponent} from './auth/auth.component';
import {HrDashboardComponent} from './hr-dashboard/hr-dashboard.component';
import {RhManagementComponent} from './rh-management/rh-management.component';
import {AuthGuard} from './guards/auth.guard';




export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' }, // default route
  { path: 'login', component: AuthComponent },

  {
    path: 'RESPONSABLE',
    data: { roles: ['RESPONSABLE'] },
    // canActivate: [AuthGuard],
    children: [
      { path: 'dashboard', component: HrDashboardComponent },
      { path: 'employers', component: EmployersComponent },
      { path: 'formations', component: FormationComponent },
      { path: 'absences', component: AbsenceComponent },
      { path: 'department', component: DepartementComponent }
    ]
  },

  {
    path: 'EMPLOYER',
    data: { roles: ['EMPLOYER'] },
    children: [


      { path: 'rh-management', component: RhManagementComponent },
      { path: '', redirectTo: '/hr/dashboard', pathMatch: 'full' }
    ]
  }
];




@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
