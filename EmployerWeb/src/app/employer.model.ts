export interface Formation {
  id: number;
  titreFormation: string;
  dateDebut: Date;
  dateFin: Date;
  certificationObtenue: string;
}

export interface absences {
  id: number;
  type: string;
  startDate: string;
  endDate: string;
  status: string;
  managerComments: string;
}

export interface Departement {
  id?: number;
  nom: string;
}

export interface Employer {

  id?: number;
  nom: string;
  prenom: string;
  adresse: string;
  email: string;
  telephone: string;
  contractType: string;
  departementId?:number;


}
