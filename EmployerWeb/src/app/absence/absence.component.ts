import { Component, OnInit } from '@angular/core';
import { AbsenceService } from './absence.service';
import {FormsModule} from '@angular/forms';
import {DatePipe, NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-absence',
  templateUrl: './absence.component.html',
  imports: [
    FormsModule,
    NgIf,
    NgForOf,
    DatePipe
  ],
  styleUrls: ['./absence.component.css']
})
export class AbsenceComponent implements OnInit {
  absences: any[] = [];
  newAbsence: any = {
    id: null,
    type: '',
    startDate: '',
    endDate: '',
    status: '',
    managerComments: ''
  };
  isEditing: boolean = false; // Variable pour savoir si on est en mode édition ou ajout
  showTable: boolean = true;

  constructor(private absenceService: AbsenceService) {}

  ngOnInit(): void {
    this.loadAbsences();
  }


  loadAbsences(): void {
    this.absenceService.getAllAbsences().subscribe((data) => {
      this.absences = data;
    });
  }


  saveAbsence(): void {
    if (this.isEditing) {
      this.absenceService.updateAbsence(this.newAbsence.id, this.newAbsence).subscribe(() => {
        this.loadAbsences();
        this.resetForm();
      });
    } else {
      this.absenceService.addAbsence(this.newAbsence).subscribe(() => {
        this.loadAbsences();
        this.resetForm();
      });
    }
  }

  addAbsence(): void {
    this.resetForm();
    this.isEditing = false;
    this.showTable = false; // Masquer le tableau
  }

  editAbsence(absence: any): void {
    this.newAbsence = { ...absence };
    this.isEditing = true;
    this.showTable = false;
  }


  deleteAbsence(id: number): void {
    this.absenceService.deleteAbsence(id).subscribe(() => {
      this.loadAbsences();
    });
  }

  resetForm(): void {
    this.newAbsence = {
      id: null,
      type: '',
      startDate: '',
      endDate: '',
      status: '',
      managerComments: ''
    };
    this.isEditing = false;
    this.showTable = true;
  }
}
