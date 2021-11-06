import { IEmployee } from 'app/entities/employee/employee.model';

export interface IDegree {
  id?: number;
  name?: string | null;
  number?: number | null;
  employees?: IEmployee[] | null;
}

export class Degree implements IDegree {
  constructor(public id?: number, public name?: string | null, public number?: number | null, public employees?: IEmployee[] | null) {}
}

export function getDegreeIdentifier(degree: IDegree): number | undefined {
  return degree.id;
}
