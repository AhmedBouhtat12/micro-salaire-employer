import {Component, NgIterable, OnInit} from '@angular/core';
import { EmployerService } from '../employer.service';
import { Employer } from '../employer.model';
import { FormsModule } from '@angular/forms';
import { DatePipe, NgForOf, NgIf } from '@angular/common';
import {FormationService} from '../formation/formation.service';
import {DepartementService} from '../departement/departement.service';


@Component({
  selector: 'app-employers',
  templateUrl: './employers.component.html',
  imports: [
    FormsModule,
    NgIf,
    NgForOf,
    DatePipe
  ],
  styleUrls: ['./employers.component.css']
})
export class EmployersComponent implements OnInit {
  employers: Employer[] = [];
  departements: any[] = [];
  formations: any[] = [];
  absences: any[] = [];
  isEditMode = false;
  selectedEmployerId!: number | undefined; // ID de l'employeur sélectionné
  selectedFormationId!: number; // ID de la formation sélectionnée
  selectedAbsenceId!: number;
  message = '';
  errorMessage: string = '';
  successMessage: string = '';
  showAddForm: boolean = false;
  showAddForm1: boolean = false;
  showAddForm2: boolean = false;
  showUpdateForm: boolean = false;
  selectedEmployer: Employer | null = null;
  formationsVisible: boolean = false;
  absencesVisible: boolean = false;
  newEmployer: Employer = {
    id: undefined, // Champ id optionnel
    nom: '',
    prenom: '',
    email: '',
    telephone: '',
    contractType: '',
    adresse: ''
  };



  constructor(private employerService: EmployerService  , private departementService : DepartementService) {}

  ngOnInit(): void {
    this.loadEmployers();
    this.loadFormations();
    this.loadAbsence();
    this.loadDepartements();
  }
  loadDepartements(): void {
    this.departementService.getAllDepartements().subscribe({
      next: (data) => {
        this.departements = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des départements:', err);
      }
    });
  }

  loadFormations(): void {
    this.employerService.getAllFormations().subscribe({
      next: (data: any[]) => {
        this.formations = data;
      },
      error: (error: any) => {
        console.error('Erreur lors du chargement des formations:', error);
        alert('Une erreur est survenue lors du chargement des formations. Veuillez réessayer plus tard.');
      }
    });
  }
  loadAbsence(): void {
    this.employerService.getAllAbsences().subscribe({
      next: (data: any[]) => {
        this.absences = data;
      },
      error: (error: any) => {
        console.error('Erreur lors du chargement des absences:', error);
        alert('Une erreur est survenue lors du chargement des formations. Veuillez réessayer plus tard.');
      }
    });
  }


  private loadEmployers(): void {
    this.employerService.getAllEmployers().subscribe({
      next: (data) => {
        this.employers = data;
        this.errorMessage = '';
      },
      error: (err) => {
        this.errorMessage = 'Erreur lors du chargement des employeurs.';
        console.error(err);
      }
    });
  }

  toggleAddForm(): void {
    this.showAddForm = !this.showAddForm;
    this.successMessage = '';
    this.errorMessage = '';
  }
  toggleAddForm1(): void {
    this.showAddForm1 = !this.showAddForm1;
  }
  toggleAddForm2(): void {
    this.showAddForm2 = !this.showAddForm2;
  }

  addEmployer(): void {
    this.employerService.addEmployer(this.newEmployer).subscribe({
      next: (employer) => {
        this.employers.push(employer);
        this.resetForm();
        this.showMessage('success', 'Employé ajouté avec succès !');
        this.toggleAddForm();
      },
      error: (err) => {
        this.showMessage('error', `Erreur lors de l'ajout de l'employé : ${err.message}`);
        console.error('Erreur:', err);
      }
    });
  }



  updatedEmployer: Employer = { // Employer à mettre à jour
    id: undefined,
    nom: '',
    prenom: '',
    email: '',
    telephone: '',
    contractType: '',
    adresse: ''
  };
  private showMessage(messageType: 'success' | 'error', message: string): void {
    if (messageType === 'success') {
      this.successMessage = message;
    } else {
      this.errorMessage = message;
    }
    setTimeout(() => {
      this.successMessage = '';
      this.errorMessage = '';
    }, 3000);
  }

  showDetails(employer: Employer): void {
    this.selectedEmployer = employer;
    this.successMessage = '';
    this.errorMessage = '';
  }



  deleteEmployer(id: number | undefined): void {
    if (id && confirm('Êtes-vous sûr de vouloir supprimer cet employé ?')) {
      this.employerService.deleteEmployer(id).subscribe({
        next: () => {
          console.log(`Employé avec ID ${id} supprimé avec succès`);
          this.employers = this.employers.filter(emp => emp.id !== id);
          this.successMessage = 'Employé supprimé avec succès !';
        },
        error: (err) => {
          this.errorMessage = `Erreur lors de la suppression de l'employé: ${err.message}`;
          console.error('Erreur:', err);
        }
      });
    }
  }



  getFormations(employerId: number | undefined): void {
    this.employerService.getFormationsByEmployer(employerId).subscribe({
      next: (formations) => {
        console.log(`Formations pour l'employé ${employerId}:`, formations);
        this.formations = formations;
        this.errorMessage = '';
        this.showAddForm1 = true;

      },
      error: (err) => {
        console.error('Erreur lors de la récupération des formations:', err);
        this.errorMessage = 'Erreur lors de la récupération des formations. Veuillez réessayer.';
      }
    });
  }
  getAbsences(employerId: number | undefined): void {
    this.employerService.getAbsencesByEmployer(employerId).subscribe({
      next: (absences) => {
        console.log(`Absences pour l'employé ${employerId}:`, absences);
        this.absences = absences;
        this.errorMessage = '';
        this.showAddForm2 = true;

      },
      error: (err) => {
        console.error('Erreur lors de la récupération des absences:', err);
        this.errorMessage = 'Erreur lors de la récupération des absences. Veuillez réessayer.';
      }
    });
  }


