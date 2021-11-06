import { ISector } from 'app/entities/sector/sector.model';
import { IDegree } from 'app/entities/degree/degree.model';
import { IDecree } from 'app/entities/decree/decree.model';

export interface IEmployee {
  id?: number;
  name?: string | null;
  jobposition?: string | null;
  phone?: string | null;
  departement?: string | null;
  sector?: ISector | null;
  degree?: IDegree | null;
  decrees?: IDecree[] | null;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public name?: string | null,
    public jobposition?: string | null,
    public phone?: string | null,
    public departement?: string | null,
    public sector?: ISector | null,
    public degree?: IDegree | null,
    public decrees?: IDecree[] | null
  ) {}
}

export function getEmployeeIdentifier(employee: IEmployee): number | undefined {
  return employee.id;
}
