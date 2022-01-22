import * as dayjs from 'dayjs';
import { IEmployee } from 'app/entities/employee/employee.model';
import { IDecreeIssue } from 'app/entities/decree-issue/decree-issue.model';
import { DecType } from 'app/entities/enumerations/dec-type.model';

export interface IDecree {
  id?: number;
  decreenum?: number;
  decreeyear?: number;
  purpose?: string | null;
  dectype?: DecType | null;
  daynum?: number;
  city?: string | null;
  countrty?: string;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  area?: string | null;
  cost?: string | null;
  decreecost?: string | null;
  imageUrl?: string | null;
  imageContentType?: string | null;
  image?: string | null;
  employees?: IEmployee[] | null;
  decreeissue?: IDecreeIssue | null;
  sponsor?: IDecreeIssue | null;
  proponent?: IDecreeIssue | null;
}

export class Decree implements IDecree {
  constructor(
    public id?: number,
    public decreenum?: number,
    public decreeyear?: number,
    public purpose?: string | null,
    public dectype?: DecType | null,
    public daynum?: number,
    public city?: string | null,
    public countrty?: string,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public area?: string | null,
    public cost?: string | null,
    public decreecost?: string | null,
    public imageUrl?: string | null,
    public imageContentType?: string | null,
    public image?: string | null,
    public employees?: IEmployee[] | null,
    public decreeissue?: IDecreeIssue | null,
    public sponsor?: IDecreeIssue | null,
    public proponent?: IDecreeIssue | null
  ) {}
}

export function getDecreeIdentifier(decree: IDecree): number | undefined {
  return decree.id;
}
