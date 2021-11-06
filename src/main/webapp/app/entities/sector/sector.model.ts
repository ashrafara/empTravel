import { IEmployee } from 'app/entities/employee/employee.model';

export interface ISector {
  id?: number;
  name?: string;
  phone?: string | null;
  address?: string | null;
  employees?: IEmployee[] | null;
  parents?: ISector[] | null;
  sector?: ISector | null;
}

export class Sector implements ISector {
  constructor(
    public id?: number,
    public name?: string,
    public phone?: string | null,
    public address?: string | null,
    public employees?: IEmployee[] | null,
    public parents?: ISector[] | null,
    public sector?: ISector | null
  ) {}
}

export function getSectorIdentifier(sector: ISector): number | undefined {
  return sector.id;
}