  selectEmployer0(employerId: number | undefined): void {
    // Si l'employé sélectionné est déjà celui sur lequel vous avez cliqué, on ferme les formulaires
    if (this.selectedEmployerId === employerId) {
      this.formationsVisible = false; // Cache le formulaire de formations
      this.selectedEmployerId = undefined;
    } else {
      this.selectedEmployerId = employerId;
      this.formationsVisible = true; // Affiche le formulaire de formations

    }
  }
  selectEmployer(employerId: number | undefined): void {

    if (this.selectedEmployerId === employerId) {
      this.absencesVisible = false; // Cache le formulaire d'absences
      this.selectedEmployerId = undefined;
    } else {
      this.selectedEmployerId = employerId;
      this.absencesVisible = true; // Affiche le formulaire d'absences
    }
  }
  selectEmployer1(employerId: number | undefined): void {
    this.getFormations(employerId);
    this.selectedEmployerId = employerId;

  }
  selectEmployer2(employerId: number | undefined): void {
    this.getAbsences(employerId);
    this.selectedEmployerId = employerId;
  }

  addFormationToEmployer(): void {
    if (!this.selectedEmployerId || !this.selectedFormationId) {
      this.errorMessage = 'Veuillez sélectionner un employé et une formation.';
      return;
    }

    this.employerService.addFormationToEmployer(this.selectedEmployerId, this.selectedFormationId).subscribe({
      next: () => {
        this.successMessage = 'Formation ajoutée avec succès !';
        this.getFormations(this.selectedEmployerId);
      },
      error: (err) => {
        this.errorMessage = `Erreur lors de l'ajout de la formation : ${err.message}`;
        console.error('Erreur:', err);
      }
    });
  }
  addAbsencesToEmployer(): void {
    if (!this.selectedEmployerId || !this.selectedAbsenceId) {
      this.errorMessage = 'Veuillez sélectionner un employé et une absence.';
      return;
    }

    this.employerService.addAbsencesToEmployer(this.selectedEmployerId, this.selectedAbsenceId).subscribe({
      next: () => {
        this.successMessage = 'absence ajoutée avec succès !';
        this.getAbsences(this.selectedEmployerId);
      },
      error: (err) => {
        this.errorMessage = `Erreur lors de l'ajout de la absence : ${err.message}`;
        console.error('Erreur:', err);
      }
    });
  }
  toggleUpdateForm(employer: Employer): void {
    this.showUpdateForm = true;
    this.updatedEmployer = { ...employer };
    this.successMessage = '';
    this.errorMessage = '';
  }

  updateEmployer(): void {
    if (this.newEmployer.id) {  // Vous utilisez 'newEmployer' ici pour accéder à 'id'
      this.employerService.updateEmployer(this.newEmployer.id, this.newEmployer).subscribe({
        next: (updated) => {
          // Met à jour l'employé localement dans la liste
          const index = this.employers.findIndex(e => e.id === updated.id);
          if (index !== -1) {
            this.employers[index] = updated;
          }
          this.showAddForm = false;  // Vous avez utilisé 'showUpdateForm' qui semble incorrect
          this.resetForm();
          this.successMessage = 'Employé mis à jour avec succès !';
        },
        error: (err) => {
          console.error('Erreur lors de la mise à jour:', err);
          this.errorMessage = `Erreur lors de la mise à jour de l'employé : ${err.message}`;
        }
      });
    }
  }

  editEmployer(employer: any) {
    this.newEmployer = { ...employer };
    this.showAddForm = true;
    this.isEditMode = true; // Passer en mode édition
  }
  resetForm(): void {
    this.newEmployer = {
      id: undefined,
      nom: '',
      prenom: '',
      email: '',
      telephone: '',
      adresse: '',
      contractType: '',
      departementId: undefined
    };
  }

  removeFormationFromEmployer(employerId: number | undefined, formationId: number): void {
    if (!employerId || !formationId) {
      this.errorMessage = 'Employé ou formation non spécifiés.';
      return;
    }

    this.employerService.removeFormationFromEmployer(employerId, formationId).subscribe({
      next: () => {
        this.successMessage = 'Formation supprimée avec succès.';
        // Rechargez ou mettez à jour la liste des formations
      },
      error: (err) => {
        this.errorMessage = `Erreur lors de la suppression de la formation: ${err.message}`;
        console.error('Erreur:', err);
      }
    });
  }

  removeAbsenceFromEmployer(employerId: number | undefined, absenceId: number): void {
    if (!employerId || !absenceId) {
      this.errorMessage = 'Employé ou absence non spécifiés.';
      return;
    }
      this.employerService.removeAbsenceFromEmployer(employerId, absenceId).subscribe({
        next: () => {
          this.successMessage = 'absence supprimée avec succès.';
          // Rechargez ou mettez à jour la liste des formations
        },
        error: (err) => {
          this.errorMessage = `Erreur lors de la suppression de la absence: ${err.message}`;
          console.error('Erreur:', err);
        }
      });
  }

  getDepartmentName(departementId: number | undefined): string {
    const department = this.departements.find(dept => dept.id === departementId);
    return department ? department.nom : 'Non défini';
  }


}
