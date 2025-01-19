import { Component, OnInit } from '@angular/core';
import { DepartementService } from './departement.service';
import {NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-departement',
  templateUrl: './departement.component.html',
  imports: [
    NgForOf,
    FormsModule,
    NgIf
  ],
  styleUrls: ['./departement.component.css']
})
export class DepartementComponent implements OnInit {
  Departement: any[] = [];
  selectedDepartement: any = null;
  newDepartementName: string = '';

  constructor(private departementService: DepartementService) { }

  ngOnInit(): void {
    this.getDepartements();
  }

  getDepartements(): void {
    this.departementService.getAllDepartements().subscribe(data => {
      this.Departement = data;
    });
  }

  getDepartementDetails(id: number): void {
    this.departementService.getDepartementDetails(id).subscribe(data => {
      this.selectedDepartement = data;
    });
  }

  deleteDepartement(id: number): void {
    this.departementService.deleteDepartement(id).subscribe(() => {
      this.getDepartements();
    });
  }

  saveDepartement(): void {
    const newDepartement = { id: null, nom: this.newDepartementName }; // Vérifiez que 'name' correspond au champ attendu par le backend
    this.departementService.saveDepartement(newDepartement).subscribe(() => {
      this.getDepartements();
      this.newDepartementName = '';
    });

  }

}
