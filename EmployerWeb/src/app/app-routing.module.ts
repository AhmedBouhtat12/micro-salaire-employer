import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { EmployersComponent } from './employers/employers.component';
import { FormationComponent } from './formation/formation.component';

import {AbsenceComponent} from './absence/absence.component';
import {DepartementComponent} from './departement/departement.component';

export const routes: Routes = [
  { path: 'employers', component: EmployersComponent },
  { path: 'formations', component: FormationComponent },
  { path: 'department', component: DepartementComponent },
  { path: 'absences', component: AbsenceComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
