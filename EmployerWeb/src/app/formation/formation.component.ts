import { Component, OnInit } from '@angular/core';
import { FormationService } from './formation.service';
import {FormsModule} from '@angular/forms';
import {DatePipe, NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-formation',
  standalone: true,
  templateUrl: './formation.component.html',
  styleUrls: ['./formation.component.css'],
  imports: [
    FormsModule,
    NgIf,
    NgForOf,
    DatePipe
  ]
})
export class FormationComponent implements OnInit {
  formations: any[] = [];
  selectedFormation: any = null;
  isEditing = false;

  constructor(private formationService: FormationService) {}

  ngOnInit(): void {
    this.loadFormations();
  }

  loadFormations(): void {
    this.formationService.getAllFormations().subscribe(
      (data) => {
        this.formations = data;
      },
      (error) => {
        console.error('Erreur lors du chargement des formations:', error);
      }
    );
  }

  addFormation(): void {
    const newFormation = {
      titreFormation: '',
      dateDebut: '',
      dateFin: '',
      certificationObtenue: '',
    };
    this.selectedFormation = newFormation;
    this.isEditing = true;
  }

  editFormation(formation: any): void {
    this.selectedFormation = { ...formation };
    this.isEditing = true;
  }

  saveFormation(): void {
    if (this.selectedFormation.id) {
      this.formationService
        .updateFormation(this.selectedFormation.id, this.selectedFormation)
        .subscribe(() => {
          this.loadFormations();
          this.cancelEdit();
        });
    } else {
      this.formationService.createFormation(this.selectedFormation).subscribe(() => {
        this.loadFormations();
        this.cancelEdit();
      });
    }
  }

  deleteFormation(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette formation ?')) {
      this.formationService.deleteFormation(id).subscribe(() => {
        this.loadFormations();
      });
    }
  }

  cancelEdit(): void {
    this.selectedFormation = null;
    this.isEditing = false;
  }
}
