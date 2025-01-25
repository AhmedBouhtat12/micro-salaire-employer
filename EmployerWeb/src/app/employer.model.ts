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



export interface User {
  id?: number;
  username: string;
  nom: string;
  prenom: string;
  email: string;
  password: string;
  role: 'EMPLOYER' | 'RESPONSABLE';  // Enum pour les rôles
  address: string;
  telephone: string;
  contractType: string;  // Assuming ContractType is a string enum
  formationId?: number[];
  absencesId?: number[];
  departementId?: number;
}


